package org.shanzhaozhen.bestserver.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.shanzhaozhen.bestserver.converter.RoleConverter;
import org.shanzhaozhen.bestserver.dto.RoleDTO;
import org.shanzhaozhen.bestserver.form.BaseSearchForm;
import org.shanzhaozhen.bestserver.form.RoleForm;
import org.shanzhaozhen.bestserver.service.RoleService;
import org.shanzhaozhen.bestserver.vo.ResultObject;
import org.shanzhaozhen.bestserver.vo.RoleVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api("用户角色接口")
@RestController
public class RoleController {

    private final String GET_ROLE_PAGE = "/role/page";
    private final String GET_ROLE_BY_ID = "/role/{roleId}";
    private final String ADD_ROLE = "/role";
    private final String UPDATE_ROLE = "/role";
    private final String DELETE_ROLE = "/role/{roleId}";

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(GET_ROLE_PAGE)
    @ApiOperation("获取角色信息（分页）")
    public ResultObject<Page<RoleVO>> getRolePage(@RequestBody BaseSearchForm<RoleDTO> baseSearchForm) {
        return ResultObject.build(result -> RoleConverter.toVO(roleService.getRolePage(baseSearchForm)));
    }

    @GetMapping(GET_ROLE_BY_ID)
    @ApiOperation("获取角色信息（通过角色id）")
    public ResultObject<RoleVO> getRoleByRoleId(@PathVariable("roleId") @ApiParam(name = "角色id", example = "1") Long roleId) {
        return ResultObject.build(result -> RoleConverter.toVO(roleService.getRoleById(roleId)));
    }

    @PostMapping(ADD_ROLE)
    @ApiOperation("添加角色接口")
    public ResultObject<RoleVO> addRole(@RequestBody @Validated({Insert.class}) RoleForm roleForm) {
        return ResultObject.build(result -> RoleConverter.toVO(roleService.addRole(RoleConverter.toDTO(roleForm))));
    }

    @PutMapping(UPDATE_ROLE)
    @ApiOperation("更新角色接口")
    public ResultObject<RoleVO> updateRole(@RequestBody @Validated({Update.class}) RoleForm roleForm) {
        return ResultObject.build(result -> RoleConverter.toVO(roleService.updateRole(RoleConverter.toDTO(roleForm))));
    }

    @DeleteMapping(DELETE_ROLE)
    @ApiOperation("删除角色接口")
    public ResultObject<Boolean> deleteRole(@PathVariable("roleId") @ApiParam(name = "角色id", example = "1") Long roleId) {
        return ResultObject.build(result -> roleService.deleteRole(roleId));
    }

}
