package com.liangmayong.apidesigner.listener;

import com.liangmayong.apidesigner.entity.APIResponse;
import com.liangmayong.apidesigner.exception.APIErrorException;

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
     * @param response
     */
    public abstract void onResponse(APIResponse response);

    /**
     * onFailure
     *
     * @param error error
     */
    public abstract void onFailure(APIErrorException error);

}
