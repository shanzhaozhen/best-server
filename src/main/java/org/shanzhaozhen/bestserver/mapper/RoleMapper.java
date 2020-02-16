package org.shanzhaozhen.bestserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.shanzhaozhen.bestserver.domain.sys.RoleDO;
import org.shanzhaozhen.bestserver.dto.RoleDTO;

import java.util.List;

public interface RoleMapper extends BaseMapper<RoleDO> {

    RoleDTO getRoleByRoleId(@Param("roleId") Long roleId);

    @Select("select r.id, r.name, r.identification, r.description, " +
            "r.create_by, r.created_date, r.last_modified_by, r.last_modified_date " +
            "from sys_role r " +
            "inner join sys_role_route srm on srm.route_id = #{routeId} and r.id = srm.role_id")
    List<RoleDTO> getRoleByRouteId(@Param("routeId") Long routeId);

    @Select("select r.id, r.name, r.identification, r.description, " +
            "r.create_by, r.created_date, r.last_modified_by, r.last_modified_date " +
            "from sys_role r " +
            "inner join sys_role_resource srr on srr.resource_id = #{resourceId} and r.id = srr.role_id")
    List<RoleDTO> getRoleByResourceId(@Param("resourceId") Long resourceId);

    @Select("select r.id, r.name, r.identification, r.description, " +
            "r.create_by, r.created_date, r.last_modified_by, r.last_modified_date " +
            "from sys_role r inner join sys_user_role sur on sur.user_id = #{userId} and r.id = sur.role_id")
    List<RoleDTO> getRoleListByUserId(@Param("userId") Long userId);

    @Select("select id, name, identification, description, " +
            "create_by, created_date, last_modified_by, last_modified_date " +
            "from sys_role where name like concat ('%', #{keyword}, '%') or identification like concat ('%', #{keyword}, '%') or description like concat ('%', #{keyword}, '%')")
    Page<RoleDTO> getRolePage(Page<RoleDTO> page, @Param("keyword") String keyword);

    @Select("select id, name, identification, description, " +
            "create_by, created_date, last_modified_by, last_modified_date " +
            "from sys_role where identification = #{identification}")
    RoleDTO getRoleByIdentification(@Param("identification") String identification);

    @Select("select id, name, identification, description, " +
            "create_by, created_date, last_modified_by, last_modified_date " +
            "from sys_role where id != #{id} and identification = #{identification}")
    RoleDTO getRoleByIdNotInAndIdentification(@Param("id") Long id, @Param("identification") String identification);
}
