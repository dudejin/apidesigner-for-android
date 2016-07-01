package com.liangmayong.apidesigner.listener;

import java.util.List;

import com.liangmayong.apidesigner.entity.APIResponse;
import com.liangmayong.apidesigner.exception.APIErrorException;

/**
 * OnApiEntityListListener
 *
 * @author LiangMaYong
 * @version 1.0
 */
public abstract class OnApiEntityListListener<T> {

    /**
     * result
     *
     * @param code     result code
     * @param message  result message
     * @param list     result entitys
     * @param response result response
     */
    public abstract void result(String code, String message, List<T> list, APIResponse response);

    /**
     * failure
     *
     * @param error error
     */
    public abstract void failure(APIErrorException error);


}
