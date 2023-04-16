package com.eduonline.content.api;

import com.eduonline.content.model.dto.CoursePreviewDto;
import com.eduonline.content.service.CourseBaseInfoService;
import com.eduonline.content.service.CoursePublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anesthesia
 * @version 1.0
 * @description
 * @date 2023/3/28 17:24
 */
@RestController
@RequestMapping("/open")
public class CourseOpenController {

    @Autowired
    private CourseBaseInfoService courseBaseInfoService;
    @Autowired
    private CoursePublishService coursePublishService;

    // 根据课程id查询课程信息
    @GetMapping("/course/whole/{courseId}")
    public CoursePreviewDto getPreviewInfo(@PathVariable("courseId") Long courseId){
        // 获取课程预览信息
        CoursePreviewDto coursePreviewInfo = coursePublishService.getCoursePreviewInfo(courseId);
        return coursePreviewInfo;
    }
}
