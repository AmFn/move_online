package io.cyf.modules.app.controller;


import io.cyf.common.utils.R;
import io.cyf.modules.oss.service.SysOssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/app/oss/upload")
@Api(tags="上传文件接口")

public class UploadController {

        @Autowired
        private SysOssService ossService;
        @PostMapping()
        @ApiOperation("上传文件")
        public R uploadOssFiles(MultipartFile file){
            String url = ossService.uploadFileAvatar(file);
            return R.ok().put("url",url);
        }


}
