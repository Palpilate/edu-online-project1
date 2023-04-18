package com.eduonline.content;

import com.eduonline.content.config.MultipartSupportConfig;
import com.eduonline.content.feignclient.MediaServiceClient;
import com.eduonline.content.model.dto.CoursePreviewDto;
import com.eduonline.content.service.CoursePublishService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


/**
 * @author Anesthesia
 * @version 1.0
 * @description 测试远程调用媒资服务
 * @date 2023/3/30 8:34
 */
@SpringBootTest
public class FeignUploadTests {

    @Autowired
    MediaServiceClient mediaServiceClient;
    @Test
    public void test() throws IOException {
        // 将file转成MultipartFile
        File file = new File("E:\\tools\\minio\\upload\\119.html");
        MultipartFile multipartFile = MultipartSupportConfig.getMultipartFile(file);
        String upload = mediaServiceClient.upload(multipartFile, "course/119.html");
        if (upload == null){
            System.out.println("走了降级逻辑处理");
        }
    }
}
