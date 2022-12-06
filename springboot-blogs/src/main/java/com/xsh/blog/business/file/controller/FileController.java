package com.xsh.blog.business.file.controller;


import com.alibaba.fastjson.JSONObject;
import com.xsh.blog.business.file.entity.BlogFile;
import com.xsh.blog.business.file.service.IFileService;

import com.xsh.blog.common.utils.FileUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 */
@Slf4j
@Api(tags = "文件管理")
@RestController
@RequestMapping("/business/file")
public class FileController {

    private IFileService fileService;
    private FileUtils fileUtils;


    @Autowired
    public FileController(IFileService fileService, FileUtils fileUtils) {
        this.fileService = fileService;
        this.fileUtils = fileUtils;
    }




    @PostMapping("/upload")
    public JSONObject uploadFile(@RequestParam(value = "file") MultipartFile[] files){
        log.info("上次文件数量{}",files.length);
        JSONObject jsonObject = new JSONObject();
        if (files.length == 1){
            BlogFile fileObj = fileUtils.getFile(files[0]);
            boolean flag = fileService.save(fileObj);
            if (flag){
                JSONObject data = new JSONObject();
                data.put("url","http://localhost:8080/business/file/lookFile?fileId=" + fileObj.getFileId());
                data.put("alt","213");
                data.put("href","1231");
                jsonObject.put("errno",0);
                jsonObject.put("data",data);
                return jsonObject;
            }
        }
        jsonObject.put("errno",500);
        jsonObject.put("data","暂不支持多文件上传");
        return jsonObject;
    }

    @GetMapping("/lookFile")
    public void downloadFile(@RequestParam String fileId) {
        BlogFile blogFile = fileService.getById(fileId);
        String fileLocation = blogFile.getFileLocation();
        fileUtils.downloadFile(fileLocation);
    }
}
