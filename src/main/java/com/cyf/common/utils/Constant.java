/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.cyf.common.utils;


import java.util.Optional;
import java.util.stream.Stream;

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Constant {
    /**
     * 距离费用
     */
    public static final  double DISTANCE_FEE = 0.01;

    public static final  double DISTANCE_FEE_EXTRA = 0.05;

    public static final  double DISTANCE_TEN_KILO_MITRE = 10000;
    /**
     * 楼层费用
     */
    public static final  double STOREY_FEE = 10;





    public static final String GAODE_STATUS_OK =  "OK";
    /**
     * 高德appKey
     */
    public static final String GAODE_APP_KEY="15c907891e55c0bfa1a7ecd1c6b95838";
    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     * 升序
     */
    public static final String ASC = "asc";



    /**
     * 菜单类型
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年11月15日 下午1:24:29
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 订单状态;0查询中|1计价完成|2进行中|3已完成|4已支付|5售后中|6异常
     */
    public enum OrderStatus{
        QUERY(0,"查询中"),
        Computed(1,"计价完成"),
        MOVING(2,"进行中"),
        COMPLETED(3,"已完成"),
        PAYED(4,"已支付"),
        AFTERMARKET(5,"售后中"),
        ERROR(6,"异常")
        ;
        private int key;
        private  String  value;

        OrderStatus(int key,String value){
            this.key = key;
            this.value = value;

        }
        public int getKey() {
            return key;
        }
        public String getValue() {
            return value;
        }
        public static String getValueByKey(int key){
            Optional<OrderStatus> first = Stream.of(OrderStatus.values()).filter(status -> status.key ==key).findFirst();
            if (!first.isPresent()) {
                throw new IllegalArgumentException("非法的枚举值:" + key);
            }
            return first.get().value;
        }
    }


//    /**
//     * 云服务商
//     */
//    public enum CloudService {
//        /**
//         * 七牛云
//         */
//        QINIU(1, QiniuGroup.class),
//        /**
//         * 阿里云
//         */
//        ALIYUN(2, AliyunGroup.class),
//        /**
//         * 腾讯云
//         */
//        QCLOUD(3, QcloudGroup.class);
//
//        private int value;
//
//        private Class<?> validatorGroupClass;
//
//        CloudService(int value, Class<?> validatorGroupClass) {
//            this.value = value;
//            this.validatorGroupClass = validatorGroupClass;
//        }
//
//        public int getValue() {
//            return value;
//        }
//
//        public Class<?> getValidatorGroupClass() {
//            return this.validatorGroupClass;
//        }
//
//        public static CloudService getByValue(Integer value) {
//            Optional<CloudService> first = Stream.of(CloudService.values()).filter(cs -> value.equals(cs.value)).findFirst();
//            if (!first.isPresent()) {
//                throw new IllegalArgumentException("非法的枚举值:" + value);
//            }
//            return first.get();
//        }
//    }

}
