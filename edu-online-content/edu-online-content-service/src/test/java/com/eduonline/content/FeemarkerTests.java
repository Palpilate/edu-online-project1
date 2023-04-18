package com.eduonline.content;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


/**
 * @author Anesthesia
 * @version 1.0
 * @description 测试freemarker页面静态化的方法
 * @date 2023/3/29 22:24
 */
@SpringBootTest
public class FeemarkerTests {

    @Autowired
    CoursePublishService coursePublishService;

    @Test
    public void testSGenerateHtmlByTemplate() throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.getVersion());

        // 拿到classpath路径
        String classpath = this.getClass().getResource("/").getPath();
        // 指定一下模板的目录
        configuration.setDirectoryForTemplateLoading(new File(classpath + "/templates/"));
        // 指定得到模板文件的编码格式为utf-8
        configuration.setDefaultEncoding("utf-8");
        // 得到模板,就是获取html页面的代码
        Template template = configuration.getTemplate("course_template.ftl");

        // 准备数据 coursePreviewInfo这个直接放入freemakertemplateUtils中会有问题 需要先建个map
        CoursePreviewDto coursePreviewInfo = coursePublishService.getCoursePreviewInfo(119L);
        HashMap<String, Object> map = new HashMap<>();
        map.put("model",coursePreviewInfo);

        // html页面代码字符串化 所需参数： Template template 模板,Object model 数据
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

        // 输入流
        InputStream inputStream = IOUtils.toInputStream(html, "utf-8");
        // 输出文件
        FileOutputStream outputStream = new FileOutputStream(new File("E:\\tools\\minio\\upload\\119.html"));
        // 使用流将html写入文件,拷贝流
        IOUtils.copy(inputStream,outputStream);

    }
}
