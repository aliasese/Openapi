package com.cnebula.lsp.openapi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtil {

    private static String urlRegUrl = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9]+)$";

    private final static String regexIPV4s = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                                        + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                                        + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                                        + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

    public static Boolean checkIPV4(String ipv4){
        Pattern p = Pattern.compile(regexIPV4s);
        Matcher m = p.matcher(ipv4);
        return m.find();
    }

    public static Boolean checkUrl(String url){
        Pattern p = Pattern.compile(urlRegUrl);
        Matcher m = p.matcher(url);
        return m.find();
    }

    /*public static void main(String[] args) {
        String url = "http://222.29.81.252:9130/questions/11809631/fully-qualified-domain-name-validation";
        String url2 = "https://blog.csdn.net/seawave/article/details/1520988";
        System.out.println(url.matches(urlRegUrl));
        System.out.println(url2.matches(urlRegUrl));
    }*/
}
