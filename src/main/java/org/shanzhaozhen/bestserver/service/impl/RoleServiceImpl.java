package org.shanzhaozhen.bestserver.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.shanzhaozhen.bestserver.converter.RoleConverter;
import org.shanzhaozhen.bestserver.domain.sys.RoleDO;
import org.shanzhaozhen.bestserver.domain.sys.RoleResourceDO;
import org.shanzhaozhen.bestserver.domain.sys.RoleRouteDO;
import org.shanzhaozhen.bestserver.dto.RoleDTO;
import org.shanzhaozhen.bestserver.form.BaseSearchForm;
import org.shanzhaozhen.bestserver.mapper.RoleMapper;
import org.shanzhaozhen.bestserver.mapper.RoleResourceMapper;
import org.shanzhaozhen.bestserver.mapper.RoleRouteMapper;
import org.shanzhaozhen.bestserver.service.RoleService;
import org.shanzhaozhen.bestserver.utils.CustomBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    private final RoleRouteMapper roleRouteMapper;

    private final RoleResourceMapper roleResourceMapper;

    public RoleServiceImpl(RoleMapper roleMapper, RoleRouteMapper roleRouteMapper, RoleResourceMapper roleResourceMapper) {
        this.roleMapper = roleMapper;
        this.roleRouteMapper = roleRouteMapper;
        this.roleResourceMapper = roleResourceMapper;
    }

    @Override
    public List<RoleDTO> getRolesByUserId(Long userId) {
        return roleMapper.getRoleListByUserId(userId);
    }

    @Override
    public Page<RoleDTO> getRolePage(BaseSearchForm<RoleDTO> baseSearchForm) {
        return roleMapper.getRolePage(baseSearchForm.getPage(baseSearchForm), baseSearchForm.getKeyword());
    }

    @Override
    public RoleDTO getRoleById(Long roleId) {
        RoleDTO roleDTO = roleMapper.getRoleByRoleId(roleId);
        Assert.notNull(roleDTO, "获取失败：没有找到该角色或已被删除");
        return roleDTO;
    }

    @Override
    @Transactional
    public RoleDTO addRole(RoleDTO roleDTO) {
        RoleDTO roleInDB = roleMapper.getRoleByIdentification(roleDTO.getIdentification());
        Assert.isNull(roleInDB, "创建失败：标识名称已被占用");
        RoleDO roleDO = RoleConverter.toDO(roleDTO);
        roleMapper.insert(roleDO);
        updateRouteAndResource(roleDO.getId(), roleDTO.getRouteIds(), roleDTO.getResourceIds());
        return roleDTO;
    }

    @Override
    @Transactional
    public RoleDTO updateRole(RoleDTO roleDTO) {
        Assert.notNull(roleDTO.getId(), "角色id不能为空");
        RoleDTO roleInDB = roleMapper.getRoleByIdNotInAndIdentification(roleDTO.getId(), roleDTO.getIdentification());
        Assert.isNull(roleInDB, "更新失败：标识名称已被占用");
        RoleDO roleDO = roleMapper.selectById(roleDTO.getId());
        Assert.notNull(roleDO, "更新失败：没有找到该角色或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMeta(roleDTO, roleDO);
        roleMapper.updateById(roleDO);
        updateRouteAndResource(roleDO.getId(), roleDTO.getRouteIds(), roleDTO.getResourceIds());
        return roleDTO;
    }

    @Override
    @Transactional
    public Boolean deleteRole(Long roleId) {
        roleRouteMapper.deleteByRoleId(roleId);
        roleResourceMapper.deleteByRoleId(roleId);
        return SqlHelper.retBool(roleMapper.deleteById(roleId));
    }

    @Override
    @Transactional
    public void updateRouteAndResource(@NotNull Long roleId, List<Long> routeIds, List<Long> resourceIds) {
        // 添加角色-路由关联
        if (routeIds != null && routeIds.size() > 0) {
            roleRouteMapper.deleteByRoleId(roleId);
            this.bathAddRoleRoute(roleId, routeIds);
        }
        // 添加角色-资源关联
        if (resourceIds != null && resourceIds.size() > 0) {
            roleResourceMapper.deleteByRoleId(roleId);
            this.bathAddRoleResource(roleId, resourceIds);
        }
    }

    @Override
    @Transactional
    public void bathAddRoleRoute(Long roleId, List<Long> routeIds) {
        for (Long routeId : routeIds) {
            RoleRouteDO RoleRouteDO = new RoleRouteDO(null, roleId, routeId);
            roleRouteMapper.insert(RoleRouteDO);
        }
    }

    @Override
    @Transactional
    public void bathAddRoleResource(Long roleId, List<Long> resourceIds) {
        for (Long resourceId : resourceIds) {
            RoleResourceDO roleResourceDO = new RoleResourceDO(null, roleId, resourceId);
            roleResourceMapper.insert(roleResourceDO);
        }
    }

}
