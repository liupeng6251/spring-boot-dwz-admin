package org.qvit.caterpillar.utils;

import java.util.UUID;

/**
 * Created by liupeng on 2018/4/1.
 */
public class UUIDUtils {
    public static String genUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
