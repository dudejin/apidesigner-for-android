package com.liangmayong.apidesigner.entity;

import java.util.List;

import com.liangmayong.apidesigner.interfaces.APIConstructor;

/**
 * APIResponse
 *
 * @author LiangMaYong
 * @version 1.0
 */
public final class APIResponse {

    public APIResponse(APIConstructor constructor) {
        this.constructor = constructor;
    }

    public APIResponse(APIConstructor constructor, Object object) {
        this.constructor = constructor;
        setResponse(object);
    }

    private APIConstructor constructor;
    private Object object = null;
    private APIParser parser = null;

    /**
     * set response
     *
     * @param object response
     */
    public void setResponse(Object object) {
        this.object = object;
    }

    /**
     * get response
     *
     * @return response
     */
    public Object getResponse() {
        return object;
    }

    /**
     * getParser
     *
     * @return parser parser
     */
    public APIParser getParser() {
        if (parser == null) {
            parser = new APIParser(this);
        }
        return parser;
    }

    /**
     * @author LiangMaYong
     * @version 1.0
     */
    public class APIParser {

        private APIResponse response;

        public APIParser(APIResponse response) {
            this.response = response;
        }

        private String code = null;
        private String message = null;
        private String data = null;
        private Object object = null;
        private Object objectList = null;
        private boolean isSuccess = false;

        /**
         * parse Status
         *
         * @return Status Status
         */
        public String getCode() {
            if (code == null) {
                code = constructor.parseCode(response);
            }
            return code;
        }

        /**
         * isSuccess
         *
         * @return true or false
         */
        public boolean isSuccess() {
            return constructor.isSuccess(response);
        }

        /**
         * parse Data
         *
         * @return Data Data
         */
        public String getData() {
            if (data == null) {
                data = constructor.parseData(response);
            }
            return data;
        }

        /**
         * getEntity
         *
         * @param entityClass entityClass
         * @param <T>         t
         * @return t
         */
        public <T> T getEntity(Class<T> entityClass) {
            if (object == null) {
                object = constructor.parseEntity(entityClass, response);
            }
            try {
                return (T) object;
            } catch (Exception e) {
                return null;
            }
        }

        /**
         * parse to entitys
         *
         * @param entityClass clazz
         * @param <T>         t
         * @return T
         */
        public <T> List<T> getEntitys(Class<T> entityClass) {
            if (objectList == null) {
                object = constructor.parseEntitys(entityClass, response);
            }
            try {
                return (List<T>) objectList;
            } catch (Exception e) {
                return null;
            }
        }

        /**
         * parse Message
         *
         * @return Message
         */
        public String getMessage() {
            if (message == null) {
                message = constructor.parseMessage(response);
            }
            return message;
        }
    }
}
