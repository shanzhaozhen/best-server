package org.shanzhaozhen.bestapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.shanzhaozhen.bestcommon.converter.UserConverter;
import org.shanzhaozhen.bestcommon.dto.UserDTO;
import org.shanzhaozhen.bestcommon.form.BaseSearchForm;
import org.shanzhaozhen.bestcommon.form.UserForm;
import org.shanzhaozhen.bestcommon.vo.ResultObject;
import org.shanzhaozhen.bestcommon.vo.UserVO;
import org.shanzhaozhen.bestcommon.vo.UserInfo;
import org.shanzhaozhen.bestservice.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api("用户信息接口")
@RestController
public class UserController {

    private final String GET_USER_INFO = "/user/info";
    private final String LOGOUT = "/user/logout";
    private final String GET_USER_PAGE = "/user/page";
    private final String GET_USER_BY_ID = "/user/{userId}";
    private final String ADD_USER = "/user";
    private final String UPDATE_USER = "/user";
    private final String DELETE_USER = "/user/{userId}";


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(GET_USER_INFO)
    @ApiOperation("获取当前登录用户信息接口")
    public ResultObject<UserInfo> getUserInfo() {
        return ResultObject.build(result -> userService.getUserInfo());
    }

    @GetMapping(LOGOUT)
    @ApiOperation("登出用户接口")
    public ResultObject<Boolean> logout() {
        return ResultObject.build(result -> true);
    }

    @PostMapping(GET_USER_PAGE)
    @ApiOperation("获取用户信息（分页）")
    public ResultObject<Page<UserVO>> getDynamicScheduledTaskPage(@RequestBody BaseSearchForm<UserDTO> baseSearchForm) {
        return ResultObject.build(result -> UserConverter.toVO(userService.getUserPage(baseSearchForm)));
    }

    @GetMapping(GET_USER_BY_ID)
    @ApiOperation("获取角色信息（通过角色id）")
    public ResultObject<UserVO> getUserByUserId(@PathVariable("userId") @ApiParam(name = "角色id", example = "1") Long userId) {
        return ResultObject.build(result -> UserConverter.toVO(userService.getUserById(userId)));
    }

    @PostMapping(ADD_USER)
    @ApiOperation("添加角色接口")
    public ResultObject<Long> addUser(@RequestBody @Validated({Insert.class}) UserForm userForm) {
        return ResultObject.build(result -> userService.addUser(UserConverter.toDTO(userForm)));
    }

    @PutMapping(UPDATE_USER)
    @ApiOperation("更新角色接口")
    public ResultObject<Long> updateUser(@RequestBody @Validated({Update.class}) UserForm userForm) {
        return ResultObject.build(result -> userService.updateUser(UserConverter.toDTO(userForm)));
    }

    @DeleteMapping(DELETE_USER)
    @ApiOperation("删除角色接口")
    public ResultObject<Long> deleteUser(@PathVariable("userId") @ApiParam(name = "角色id", example = "1") Long userId) {
        return ResultObject.build(result -> userService.deleteUser(userId));
    }

}
