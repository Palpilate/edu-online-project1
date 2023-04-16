package com.eduonline.content.model.dto;

import lombok.Data;

import java.util.List;

/**
 /**
 * @author Anesthesia
 * @version 1.0
 * @description 课程预览模型类
 * @date 2023/3/28 16:23
 */
@Data
public class CoursePreviewDto {

    // 课程基本信息、营销信息
    private CourseBaseInfoDto courseBase;

    // 课程计划信息
    private List<TeachplanDto> teachplans;

}
