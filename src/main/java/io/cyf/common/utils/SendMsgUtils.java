package io.cyf.common.utils;

import com.zhenzi.sms.ZhenziSmsClient;

import java.util.HashMap;
import java.util.Map;

public class SendMsgUtils {

    public static Boolean sendMsg(String phone, String code){

        String apiUrl = "https://sms_developer.zhenzikj.com";
        String appId = "110406";
        String appSecret = "1ff37082-7faf-44d5-871a-7ad381ceaee8";
        ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("number", phone);
        params.put("templateId", "7383");
        String[] templateParams = new String[2];
        templateParams[0] = code;
        templateParams[1] = "5分钟";

        params.put("templateParams", templateParams);
        String result = null;
        try {
            result = client.send(params);
            System.out.println(result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return false;
    }

    public static String sendAppointmentMsg(String phone, String realName, String date, String time){
        String apiUrl = "https://sms_developer.zhenzikj.com";
        String appId = "110406";
        String appSecret = "1ff37082-7faf-44d5-871a-7ad381ceaee8";
        ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("number", phone);
        params.put("templateId", "7718");
        String[] templateParams = new String[3];
        templateParams[0] = realName;
        templateParams[1] = date;
        templateParams[2] = time;

        params.put("templateParams", templateParams);
        String result = null;
        try {
            result = client.send(params);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}