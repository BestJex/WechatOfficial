package com.jex.official.common;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpUtils {

    /**
     * 获取ip地址
     */
    public static String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("X-Real-IP");
                    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                        return request.getRemoteAddr();
                    }
                }
            }
        }
        int index = ip.indexOf(",");
        if (index != -1) {
            return ip.substring(0, index);
        } else {
            return ip;
        }
    }

    /**
     * 是否为ipv4地址
     */
    public static boolean isIPV4Address(String ip){
        int count=0;
        Pattern p=Pattern.compile("(^\\d{1,3})(\\.)(\\d{1,3})(\\.)(\\d{1,3})(\\.)(\\d{1,3}$)");
        Matcher m=p.matcher(ip);
        while(m.find()){
            for(int i=1;i<8;i+=2){
                if(Integer.parseInt(m.group(i))<=255&&Integer.parseInt(m.group(i))>=0){
                    count++;
                }
            }
        }
        if(count==4)
            return true;
        else
            return false;
    }

    /**
     * 获取服务器的ip地址
     *
     * @param request
     * @return
     */
    public static String getLocalIp(HttpServletRequest request) {
        return request.getLocalAddr();
    }
}
