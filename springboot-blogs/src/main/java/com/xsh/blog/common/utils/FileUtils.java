package com.xsh.blog.common.utils;

import com.xsh.blog.business.file.entity.BlogFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Slf4j
@Component
public class FileUtils {


    private static String fileLocation;
    @Value("${bigSmart.file-location}")
    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    /**
     * 文件类型-后缀
     * @return
     */
    public static String getFileType(String fileName){
        int i = fileName.lastIndexOf(".");
        String fileType = fileName.substring(i + 1, fileName.length());
        return fileType;
    }

    /**
     * 文件名称-前缀
     * @return
     */
    public static String getFileName(String fileName){
        int i = fileName.lastIndexOf(".");
        String fileType = fileName.substring(0,i);
        return fileType;
    }

    public static BlogFile getFile(MultipartFile file){

        long size = file.getSize();
        String fileType = getFileType(file.getOriginalFilename());
        String fileName = getFileName(file.getOriginalFilename()) + "." + fileType;

        // 生成随机名称
        String randomFileName = UUID.randomUUID().toString();
        // 文件路径
        String fileLocationT = fileLocation + randomFileName + "." + fileType;
        try {
            file.transferTo(new File(fileLocationT));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        BlogFile finalFile = BlogFile.builder().fileName(fileName).fileType(fileType).fileSize((double) size).fileLocation(fileLocationT).build();
        return finalFile;
    }

    public void  downloadFile(String fileLocation) {
        InputStream deployInputStream = null;
        try {
            deployInputStream = new FileInputStream(fileLocation);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ServletOutputStream outputStream = null;
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        byte[] buf = new byte[1024 * 5];
        try {
            outputStream = response.getOutputStream();
            while (deployInputStream.read(buf) > 0) {
                outputStream.write(buf, 0, buf.length);
            }
            //强制将流刷新到response中
            outputStream.flush();
        } catch (IOException io) {
            log.error("文件流传输出错", io);
        } finally {
            try {
                outputStream.close();
                deployInputStream.close();
            } catch (Exception e) {
                log.error("流关闭时出错", e);
            }
        }

    }
}
