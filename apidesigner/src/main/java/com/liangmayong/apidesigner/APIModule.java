package com.liangmayong.apidesigner;

import java.net.MalformedURLException;
import java.net.URL;

import com.liangmayong.apidesigner.entity.Parameter;
import com.liangmayong.apidesigner.entity.Response;
import com.liangmayong.apidesigner.exception.APIRequsetException;
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

    private APIDesigner connector;

    /**
     * attachConnector
     *
     * @param connector connector
     */
    protected void attachAPI(APIDesigner connector) {
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
    protected Call createCall(APIMethod method, String url, final Parameter parameter) {
        if (connector != null) {
            Call call = new Call(connector, method, url, parameter);
            return call;
        } else {
            APILog.d("not attach APIDesigner");
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

        private APIDesigner connector;
        private APIMethod method;
        private String url;
        private Parameter parameter;

        private Call(APIDesigner connector, APIMethod method, String url, Parameter parameter) {
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
                connector.asynchronousRequest(context, method, parseUrl(url), parameter, (OnApiDefaultListener) listener);
                return true;
            } else if (listener instanceof OnApiEntityListener) {
                connector.asynchronousRequest(context, method, parseUrl(url), parameter, (OnApiEntityListener) listener);
                return true;
            } else if (listener instanceof OnApiEntityListListener) {
                connector.asynchronousRequest(context, method, parseUrl(url), parameter, (OnApiEntityListListener) listener);
                return true;
            } else if (listener instanceof OnApiRequestListener) {
                connector.asynchronousRequest(context, method, parseUrl(url), parameter, (OnApiRequestListener) listener);
                return true;
            } else if (listener instanceof OnApiStringListener) {
                connector.asynchronousRequest(context, method, parseUrl(url), parameter, (OnApiStringListener) listener);
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
         * @return Response
         * @throws APIRequsetException error
         */
        public Response synchronizationRequest(Context context) throws APIRequsetException {
            return connector.synchronizationRequest(context, method, url, parameter);
        }
    }
}
