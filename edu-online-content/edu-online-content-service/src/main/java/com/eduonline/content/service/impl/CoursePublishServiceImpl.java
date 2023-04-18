package com.eduonline.content.service.impl;


import com.alibaba.fastjson.JSON;
import com.eduonline.base.exception.CommonError;
import com.eduonline.base.exception.EduOnlineException;
import com.eduonline.content.config.MultipartSupportConfig;
import com.eduonline.content.feignclient.MediaServiceClient;
import com.eduonline.content.mapper.CourseBaseMapper;
import com.eduonline.content.mapper.CourseMarketMapper;
import com.eduonline.content.mapper.CoursePublishMapper;
import com.eduonline.content.mapper.CoursePublishPreMapper;
import com.eduonline.content.model.dto.CourseBaseInfoDto;
import com.eduonline.content.model.dto.CoursePreviewDto;
import com.eduonline.content.model.dto.TeachplanDto;
import com.eduonline.content.model.po.CourseBase;
import com.eduonline.content.model.po.CourseMarket;
import com.eduonline.content.model.po.CoursePublish;
import com.eduonline.content.model.po.CoursePublishPre;
import com.eduonline.content.service.CourseBaseInfoService;
import com.eduonline.content.service.CoursePublishService;
import com.eduonline.content.service.TeachplanService;
import com.eduonline.messagesdk.model.po.MqMessage;
import com.eduonline.messagesdk.service.MqMessageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @author Anesthesia
 * @version 1.0
 * @description 课程发布相关接口实现
 * @date 2023/3/28 16:37
 */
@Slf4j
@Service
public class CoursePublishServiceImpl implements CoursePublishService {

    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    TeachplanService teachplanService;

    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    CoursePublishPreMapper coursePublishPreMapper;

    @Autowired
    CoursePublishMapper coursePublishMapper;

    @Autowired
    MqMessageService mqMessageService;

    @Autowired
    MediaServiceClient mediaServiceClient;

    @Override
    public CoursePreviewDto getCoursePreviewInfo(Long courseId) {

        CoursePreviewDto coursePreviewDto = new CoursePreviewDto();
        // 查询课程基本信息、营销信息
        CourseBaseInfoDto courseBaseInfo = courseBaseInfoService.getCourseBaseInfo(courseId);
        // 封装
        coursePreviewDto.setCourseBase(courseBaseInfo);

        // 课程计划信息
        List<TeachplanDto> teachplanTree = teachplanService.findTeachplanTree(courseId);
        // 封装
        coursePreviewDto.setTeachplans(teachplanTree);

        return coursePreviewDto;
    }

    @Transactional
    @Override
    public void commitAudit(Long companyId, Long courseId) {

        CourseBaseInfoDto courseBaseInfo = courseBaseInfoService.getCourseBaseInfo(courseId);
        if (courseBaseInfo == null){
            EduOnlineException.cast("该课程不存在");
        }
        // 审核状态
        String auditStatus = courseBaseInfo.getAuditStatus();

        // 如果课程的审核状态为已提交则不允许提交
        if (auditStatus.equals("202003")){
            EduOnlineException.cast("该课程已提交，审核中，请耐心等待审核完成");
        }

        // 机构只能提交该机构的课程，机构id不同不允许提交
//        Long companyIdTable = courseBaseInfo.getCompanyId();
//        if (companyId != companyIdTable){
//            EduOnlineException.cast("机构只能提交该机构的课程");
//        }

        // 课程的图片、计划信息没有填写也不允许提交
        String pic = courseBaseInfo.getPic();
        if (StringUtils.isEmpty(pic)){
            EduOnlineException.cast("请上传课程图片");
        }
        // 查询课程计划
        List<TeachplanDto> teachplanTree = teachplanService.findTeachplanTree(courseId);
        if (teachplanTree == null || teachplanTree.size() == 0){
            EduOnlineException.cast("请编写课程计划");
        }

        // 查询到课程基本信息、营销信息、计划等信息插入到课程预发布表
        CoursePublishPre coursePublishPre = new CoursePublishPre();
        BeanUtils.copyProperties(courseBaseInfo,coursePublishPre);

        // 设置机构id
        coursePublishPre.setCompanyId(companyId);

        // 营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        // 转Json
        String courseMarketJson = JSON.toJSONString(courseMarket);
        coursePublishPre.setMarket(courseMarketJson);

        // 计划信息
        // 转Json
        String teachplanTreeJson = JSON.toJSONString(teachplanTree);
        coursePublishPre.setTeachplan(teachplanTreeJson);

        // 状态设置为已提交
        coursePublishPre.setStatus("202003");
        // 提交时间
        coursePublishPre.setCreateDate(LocalDateTime.now());

        // 检查预发布表，若有记录则更新、无则插入
        CoursePublishPre coursePublishPreObj = coursePublishPreMapper.selectById(courseId);
        if (coursePublishPreObj == null){
            // 插入
            coursePublishPreMapper.insert(coursePublishPre);
        }else {
            // 更新
            coursePublishPreMapper.updateById(coursePublishPre);
        }

        // 更新课程基本信息表的审核状态为已提交
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        courseBase.setAuditStatus("202003");

        courseBaseMapper.updateById(courseBase);

    }

