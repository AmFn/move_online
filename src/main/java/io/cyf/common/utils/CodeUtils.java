package io.cyf.common.utils;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.util.Random;

/**
 * 生成验证码
 */
public class CodeUtils {

    /**
     *
     * @return
     */
    public static String getCode(){
        Random random = new Random();
        //随机验证码池
        char [] array = "0123456789".toCharArray();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++){
            int index = random.nextInt(array.length);
            sb.append(array[index]);
        }
        return sb.toString();
    }

    public static String getOrderCode(){
        long id = IdWorker.getId();
        return String.valueOf(id);
    }

    public static void main(String[] args) {
        System.out.println(getOrderCode());
    }
}