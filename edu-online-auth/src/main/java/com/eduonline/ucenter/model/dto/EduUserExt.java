package com.eduonline.ucenter.model.dto;

import com.eduonline.ucenter.model.po.EduUser;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 用户扩展信息
 * @author Anesthesia
 * @date 2023/3/31 13:56
 * @version 1.0
 */
@Data
public class EduUserExt extends EduUser {
    //用户权限
    List<String> permissions = new ArrayList<>();
}
