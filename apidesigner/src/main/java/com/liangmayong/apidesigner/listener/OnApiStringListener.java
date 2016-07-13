package com.liangmayong.apidesigner.listener;

import com.liangmayong.apidesigner.entity.Response;
import com.liangmayong.apidesigner.exception.APIRequsetException;

/**
 * OnApiStringListener
 *
 * @author LiangMaYong
 * @version 1.0
 */
public abstract class OnApiStringListener {

    /**
     * result
     *
     * @param code     result code
     * @param message  result message
     * @param data     result data
     * @param response result response
     */
    public abstract void result(String code, String message, String data, Response response);

    /**
     * failure
     *
     * @param error error
     */
    public abstract void failure(APIRequsetException error);

}
