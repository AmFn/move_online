package io.cyf.modules.app.service.impl;

import io.cyf.common.exception.RRException;
import io.cyf.common.utils.Constant;
import io.cyf.modules.app.Dto.CitySearch;
import io.cyf.modules.app.Dto.DriverRoute;
import io.cyf.modules.app.Dto.Poi;
import io.cyf.modules.app.Dto.SearchAddressResponseDto;
import io.cyf.modules.app.Dto.SaveAddressDTO;
import io.cyf.modules.app.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.Query;

import io.cyf.modules.app.dao.AddressDao;
import io.cyf.modules.app.entity.AddressEntity;
import io.cyf.modules.app.service.AddressService;
import org.springframework.web.client.RestTemplate;

/**
 * @author ChengFeng
 */
@Service("addressService")
public class AddressServiceImpl extends ServiceImpl<AddressDao, AddressEntity> implements AddressService {
    private static  final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
    @Autowired
    UserService userService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AddressEntity> page = this.page(
                new Query<AddressEntity>().getPage(params),
                new QueryWrapper<AddressEntity>()
        );

        return new PageUtils(page);
    }

    public boolean saveAddress(SaveAddressDTO oldAddress, SaveAddressDTO newAddress, Long uid){
        AddressEntity oldAddr = saveAddressByLocation(oldAddress);
        AddressEntity newAddr = saveAddressByLocation(newAddress);
        return  userService.updateAddr(oldAddr.getId(), newAddr.getId(), uid);
    }
    public AddressEntity saveAddressByLocation(SaveAddressDTO addressDTO){
        String location = addressDTO.getLocation();
        String address = addressDTO.getAddress();
        String detail = addressDTO.getDetail();
        AddressEntity addressEntity =   new AddressEntity();
        String url = "https://restapi.amap.com/v3/place/around?key="+ Constant.GAODE_APP_KEY+"&location="+location;
        RestTemplate templates = new RestTemplate();
        ResponseEntity<CitySearch> poiResponseEntity = templates.getForEntity(url, CitySearch.class);
        if (poiResponseEntity.getStatusCode().isError()) {
            throw new RRException("请求失败");

        }
        CitySearch citySearch = poiResponseEntity.getBody();

        if(citySearch.getInfo().equals(Constant.GAODE_STATUS_OK)){
            Poi poi = citySearch.getPois().get(0);
            addressEntity.setCity(poi.getCityname());
            addressEntity.setLocation(location);
            addressEntity.setDetail(address+detail);
            addressEntity.setProvince(poi.getPname());
            addressEntity.setDistrict(poi.getAdname());
            int insert = baseMapper.insert(addressEntity);
            AddressEntity insertAddressEntity = baseMapper.selectById(insert);
            if(insertAddressEntity!=null){
                log.info("poi {}",poi.toString());
                return  insertAddressEntity;
            }

        }
        log.error("获取api失败");
        throw new RRException(citySearch.getInfo());

    }



    public  String getDrivingDistance(String origin,String destination){
        String url = "https://restapi.amap.com/v5/direction/driving?key="+ Constant.GAODE_APP_KEY+"&origin="+origin+"&destination="+destination;
        log.info("请求api 参数origin： {} destination：{}",origin,destination);
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


    public List<SearchAddressResponseDto> searchByKeyWord(String keyword,String city){
        String url = "https://restapi.amap.com/v3/place/text?keywords="+keyword+"&city="+city+"&output=json&offset=20&page=1&key="+ Constant.GAODE_APP_KEY+"&extensions=base";
        RestTemplate templates = new RestTemplate();
        ResponseEntity<CitySearch> searchResponseEntity = templates.getForEntity(url, CitySearch.class);
        if (searchResponseEntity.getStatusCode().isError()) {
            throw new RRException("请求失败");

        }

        CitySearch citySearch = searchResponseEntity.getBody();
        assert null != citySearch;
        if(citySearch.getInfo().equals(Constant.GAODE_STATUS_OK)){
            return citySearch
                    .getPois()
                    .stream()
                    .map(poi ->{
                        SearchAddressResponseDto res = new SearchAddressResponseDto();
                        if(StringUtils.isNotBlank(poi.getLocation())){
                            res.setLocation(poi.getLocation());
                        }
                        StringBuilder sb = new StringBuilder();
                        if(StringUtils.isNotBlank(poi.getPname())){
                            sb.append( poi.getPname());
                        }
                        if(StringUtils.isNotBlank(poi.getCityname() )){
                            sb.append( poi.getCityname());
                        }
                        if(StringUtils.isNotBlank(poi.getAdname())){
                            sb.append( poi.getAdname());
                        }

                        if(StringUtils.isNotBlank(poi.getName())){
                            sb.append( "("+poi.getName()+")");
                        }

                        res.setAddress(sb.toString());
                        return  res;
                    })

                    .collect(Collectors.toList());

        }

        log.error("获取api失败");
        throw new RRException(citySearch.getInfo());
    }






}