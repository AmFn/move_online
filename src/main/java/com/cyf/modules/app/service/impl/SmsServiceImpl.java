package com.cyf.modules.app.service.impl;



import com.alibaba.fastjson.JSONObject;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.cyf.modules.app.service.SmsService;
import com.cyf.modules.sys.dao.SysCaptchaDao;
import com.cyf.modules.sys.entity.SysCaptchaEntity;
import com.cyf.common.utils.CodeUtils;
import com.cyf.common.utils.ConstantPropertiesUtil;
import com.cyf.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author chenyufeng
 * @date 2022/5/10 17:06
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {


    @Autowired
    private SysCaptchaDao captchaDao;

@Override
    public Map<String, Object> sendMsg(String phone) {

        String code = CodeUtils.getCode();
        Map<String, String> codeMap = new HashMap<>(2);
        codeMap.put("code", code);
        Boolean flag = null;
        try {
            flag = sendByAliyun(phone, code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>(2);
        String uuid = UUID.randomUUID().toString();

        if (flag){
            SysCaptchaEntity captchaEntity = new SysCaptchaEntity();
            captchaEntity.setUuid(uuid);
            captchaEntity.setCode(code);
            //5分钟后过期
            captchaEntity.setExpireTime(DateUtils.addDateMinutes(new Date(), 1));
            captchaDao.insert(captchaEntity);
            map.put("uuid", uuid);
        }

        map.put("flag", flag);
        System.out.println(code);
        return map;
    }
    public static boolean sendPriceByAliyun(String phoneNumber,String price){

        log.error("{}发送价格信息",phoneNumber);
        DefaultProfile profile =
                DefaultProfile.getProfile("default", ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关固定的参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("SignName","多彩校园二手平台");
        request.putQueryParameter("PhoneNumbers",phoneNumber);

        request.putQueryParameter("TemplateCode","SMS_241345283");

        Map<String, String> map = new HashMap<>();
        map.put("price",price);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));
        try {
            //最终发送
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }

 public static boolean sendByAliyun(String phoneNumber,String code){

    log.error("{}正在获取验证码",phoneNumber);
     DefaultProfile profile =
             DefaultProfile.getProfile("default", ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
     IAcsClient client = new DefaultAcsClient(profile);

     //设置相关固定的参数
     CommonRequest request = new CommonRequest();
     //request.setProtocol(ProtocolType.HTTPS);
     request.setMethod(MethodType.POST);
     request.setDomain("dysmsapi.aliyuncs.com");
     request.setVersion("2017-05-25");
     request.setAction("SendSms");

     //设置发送相关的参数
     request.putQueryParameter("SignName","多彩校园二手平台"); //申请阿里云 签名名称
     request.putQueryParameter("PhoneNumbers",phoneNumber); //手机号

     request.putQueryParameter("TemplateCode","SMS_204970871"); //申请阿里云 模板code

     Map<String, String> map = new HashMap<>();
     map.put("code",code);
     request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map)); //验证码数据，转换json数据传递

     try {
         //最终发送
         CommonResponse response = client.getCommonResponse(request);
         boolean success = response.getHttpResponse().isSuccess();
         return success;
     }catch(Exception e) {
         e.printStackTrace();
         return false;
     }

 }



}
