package org.qvit.hybrid.utils;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 辅助输出json，格式：{"resultCode":0,"message":"","data":{}|[]}
 * 
 * @author tianyale 2016年6月30日
 *
 */
public class ResponseJsonUtils {

    public static final String SUCC_CODE = "200";
    public static final String NEED_LOGIN_CODE = "301";
    public static final String UNKNOW_ERROR_CODE = "300";

    /***
     * 
     * @param code
     * @param msg
     * @param navTabId
     * @param callbackType
     * @param rel
     * @param forwardUrl
     * @return
     */

    public static String getJson(String code, String msg, String navTabId, String callbackType, String rel, String forwardUrl) {
        JSONObject obj = new JSONObject();
        obj.put("statusCode", code);
        obj.put("message", msg);
        obj.put("callbackType", callbackType);
        obj.put("navTabId", navTabId);
        obj.put("rel", rel);
        obj.put("forwardUrl", forwardUrl);
        return obj.toJSONString();
    }

    public static String getNeedLoginJson() {
        return getJson(NEED_LOGIN_CODE, "登录超时需要重新登录！", null, null, null, null);
    }

    public static String getUnknowErrorJson(String msg) {
        return getJson(UNKNOW_ERROR_CODE, msg, null, null, null, null);
    }

    /**
     * 
     * @param msg
     *            提示消息
     * @param navTabId
     *            tab页id
     * @param callbackType
     *            * callbackType如果是closeCurrent就会关闭当前tab
     *            只有callbackType="forward"时需要forwardUrl值
     * @param rel
     * @param forwardUrl
     *            调转地址
     * @return
     */
    public static String getSuccessMsg(String msg, String navTabId, String callbackType, String rel, String forwardUrl) {
        return getJson(SUCC_CODE, msg, navTabId, callbackType, rel, forwardUrl);
    }

    public static String getSuccessMsg(String msg) {
        return getJson(SUCC_CODE, msg, null, null, null, null);
    }

    public static String getSuccessMsg(String msg, String navTabId) {
        return getJson(SUCC_CODE, msg, navTabId, null, null, null);
    }

    public static String getUnknowErrorMap(String msg, String callbackType) {
        return getJson(SUCC_CODE, msg, null, callbackType, null, null);
    }

    public static Map<String, Object> getUnknowErrorMap(String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("resultCode", UNKNOW_ERROR_CODE);
        map.put("message", msg);
        return map;
    }
}
