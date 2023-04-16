package com.eduonline.content.service;

import com.eduonline.content.model.dto.CoursePreviewDto;

/**
 /**
 * @author Anesthesia
 * @version 1.0
 * @description 课程发布相关接口
 * @date 2023/3/28 16:33
 */
public interface CoursePublishService {

    /**
     * @description 获取课程预览信息
     * @param courseId  课程id
     * @return
     * @author Anesthesia
     * @date 2023/3/28 16:35
     */
    public CoursePreviewDto getCoursePreviewInfo(Long courseId);

    /**
     * @description 提交审核
     * @param companyId 机构id
     * @param courseId  课程id
     * @author Anesthesia
     * @date 2023/3/28/18:54
     */
    public void commitAudit(Long companyId, Long courseId);

    /**
     * @description 课程发布接口
     * @param companyId
     * @param courseId
     * @author Anesthesia
     * @date 2023/3/29/10:54
     */
    public void publish(Long companyId, Long courseId);
}
