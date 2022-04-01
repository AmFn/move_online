package io.cyf.modules.app.service.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import io.cyf.common.exception.RRException;
import io.cyf.common.utils.Constant;
import io.cyf.modules.app.Dto.DriverRoute;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.Query;

import io.cyf.modules.app.dao.AddressDao;
import io.cyf.modules.app.entity.AddressEntity;
import io.cyf.modules.app.service.AddressService;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import static io.cyf.common.utils.Constant.GAODE_STATUS_OK;

/**
 * @author ChengFeng
 */
@Service("addressService")
public class AddressServiceImpl extends ServiceImpl<AddressDao, AddressEntity> implements AddressService {
    private static  final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AddressEntity> page = this.page(
                new Query<AddressEntity>().getPage(params),
                new QueryWrapper<AddressEntity>()
        );

        return new PageUtils(page);
    }

    public boolean saveAddress(){

        return true;
    }

    public  String getDrivingDistance(String origin,String destination){
        String url = "https://restapi.amap.com/v5/direction/driving?key="+ Constant.GAODE_APP_KEY+"&origin="+origin+"&destination="+destination;
//        log.info("请求高德api {}",url);
        RestTemplate templates = new RestTemplate();
        ResponseEntity<DriverRoute> driverRouteResponseEntity = templates.getForEntity(url, DriverRoute.class);
        if (driverRouteResponseEntity.getStatusCode().isError()) {
            throw new RRException("请求失败");

        }
        DriverRoute driverRoute = driverRouteResponseEntity.getBody();
        assert driverRoute != null;
        if(driverRoute.getInfo().equals(Constant.GAODE_STATUS_OK)){
            return driverRoute.getRoute().getPaths().get(0).getDistance();
        }
        log.error("获取距离失败");
        throw new RRException(driverRoute.getInfo());

    }
}