package org.shanzhaozhen.bestrepo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.shanzhaozhen.bestcommon.domain.sys.RoleRouteDO;

public interface RoleRouteMapper extends BaseMapper<RoleRouteDO> {

    @Delete("delete from sys_role_route where role_id = #{roleId}")
    int deleteByRoleId(Long roleId);
}
