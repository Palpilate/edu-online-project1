package com.eduonline.content.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Anesthesia
 * @version 1.0
 * @description Freemarker入门程序
 * @date 2023/3/28 15:23
 */
@Controller
public class CoursePublishController {

    @GetMapping("/coursepreview/{courseId}")
    public ModelAndView preview(@PathVariable("courseId") Long courseId){

        ModelAndView modelAndView = new ModelAndView();
        // 指定模板
        modelAndView.setViewName("course_template");    // 根据视图名称加.ftl后缀找到模板文件
        return modelAndView;
    }
}
