package com.eduonline.content.model.dto;

import com.eduonline.content.model.po.CourseCategory;
import lombok.Data;

import java.util.List;

/**
 * @author Anesthesia
 * @version 1.0
 * @description TODO
 * @date 2023/3/21 11:51
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements java.io.Serializable {

   //子节点
   List<CourseCategoryTreeDto> childrenTreeNodes;

}
