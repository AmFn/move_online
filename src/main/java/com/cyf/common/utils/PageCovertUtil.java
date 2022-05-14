package com.cyf.common.utils;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * IPage<T> 转IPage<B>封装工具类
 *
 * @Author:Jarvis
 * @Date 2021/8/17 17:12
 */
public class PageCovertUtil {
    /**
     * 将PageInfo对象泛型中的Po对象转化为Vo对象
     *
     * @param pageInfoPo PageInfo<Po>对象</>
     * @param <V>        V类型
     * @return
     */
    public static <P,V> IPage<V> pageVoCovert(IPage<P> pageInfoPo, Class<V> v) {
        // 创建Page对象，实际上是一个ArrayList类型的集合
        try {
            if (pageInfoPo != null) {

                IPage<V> page = new Page<>(pageInfoPo.getCurrent(), pageInfoPo.getSize());
                page.setTotal(pageInfoPo.getTotal());
                List<P> records = pageInfoPo.getRecords();
                List<V> list = new ArrayList<>();
                for (P record : records) {
                    if(record!=null){
                        V newV = v.newInstance();
                        // 把原对象数据拷贝到新的对象
                        BeanUtil.copyProperties(record,newV);
                        list.add(newV);
                    }
                }
                page.setRecords(list);
                page.setTotal(pageInfoPo.getTotal());
                return page;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
