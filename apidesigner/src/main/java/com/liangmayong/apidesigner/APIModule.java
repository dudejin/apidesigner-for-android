package com.liangmayong.apidesigner;

import java.net.MalformedURLException;
import java.net.URL;

import com.liangmayong.apidesigner.entity.APIParameter;
import com.liangmayong.apidesigner.entity.APIResponse;
import com.liangmayong.apidesigner.exception.APIErrorException;
import com.liangmayong.apidesigner.interfaces.APIConstructor.APIMethod;
import com.liangmayong.apidesigner.listener.OnApiDefaultListener;
import com.liangmayong.apidesigner.listener.OnApiEntityListListener;
import com.liangmayong.apidesigner.listener.OnApiEntityListener;
import com.liangmayong.apidesigner.listener.OnApiRequestListener;
import com.liangmayong.apidesigner.listener.OnApiStringListener;

import android.content.Context;

/**
 * APIModule
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class APIModule {

    public APIModule() {
    }

    private API connector;

    /**
     * attachConnector
     *
     * @param connector connector
     */
    protected void attachAPI(API connector) {
        this.connector = connector;
    }

    /**
     * createCall
     *
     * @param method    method
     * @param url       url
     * @param parameter parameter
     * @return call
     */
    protected Call createCall(APIMethod method, String url, final APIParameter parameter) {
        if (connector != null) {
            Call call = new Call(connector, method, url, parameter);
            return call;
        } else {
            APILog.d("not attach API");
            return null;
        }
    }

    /**
     * destroy connection
     *
     * @param context context
     */
    public void destroy(Context context) {
        if (connector != null) {
            connector.destroy(context);
        }
    }

    /**
     * Call
     */
    public static class Call {

        private API connector;
        private APIMethod method;
        private String url;
        private APIParameter parameter;

        private Call(API connector, APIMethod method, String url, APIParameter parameter) {
            this.connector = connector;
            this.method = method;
            this.parameter = parameter;
            this.url = url;
        }

        /**
         * asynchronousRequest
         *
         * @param context  context
         * @param listener listener
         * @return true or false
         */
        public boolean asynchronousRequest(Context context, OnApiStringListener listener) {
            return _asynchronousRequest(context, listener);
        }

        /**
         * asynchronousRequest
         *
         * @param context  context
         * @param listener listener
         * @return true or false
         */
        public boolean asynchronousRequest(Context context, OnApiDefaultListener listener) {
            return _asynchronousRequest(context, listener);
        }

        /**
         * asynchronousRequest
         *
         * @param context  context
         * @param listener listener
         * @return true or false
         */
        public boolean asynchronousRequest(Context context, OnApiEntityListener<?> listener) {
            return _asynchronousRequest(context, listener);
        }

        /**
         * asynchronousRequest
         *
         * @param context  context
         * @param listener listener
         * @return true or false
         */
        public boolean asynchronousRequest(Context context, OnApiRequestListener listener) {
            return _asynchronousRequest(context, listener);
        }

        /**
         * asynchronousRequest
         *
         * @param context  context
         * @param listener listener
         * @return true or false
         */
        public boolean asynchronousRequest(Context context, OnApiEntityListListener<?> listener) {
            return _asynchronousRequest(context, listener);
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        private boolean _asynchronousRequest(Context context, Object listener) {
            if (listener == null) {
                return false;
            }
            if (listener instanceof OnApiDefaultListener) {
                connector.request(context, method, parseUrl(url), parameter, (OnApiDefaultListener) listener);
                return true;
            } else if (listener instanceof OnApiEntityListener) {
                connector.request(context, method, parseUrl(url), parameter, (OnApiEntityListener) listener);
                return true;
            } else if (listener instanceof OnApiEntityListListener) {
                connector.request(context, method, parseUrl(url), parameter, (OnApiEntityListListener) listener);
                return true;
            } else if (listener instanceof OnApiRequestListener) {
                connector.request(context, method, parseUrl(url), parameter, (OnApiRequestListener) listener);
                return true;
            } else if (listener instanceof OnApiStringListener) {
                connector.request(context, method, parseUrl(url), parameter, (OnApiStringListener) listener);
                return true;
            }
            return false;
        }

        /**
         * parseUrl
         *
         * @param url url
         * @return url
         */
        private String parseUrl(String url) {
            if (url.startsWith("http://") || url.startsWith("https://")) {
                return url;
            } else if (url.startsWith("/")) {
                return getUrlHost(connector.getBaseUrl()) + url;
            } else {
                return connector.getBaseUrl() + url;
            }
        }

        /**
         * getUrlHost
         *
         * @param url url
         * @return url
         */
        private String getUrlHost(String url) {
            try {
                return new URL(url).getHost();
            } catch (MalformedURLException e) {
            }
            return url;
        }

        /**
         * synchronousRequest
         *
         * @param context context
         * @return APIResponse
         * @throws APIErrorException error
         */
        public APIResponse synchronousRequest(Context context) throws APIErrorException {
            return connector.syncRequest(context, method, url, parameter);
        }
    }
}
