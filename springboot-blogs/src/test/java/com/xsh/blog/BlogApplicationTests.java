package com.xsh.blog;

import com.xsh.blog.common.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class BlogApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void test(){
        String fileNmae = "11.jpg";
        String fileType = FileUtils.getFileType(fileNmae);
        log.info(fileType);
    }
}
