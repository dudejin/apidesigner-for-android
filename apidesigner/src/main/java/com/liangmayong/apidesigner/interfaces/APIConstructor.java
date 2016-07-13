package com.liangmayong.apidesigner.interfaces;

import java.util.List;

import com.liangmayong.apidesigner.entity.Parameter;
import com.liangmayong.apidesigner.entity.Response;
import com.liangmayong.apidesigner.exception.APIRequsetException;
import com.liangmayong.apidesigner.listener.OnApiRequestListener;

import android.content.Context;

/**
 * APIConstructor
 *
 * @author LiangMaYong
 * @version 1.0
 */
public interface APIConstructor {

    /**
     * APIDesigner REQUEST Method<br>
     * CREATE PUT<br>
     * READ GET<br>
     * UPDATE POST<br>
     * DELETE DELETE<br>
     *
     * @author LiangMaYong
     * @version 1.0
     */
    public static enum APIMethod {
        POST, GET, PUT, DELETE;
    }

    /**
     * base url
     *
     * @return base url
     */
    String getBaseUrl();

    /**
     * is success
     *
     * @param response response
     * @return true or false
     */
    boolean isSuccess(Response response);

    /**
     * parse Data
     *
     * @param response response
     * @return data
     */
    String parseData(Response response);

    /**
     * parseEntity
     *
     * @param entityClass entityClass
     * @param response    response
     * @param <T>         t
     * @return t
     */
    <T> T parseEntity(Class<T> entityClass, Response response);

    /**
     * parseEntitys
     *
     * @param entityClass entityClass
     * @param response    response
     * @param <T>         t
     * @return list
     */
    <T> List<T> parseEntitys(Class<T> entityClass, Response response);

    /**
     * parseMessage
     *
     * @param response response
     * @return message
     */
    String parseMessage(Response response);

    /**
     * parseCode
     *
     * @param response response
     * @return code
     */
    String parseCode(Response response);

    /**
     * destroy
     *
     * @param context context
     */
    void destroy(Context context);

    /**
     * asynchronousRequest
     *
     * @param context   context
     * @param method    method
     * @param url       url
     * @param parameter parameter
     * @param listener  listener
     */
    void asynchronousRequest(Context context, APIMethod method, String url, Parameter parameter,
                             final OnApiRequestListener listener);

    /**
     * synchronizationRequest
     *
     * @param context   context
     * @param method    method
     * @param url       url
     * @param parameter parameter
     * @return Response
     * @throws APIRequsetException e
     */
    Response synchronizationRequest(Context context, APIMethod method, String url, Parameter parameter)
            throws APIRequsetException;
}
