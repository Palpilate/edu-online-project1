package com.eduonline.content.service;

import com.eduonline.content.model.dto.SaveTeachplanDto;
import com.eduonline.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * @author Anesthesia
 * @version 1.0
 * @description 课程计划管理相关接口
 * @date 2023/2/14 12:10
 */
public interface TeachplanService {
 /**
  * 根据课程id查询课程计划
  * @param courseId 课程计划
  * @return
  */
  public List<TeachplanDto> findTeachplanTree(Long courseId);

 /**
  * 新增/修改/保存课程计划
  * @param saveTeachplanDto
  */
 public void saveTeachplan(SaveTeachplanDto saveTeachplanDto);
}