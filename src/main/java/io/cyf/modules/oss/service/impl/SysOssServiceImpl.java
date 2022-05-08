/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.cyf.modules.oss.service.impl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cyf.common.utils.ConstantPropertiesUtil;
import io.cyf.common.utils.PageUtils;
import io.cyf.common.utils.Query;
import io.cyf.modules.oss.dao.SysOssDao;
import io.cyf.modules.oss.entity.SysOssEntity;
import io.cyf.modules.oss.service.SysOssService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<SysOssEntity> page = this.page(
			new Query<SysOssEntity>().getPage(params)
		);

		return new PageUtils(page);
	}

	@Override
	public String uploadFileAvatar(MultipartFile file) {
		// 工具类获取值
		String endpoint = ConstantPropertiesUtil.END_POINT;
		String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
		String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
		String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

		try {
			// 创建OSS实例。
			OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

			//获取上传文件输入流
			InputStream inputStream = file.getInputStream();
			//获取文件名称
			String fileName = file.getOriginalFilename();
			// 在文件名称里面添加随机唯一的值
			String uuid = UUID.randomUUID().toString().replaceAll("-","");
			fileName = uuid+fileName;

			//获取当前日期
			String datePath = new DateTime().toString("yyyy/MM/dd");
			//拼接
			fileName = datePath+"/"+fileName;
			//第一个参数  Bucket名称
			//第二个参数  上传到oss文件路径和文件名称   aa/bb/1.jpg
			//第三个参数  上传文件输入流
			ossClient.putObject(bucketName,fileName , inputStream);
			// 关闭OSSClient。
			ossClient.shutdown();
			//把上传之后文件路径返回
			//需要把上传到阿里云oss路径手动拼接出来
			String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
			return url;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
