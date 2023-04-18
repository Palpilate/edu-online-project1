package com.eduonline.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eduonline.ucenter.model.po.EduMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Anesthesia
 */
public interface EduMenuMapper extends BaseMapper<EduMenu> {
    @Select("SELECT	* FROM xc_menu WHERE id IN (SELECT menu_id FROM xc_permission WHERE role_id IN ( SELECT role_id FROM xc_user_role WHERE user_id = #{userId} ))")
    List<EduMenu> selectPermissionByUserId(@Param("userId") String userId);
}
