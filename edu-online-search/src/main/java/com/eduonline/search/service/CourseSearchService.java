package com.eduonline.search.service;

import com.eduonline.base.model.PageParams;
import com.eduonline.base.model.PageResult;
import com.eduonline.search.dto.SearchCourseParamDto;
import com.eduonline.search.dto.SearchPageResultDto;
import com.eduonline.search.po.CourseIndex;

/**
 * @description 课程搜索service
 * @author Anesthesia
 * @date 2023/3/30 22:40
 * @version 1.0
 */
public interface CourseSearchService {


    /**
     * @description 搜索课程列表
     * @param pageParams 分页参数
     * @param searchCourseParamDto 搜索条件
     * @return com.eduonline.base.model.PageResult<com.eduonline.search.po.CourseIndex> 课程列表
     * @author Anesthesia
     * @date 2023/3/30 22:45
    */
    SearchPageResultDto<CourseIndex> queryCoursePubIndex(PageParams pageParams, SearchCourseParamDto searchCourseParamDto);

 }
