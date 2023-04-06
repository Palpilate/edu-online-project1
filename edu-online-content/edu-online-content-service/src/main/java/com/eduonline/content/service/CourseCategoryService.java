package com.eduonline.content.service;

import com.eduonline.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * @author Anesthesia
 * @version 1.0
 * @description TODO
 * @date 2023/3/21 14:49
 */
public interface CourseCategoryService {
 /**
  * 课程分类树形结构查询
  *
  * @return
  */
 public List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
