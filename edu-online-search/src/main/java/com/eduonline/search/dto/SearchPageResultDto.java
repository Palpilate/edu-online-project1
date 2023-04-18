package com.eduonline.search.dto;

import com.eduonline.base.model.PageResult;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Anesthesia
 * @version 1.0
 * @description TODO
 * @date 2023/4/1 17:51
 */
@Data
@ToString
public class SearchPageResultDto<T> extends PageResult {

    //大分类列表
    List<String> mtList;
    //小分类列表
    List<String> stList;

    public SearchPageResultDto(List<T> items, long counts, long page, long pageSize) {
        super(items, counts, page, pageSize);
    }

}
