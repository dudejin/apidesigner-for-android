package com.liangmayong.apidesigner;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.liangmayong.apidesigner.entity.Parameter;
import com.liangmayong.apidesigner.entity.Response;
import com.liangmayong.apidesigner.exception.APIRequsetException;
import com.liangmayong.apidesigner.interfaces.APIConstructor;
import com.liangmayong.apidesigner.interfaces.APIConstructor.APIMethod;
import com.liangmayong.apidesigner.listener.OnApiDefaultListener;
import com.liangmayong.apidesigner.listener.OnApiEntityListListener;
import com.liangmayong.apidesigner.listener.OnApiEntityListener;
import com.liangmayong.apidesigner.listener.OnApiRequestListener;
import com.liangmayong.apidesigner.listener.OnApiStringListener;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * APIDesigner
 *
 * @author LiangMaYong
 * @version 1.0
 */
@SuppressLint("DefaultLocale")
public final class APIDesigner {
    private static Map<String, APIDesigner> connectorMap = new HashMap<String, APIDesigner>();

    public final static APIDesigner constructor(Class<? extends APIConstructor> constructorClass) {
        if (constructorClass == null) {
            return null;
        }
        if (connectorMap.containsKey(constructorClass.getName())) {
            APIDesigner connector = connectorMap.get(constructorClass.getName());
            return connector;
        } else {
            try {
                APIDesigner connector = new APIDesigner(constructorClass.newInstance());
                connectorMap.put(constructorClass.getName(), connector);
                return connector;
            } catch (Exception e) {
            }
            return null;
        }
    }

    private APIConstructor constructor = null;

    protected String getTag() {
        return "APIDesigner";
    }

    /**
     * create
     *
     * @param clazz clazz
     * @param <T>   T
     * @return APIModule
     */
    public final <T extends APIModule> T create(Class<T> clazz) {
        try {
            Constructor<T> classConstructor = clazz.getDeclaredConstructor();
            classConstructor.setAccessible(true);
            T t = (T) classConstructor.newInstance();
            t.attachAPI(this);
            return t;
        } catch (Exception e) {
        }
        return null;
    }

    private APIDesigner(APIConstructor constructor) {
        this.constructor = constructor;
    }

    /**
     * asynchronousRequest
     *
     * @param context   context
     * @param method    method
     * @param url       url
     * @param parameter parameter
     * @param listener  listener
     * @param <T>       t
     */
    protected <T> void asynchronousRequest(Context context, APIMethod method, String url, final Parameter parameter,
                               final OnApiEntityListener<T> listener) {
        asynchronousRequest(context, method, url, parameter, new OnApiRequestListener() {
            @Override
            public void onResponse(Response response) {
                if (listener != null) {
                    Class<T> entityClass = null;
                    Type t = listener.getClass().getGenericSuperclass();
                    if (t instanceof ParameterizedType) {
                        Type[] p = ((ParameterizedType) t).getActualTypeArguments();
                        entityClass = (Class<T>) p[0];
                    }
                    listener.result(constructor.parseCode(response), constructor.parseMessage(response),
                            constructor.parseEntity(entityClass, response), response);
                }
            }

            @Override
            public void onFailure(APIRequsetException e) {
                if (listener != null) {
                    listener.failure(e);
                }
            }
        });
    }

    /**
     * asynchronousRequest
     *
     * @param context   context
     * @param method    method
     * @param url       url
     * @param parameter parameter
     * @param listener  listener
     * @param <T>       t
     */
    protected <T> void asynchronousRequest(Context context, APIMethod method, String url, final Parameter parameter,
                               final OnApiEntityListListener<T> listener) {
        asynchronousRequest(context, method, url, parameter, new OnApiRequestListener() {

            @Override
            public void onResponse(Response response) {
                if (listener != null) {
                    Class<T> entityClass = null;
                    Type t = listener.getClass().getGenericSuperclass();
                    if (t instanceof ParameterizedType) {
                        Type[] p = ((ParameterizedType) t).getActualTypeArguments();
                        entityClass = (Class<T>) p[0];
                    }
                    listener.result(constructor.parseCode(response), constructor.parseMessage(response),
                            constructor.parseEntitys(entityClass, response), response);
                }
            }

            @Override
            public void onFailure(APIRequsetException e) {
                if (listener != null) {
                    listener.failure(e);
                }
            }
        });
    }

    /**
     * asynchronousRequest
     *
     * @param context   context
     * @param method    method
     * @param url       url
     * @param parameter parameter
     * @param listener  listener
     */
    protected void asynchronousRequest(Context context, APIMethod method, String url, final Parameter parameter,
                           final OnApiStringListener listener) {
        asynchronousRequest(context, method, url, parameter, new OnApiRequestListener() {

            @Override
            public void onResponse(Response response) {
                if (listener != null) {
                    listener.result(constructor.parseCode(response), constructor.parseMessage(response),
                            constructor.parseData(response), response);
                }
            }

            @Override
            public void onFailure(APIRequsetException e) {
                if (listener != null) {
                    listener.failure(e);
                }
            }
        });
    }

    /**
     * asynchronousRequest
     *
     * @param context   context
     * @param method    method
     * @param url       url
     * @param parameter parameter
     * @param listener  listener
     */
    protected void asynchronousRequest(Context context, APIMethod method, String url, final Parameter parameter,
                           final OnApiDefaultListener listener) {
        asynchronousRequest(context, method, url, parameter, new OnApiRequestListener() {

            @Override
            public void onResponse(Response response) {
                if (listener != null) {
                    listener.result(constructor.parseCode(response), constructor.parseMessage(response), response);
                }
            }

            @Override
            public void onFailure(APIRequsetException e) {
                if (listener != null) {
                    listener.failure(e);
                }
            }
        });
    }

    /**
     * asynchronousRequest
     *
     * @param context   context
     * @param method    method
     * @param url       url
     * @param parameter parameter
     * @param listener  listener
     */
    protected void asynchronousRequest(Context context, APIMethod method, String url, Parameter parameter,
                           final OnApiRequestListener listener) {
        constructor.asynchronousRequest(context, method, url, parameter, new OnApiRequestListener() {
            @Override
            public void onResponse(Response response) {
                if (listener != null) {
                    listener.onResponse(response);
                }
                APILog.d("onResponse:" + response.toString());
            }

            @Override
            public void onFailure(APIRequsetException e) {
                if (listener != null) {
                    listener.onFailure(e);
                }
                APILog.d("onFailure", e);
            }
        });
    }

    /**
     * synchronizationRequest
     *
     * @param context   context
     * @param method    method
     * @param url       url
     * @param parameter parameter
     * @return Response
     * @throws APIRequsetException error
     */
    protected Response synchronizationRequest(Context context, APIMethod method, String url, Parameter parameter)
            throws APIRequsetException {
        return constructor.synchronizationRequest(context, method, url, parameter);
    }

    /**
     * getBaseUrl
     *
     * @return base url
     */
    protected String getBaseUrl() {
        return constructor.getBaseUrl();
    }

    /**
     * destroy
     *
     * @param context context
     */
    protected void destroy(Context context) {
        constructor.destroy(context);
    }

}
