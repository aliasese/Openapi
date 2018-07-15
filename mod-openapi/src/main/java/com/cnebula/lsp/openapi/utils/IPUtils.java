package com.cnebula.lsp.openapi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by calis on 2017/11/14.
 */
public class IPUtils {

    private final static String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."

                                        +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."

                                        +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."

                                        +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

    public static Boolean checkIPV4(String ipv4){
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(ipv4);
        return m.find();
    }

}