    @Transactional
    @Override
    public void publish(Long companyId, Long courseId) {

        // 获取预发布表内容
        CoursePublishPre coursePublishPre = coursePublishPreMapper.selectById(courseId);
        if (coursePublishPre == null){
            EduOnlineException.cast("该课程没有审核记录，无法发布");
        }

        // 获取审核状态
        String status = coursePublishPre.getStatus();
        // 如果没有审核通过则不允许发布
        if (!status.equals("202004")){
            EduOnlineException.cast("该课程没有审核通过，不允许发布");
        }

        // 向发布表写入数据
        CoursePublish coursePublish = new CoursePublish();
        BeanUtils.copyProperties(coursePublishPre, coursePublish);
        // 先查询发布表，若有则更新，无则添加
        CoursePublish coursePublishObj = coursePublishMapper.selectById(courseId);
        if (coursePublishObj == null){
            coursePublishMapper.insert(coursePublish);
        }else {
            coursePublishMapper.updateById(coursePublish);
        }

        // 向信息表写入数据
//        mqMessageService.addMessage("course_publish", String.valueOf(courseId),null,null);
        saveCoursePublishMessage(courseId);

        // 将预发布表的数据删除
        coursePublishPreMapper.deleteById(courseId);
    }

    @Override
    public File generateCourseHtml(Long courseId) {
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 最终的静态文件载体
        File htmlFile=null;

        try {
            // 拿到classpath路径
            String classpath = this.getClass().getResource("/").getPath();
            // 指定一下模板的目录
            configuration.setDirectoryForTemplateLoading(new File(classpath + "/templates/"));
            // 指定得到模板文件的编码格式为utf-8
            configuration.setDefaultEncoding("utf-8");
            // 得到模板,就是获取html页面的代码
            Template template = configuration.getTemplate("course_template.ftl");

            // 准备数据 coursePreviewInfo这个直接放入freemakertemplateUtils中会有问题 需要先建个map
            CoursePreviewDto coursePreviewInfo = this.getCoursePreviewInfo(courseId);
            HashMap<String, Object> map = new HashMap<>();
            map.put("model",coursePreviewInfo);

            // html页面代码字符串化 所需参数： Template template 模板,Object model 数据
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

            // 输入流
            InputStream inputStream = IOUtils.toInputStream(html, "utf-8");
            // 输出文件
            htmlFile= File.createTempFile("coursepublishtemp",".html"); // 生成临时文件
            FileOutputStream outputStream = new FileOutputStream(htmlFile);
            // 使用流将html写入文件,拷贝流
            IOUtils.copy(inputStream,outputStream);
        }catch (Exception e){
            log.error("页面静态化出现问题，课程id:{}", courseId);
            e.printStackTrace();
        }


        return htmlFile;
    }

    @Override
    public void uploadCourseHtml(Long courseId, File file) {

        try {
            MultipartFile multipartFile = MultipartSupportConfig.getMultipartFile(file);
            String upload = mediaServiceClient.upload(multipartFile, "course/"+courseId+".html");
            if (upload == null){
                log.debug("远程调用走了降级逻辑处理，得到的上传结果为null，课程id:{}", courseId);
                EduOnlineException.cast("上传静态文件过程中存在异常");
            }
        }catch (Exception e){
            e.printStackTrace();
            EduOnlineException.cast("上传静态文件过程中存在异常");
        }

    }

    /**
     * @description 保存消息表记录
     * @param courseId  课程id
     * @return void
     * @author Anesthesia
     * @date 2023/03/29 21:32
     */
    private void saveCoursePublishMessage(Long courseId){
        MqMessage mqMessage = mqMessageService.addMessage("course_publish", String.valueOf(courseId), null, null);
        if (mqMessage == null){
            EduOnlineException.cast(CommonError.UNKOWN_ERROR);
        }
    }
}
