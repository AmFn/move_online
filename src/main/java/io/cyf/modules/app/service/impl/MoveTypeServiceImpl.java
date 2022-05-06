package io.cyf.modules.app.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.Query;
import io.cyf.modules.app.entity.ExtraServiceEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import io.cyf.modules.app.dao.MoveTypeDao;
import io.cyf.modules.app.entity.MoveTypeEntity;
import io.cyf.modules.app.service.MoveTypeService;
import org.springframework.util.StringUtils;

@Service("moveTypeService")
public class MoveTypeServiceImpl extends ServiceImpl<MoveTypeDao, MoveTypeEntity> implements MoveTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<MoveTypeEntity> wrapper = new LambdaQueryWrapper<MoveTypeEntity>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((w) -> {
                w.or().like(MoveTypeEntity::getType, key)

                ;
            });
        }
        IPage<MoveTypeEntity> page = this.page(
                new Query<MoveTypeEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

}