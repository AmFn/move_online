package io.cyf.modules.app.service.impl;

import io.cyf.common.exception.RRException;
import io.cyf.modules.app.Dto.CompensateCreateDto;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.IntFunction;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.Query;

import io.cyf.modules.app.dao.CompensateDao;
import io.cyf.modules.app.entity.CompensateEntity;
import io.cyf.modules.app.service.CompensateService;


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