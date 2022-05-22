package com.cyf.modules.app.service.impl;

import com.cyf.common.validator.Assert;
import com.cyf.modules.app.Dto.*;
import com.cyf.modules.app.service.AddressService;
import com.cyf.common.exception.RRException;
import com.cyf.common.utils.Constant;
import com.cyf.modules.app.service.UserService;
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
import com.cyf.common.utils.PageUtils;
import com.cyf.common.utils.Query;
import com.cyf.modules.app.dao.AddressDao;
import com.cyf.modules.app.entity.AddressEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author ChengFeng
 */
@Service("addressService")
public class AddressServiceImpl extends ServiceImpl<AddressDao, AddressEntity> implements AddressService {
    private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
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

    @Override
    public boolean saveAddress(SaveAddressDTO oldAddress, SaveAddressDTO newAddress, Long uid) {
        AddressEntity oldAddr;
        AddressEntity newAddr;
        if (StringUtils.isBlank(oldAddress.getLocation())) {
            oldAddr = saveAddressWithOutLocation(oldAddress);
        } else {
            oldAddr = saveAddressByLocation(oldAddress);
        }
        if (StringUtils.isBlank(newAddress.getLocation())) {
            newAddr = saveAddressWithOutLocation(newAddress);
        } else {
            newAddr = saveAddressByLocation(newAddress);
        }
        assert oldAddr != null;
        assert newAddr != null;
        return userService.updateAddr(oldAddr.getId(), newAddr.getId(), uid);
    }

    private AddressEntity saveAddressWithOutLocation(SaveAddressDTO address) {
        String addr = address.getAddress();
        String detail = address.getDetail();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setDetail(addr + detail);
        addressEntity.setIsDel(0);
        try {
            List<SearchAddressResponseDto> addressToList = searchByKeyWord(address.getAddress(), null, 10, 1);
            if (addressToList.size() > 0) {
                address.setLocation(addressToList.get(0).getLocation());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        baseMapper.insert(addressEntity);
        if (addressEntity.getId() != null) {
            return addressEntity;
        }
        throw new RRException("地址保存错误");
    }

    public AddressEntity saveAddressByLocation2(SaveAddressDTO addressDTO) {
        String location = addressDTO.getLocation();
        String address = addressDTO.getAddress();
        String detail = addressDTO.getDetail();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setDetail(address + detail);
        try {
            setAddressInfo(addressEntity, location);
        } catch (Exception e) {
            log.error("设置地址基本信息失败", e.getMessage());
        }
        baseMapper.insert(addressEntity);
        return addressEntity;
    }

    private void setAddressInfo(AddressEntity addressEntity, String location) {
        String url = "https://restapi.amap.com/v3/place/around?key=" + Constant.GAODE_APP_KEY + "&location=" + location;
        RestTemplate templates = new RestTemplate();
        ResponseEntity<CitySearch> poiResponseEntity = templates.getForEntity(url, CitySearch.class);
        if (poiResponseEntity.getStatusCode().isError()) {
            throw new RRException("请求失败");
        }
        CitySearch citySearch = poiResponseEntity.getBody();
        if (citySearch.getInfo().equals(Constant.GAODE_STATUS_OK)) {
            Poi poi = citySearch.getPois().get(0);
            addressEntity.setCity(poi.getCityname());
            addressEntity.setLocation(location);
            addressEntity.setProvince(poi.getPname());
            addressEntity.setDistrict(poi.getAdname());
            addressEntity.setIsDel(0);
        }
    }

    public AddressEntity saveAddressByLocation(SaveAddressDTO addressDTO) {
        String location = addressDTO.getLocation();
        String address = addressDTO.getAddress();
        String detail = addressDTO.getDetail();
        AddressEntity addressEntity = new AddressEntity();
        String url = "https://restapi.amap.com/v3/place/around?key=" + Constant.GAODE_APP_KEY + "&location=" + location;
        RestTemplate templates = new RestTemplate();
        ResponseEntity<CitySearch> poiResponseEntity = templates.getForEntity(url, CitySearch.class);
        if (poiResponseEntity.getStatusCode().isError()) {
            throw new RRException("请求失败");
        }
        CitySearch citySearch = poiResponseEntity.getBody();
        if (citySearch.getInfo().equals(Constant.GAODE_STATUS_OK)) {
            Poi poi = citySearch.getPois().get(0);
            addressEntity.setCity(poi.getCityname());
            addressEntity.setLocation(location);
            addressEntity.setDetail(address + detail);
            addressEntity.setProvince(poi.getPname());
            addressEntity.setDistrict(poi.getAdname());
            addressEntity.setIsDel(0);
            int insert = baseMapper.insert(addressEntity);
            if (addressEntity.getId() != null) {
                log.info("poi {}", poi.toString());
                return addressEntity;
            }
        }
        log.error("获取api失败");
        throw new RRException(citySearch.getInfo());
    }

    @Override
    public String getDrivingDistance(String origin, String destination) {
        String url = "https://restapi.amap.com/v5/direction/driving?key=" + Constant.GAODE_APP_KEY + "&origin=" + origin + "&destination=" + destination;
        log.info("请求api 参数origin： {} destination：{}", origin, destination);
        RestTemplate templates = new RestTemplate();
        ResponseEntity<DriverRoute> driverRouteResponseEntity = templates.getForEntity(url, DriverRoute.class);
        if (driverRouteResponseEntity.getStatusCode().isError()) {
            throw new RRException("请求失败");
        }
        DriverRoute driverRoute = driverRouteResponseEntity.getBody();
        assert driverRoute != null;
        if (driverRoute.getInfo().equals(Constant.GAODE_STATUS_OK)) {
            return driverRoute.getRoute().getPaths().get(0).getDistance();
        }
        log.error("获取距离失败");
        throw new RRException(driverRoute.getInfo());
    }

    @Override
    public List<SearchAddressResponseDto> searchByKeyWord(String keyword, String city, int offset, int page) {
        String url = Constant.GAODE_SEARCH_URL + "?keywords=" + keyword + "&city=" + city + "&output=json&offset=" + offset + "&page=" + page + "&key=" + Constant.GAODE_APP_KEY + "&extensions=base";
        log.info("请求url：{}", url);
        RestTemplate templates = new RestTemplate();
        ResponseEntity<CitySearch> searchResponseEntity = templates.getForEntity(url, CitySearch.class);
        if (searchResponseEntity.getStatusCode().isError()) {
            throw new RRException("请求失败");
        }
        CitySearch citySearch = searchResponseEntity.getBody();
        Assert.isNull(citySearch,"搜索失败");
        if (citySearch.getInfo().equals(Constant.GAODE_STATUS_OK)) {
            return citySearch
                    .getPois()
                    .stream()
                    .map(this::convertToResult)
                    .collect(Collectors.toList());
        }
        log.error("获取api失败");
        throw new RRException(citySearch.getInfo());
    }
    private SearchAddressResponseDto convertToResult(Poi poi){
        SearchAddressResponseDto res = new SearchAddressResponseDto();
        if (StringUtils.isNotBlank(poi.getLocation())) {
            res.setLocation(poi.getLocation());
        }
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(poi.getPname())) {
            sb.append(poi.getPname());
        }
        if (StringUtils.isNotBlank(poi.getCityname())) {
            sb.append(poi.getCityname());
        }
        if (StringUtils.isNotBlank(poi.getAdname())) {
            sb.append(poi.getAdname());
        }
        if (StringUtils.isNotBlank(poi.getName())) {
            sb.append("(" + poi.getName() + ")");
        }
        res.setAddress(sb.toString());
        return res;
    }

}