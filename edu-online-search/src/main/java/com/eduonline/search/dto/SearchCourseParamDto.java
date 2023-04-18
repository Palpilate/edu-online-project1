package com.eduonline.search.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @description 搜索课程参数dtl
 * @author Anesthesia
 * @date 2023/3/30 22:36
 * @version 1.0
 */
 @Data
 @ToString
public class SearchCourseParamDto {

  //关键字
  private String keywords;

  //大分类
  private String mt;

  //小分类
  private String st;
  //难度等级
  private String grade;




}
