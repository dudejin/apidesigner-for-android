package com.liangmayong.apidesigner.interfaces;

import java.util.List;

import com.liangmayong.apidesigner.entity.APIParameter;
import com.liangmayong.apidesigner.entity.APIResponse;
import com.liangmayong.apidesigner.exception.APIErrorException;
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
     * API REQUEST Method<br>
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
     * parse Data
     *
     * @param response response
     * @return data
     */
    String parseData(APIResponse response);

    /**
     * parseEntity
     * @param entityClass entityClass
     * @param response response
     * @param <T>
     * @return
     */
    <T> T parseEntity(Class<T> entityClass, APIResponse response);

    /**
     * parseEntitys
     *
     * @param entityClass entityClass
     * @param response    response
     * @param <T>         t
     * @return list
     */
    <T> List<T> parseEntitys(Class<T> entityClass, APIResponse response);

    /**
     * parseMessage
     *
     * @param response response
     * @return message
     */
    String parseMessage(APIResponse response);

    /**
     * parseCode
     *
     * @param response response
     * @return code
     */
    String parseCode(APIResponse response);

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
    void asynchronousRequest(Context context, APIMethod method, String url, APIParameter parameter,
                             final OnApiRequestListener listener);

    /**
     * synchronizationRequest
     *
     * @param context   context
     * @param method    method
     * @param url       url
     * @param parameter parameter
     * @return APIResponse
     * @throws APIErrorException e
     */
    APIResponse synchronizationRequest(Context context, APIMethod method, String url, APIParameter parameter)
            throws APIErrorException;
}
