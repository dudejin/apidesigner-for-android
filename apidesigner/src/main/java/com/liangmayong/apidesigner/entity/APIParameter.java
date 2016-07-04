package com.liangmayong.apidesigner.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;

/**
 * APIParameter
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class APIParameter {

    public APIParameter() {
        params = new HashMap<String, String>();
    }

    public APIParameter(Map<String, String> copyMap) {
        params = new HashMap<String, String>(copyMap);
    }

    public APIParameter(JSONObject copyJSON) {
        params = jsonToMap(copyJSON);
    }

    private Map<String, String> params = null;
    private JSONObject json;
    private boolean valueChange = false;
    private boolean useCache = false;
    private Bundle extras;

    /**
     * setExtras
     *
     * @param extras extras
     */
    public void setExtras(Bundle extras) {
        this.extras = extras;
    }

    /**
     * getExtras
     *
     * @return extras
     */
    public Bundle getExtras() {
        return extras;
    }

    /**
     * setUseCache
     *
     * @param useCache useCache
     */
    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

    /**
     * isUseCache
     *
     * @return useCache
     */
    public boolean isUseCache() {
        return useCache;
    }

    /**
     * put string
     *
     * @param key   key
     * @param value value
     */
    public void put(String key, String value) {
        valueChange = true;
        params.put(key, value);
    }

    /**
     * put int
     *
     * @param key   key
     * @param value value
     */
    public void put(String key, int value) {
        valueChange = true;
        params.put(key, value + "");
    }

    /**
     * put boolean
     *
     * @param key   key
     * @param value true=1,false=0
     */
    public void put(String key, boolean value) {
        valueChange = true;
        params.put(key, value ? "1" : "0");
    }

    /**
     * to JSONObject
     *
     * @return JSONObject
     */
    public JSONObject toJSONObject() {
        if (valueChange) {
            json = new JSONObject(params);
            valueChange = false;
        }
        return json;
    }

    /**
     * to Map
     *
     * @return map
     */
    public Map<String, String> toMap() {
        return params;
    }

    private HashMap<String, String> jsonToMap(JSONObject object) {
        HashMap<String, String> data = new HashMap<String, String>();
        @SuppressWarnings("rawtypes")
        Iterator it = object.keys();
        while (it.hasNext()) {
            try {
                String key = String.valueOf(it.next());
                String value = (String) object.get(key);
                data.put(key, value);
            } catch (Exception e) {
            }
        }
        return data;
    }

    @Override
    public String toString() {
        return "APIParameter{" +
                "params=" + params +
                '}';
    }
}
