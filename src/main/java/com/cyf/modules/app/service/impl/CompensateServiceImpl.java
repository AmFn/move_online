package com.cyf.modules.app.service.impl;

import com.cyf.modules.app.service.CompensateService;
import com.cyf.common.exception.RRException;
import com.cyf.modules.app.Dto.CompensateCreateDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyf.common.utils.PageUtils;
import com.cyf.common.utils.Query;

import com.cyf.modules.app.dao.CompensateDao;
import com.cyf.modules.app.entity.CompensateEntity;

@Service("compensateService")
public class CompensateServiceImpl extends ServiceImpl<CompensateDao, CompensateEntity> implements CompensateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CompensateEntity> page = this.page(
                new Query<CompensateEntity>().getPage(params),
                new QueryWrapper<CompensateEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveCompensate(CompensateCreateDto compensate) {
        if(compensate.getOrderId()==null
                ||compensate.getItemName()==null
                ||compensate.getItemPrice()==null
                ||compensate.getCompensate()==null
        ){
            throw new RRException("参数错误");
        }
        CompensateEntity compensateEntity = new CompensateEntity();
        compensateEntity.setOrderId(compensate.getOrderId());
        compensateEntity.setCompensate(BigDecimal.valueOf(compensate.getCompensate()));
        compensateEntity.setItemName(compensate.getItemName());
        compensateEntity.setItemPrice(String.valueOf(compensate.getItemPrice()));
        compensateEntity.setRemark(compensate.getRemark());
        compensateEntity.setPicture(compensate.getPicture());
        compensateEntity.setIsDel(0);
        compensateEntity.setIsAudit(0);
        baseMapper.insert(compensateEntity);

    }
}