package org.qvit.caterpillar.config;

import org.apache.commons.lang3.StringUtils;
import org.qvit.caterpillar.utils.UUIDUtils;

/**
 * Created by liupeng on 2018/4/1.
 */
public class SystemConfig {

    private static String code;
    public static boolean isInit(){
        return false;
    }
    public static synchronized String generAuthCode(){
         code=UUIDUtils.genUUID();
        return code;
    }
    public static boolean checkAuthCode(String inCode){
        if(StringUtils.isEmpty(inCode)){
            return false;
        }
        if(inCode.equals(code)){
            return true;
        }
        return false;
    }
}
