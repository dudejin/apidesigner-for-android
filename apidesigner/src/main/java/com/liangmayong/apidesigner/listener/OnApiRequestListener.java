package com.liangmayong.apidesigner.listener;

import com.liangmayong.apidesigner.entity.Response;
import com.liangmayong.apidesigner.exception.APIRequsetException;

/**
 * OnApiRequestListener
 *
 * @author LiangMaYong
 * @version 1.0
 */
public abstract class OnApiRequestListener {

    /**
     * onResponse
     *
     * @param response response
     */
    public abstract void onResponse(Response response);

    /**
     * onFailure
     *
     * @param error error
     */
    public abstract void onFailure(APIRequsetException error);

}
