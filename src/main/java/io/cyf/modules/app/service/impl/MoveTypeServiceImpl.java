package io.cyf.modules.app.service.impl;


import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import io.cyf.modules.app.dao.MoveTypeDao;
import io.cyf.modules.app.entity.MoveTypeEntity;
import io.cyf.modules.app.service.MoveTypeService;


@Service("moveTypeService")
public class MoveTypeServiceImpl extends ServiceImpl<MoveTypeDao, MoveTypeEntity> implements MoveTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MoveTypeEntity> page = this.page(
                new Query<MoveTypeEntity>().getPage(params),
                new QueryWrapper<MoveTypeEntity>()
        );

        return new PageUtils(page);
    }

}