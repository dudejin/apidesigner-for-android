package com.liangmayong.apidesigner.listener;

import com.liangmayong.apidesigner.entity.APIResponse;
import com.liangmayong.apidesigner.exception.APIErrorException;

/**
 * OnApiEntityListener
 *
 * @author LiangMaYong
 * @version 1.0
 */
public abstract class OnApiEntityListener<T> {

    /**
     * result
     *
     * @param code     result code
     * @param message  result message
     * @param entity   result entity
     * @param response result response
     */
    public abstract void result(String code, String message, T entity, APIResponse response);

    /**
     * failure
     *
     * @param error error
     */
    public abstract void failure(APIErrorException error);


}
