package org.shanzhaozhen.bestapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.shanzhaozhen.bestcommon.converter.UserConverter;
import org.shanzhaozhen.bestcommon.form.UserForm;
import org.shanzhaozhen.bestcommon.vo.ResultObject;
import org.shanzhaozhen.bestservice.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "用户注册接口")
@RestController
public class RegisterController {

    private final String REGISTER = "/register";
    private final String CHECK_USERNAME = "/register/{username}";

    private final UserService sysUserService;

    public RegisterController(UserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @PostMapping(REGISTER)
    @ApiOperation("用户注册接口")
    public ResultObject<Long> register(@RequestBody @Validated UserForm userForm) {
        return ResultObject.build(result -> sysUserService.register(UserConverter.formToDTO(userForm)));
    }

    @GetMapping(CHECK_USERNAME)
    @ApiOperation("检查用户是否已被注册")
    public ResultObject<Boolean> checkUserName(@PathVariable("username") @ApiParam("用户名") String username) {
        return ResultObject.build(result -> sysUserService.isExistUser(username));
    }

}
