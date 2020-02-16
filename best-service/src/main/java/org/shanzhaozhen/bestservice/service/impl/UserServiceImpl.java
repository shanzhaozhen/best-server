package org.shanzhaozhen.bestservice.service.impl;

import org.shanzhaozhen.bestcommon.converter.RoleConverter;
import org.shanzhaozhen.bestcommon.domain.sys.UserDO;
import org.shanzhaozhen.bestcommon.vo.UserInfo;
import org.shanzhaozhen.bestcommon.dto.UserDTO;
import org.shanzhaozhen.bestrepo.mapper.UserMapper;
import org.shanzhaozhen.bestservice.service.RouteService;
import org.shanzhaozhen.bestservice.service.UserService;
import org.shanzhaozhen.bestcommon.utils.PasswordUtils;
import org.shanzhaozhen.bestcommon.utils.UserDetailsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final RouteService routeService;

    public UserServiceImpl(RouteService routeService, UserMapper userMapper) {
        this.routeService = routeService;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO getUserByUserId(Long userId) {
        return userMapper.getUserByUserId(userId);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public UserDTO getCurrentUser() {
        UserDTO userDTO = userMapper.getUserAndRolesByUserId(UserDetailsUtils.getUserId());
        Assert.notNull(userDTO, "没有找到当前用户信息");
        return userDTO;
    }

    @Override
    @Transactional
    public Long register(UserDTO userDTO) {
        Assert.isNull(userMapper.selectUserByUsername(userDTO.getUsername()), "注册失败，该用户名已存在！");
        UserDO newUser = new UserDO();
        BeanUtils.copyProperties(userDTO, newUser, "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled");
        newUser.setPassword(PasswordUtils.encryption(userDTO.getPassword()));
        userMapper.insert(newUser);
        return newUser.getId();
    }

    @Override
    public Boolean isExistUser(String username) {
        UserDO userDO = userMapper.selectUserByUsername(username);
        return userDO == null;
    }

    public UserInfo getUserInfo() {
        UserDTO userDTO = this.getCurrentUser();
        return new UserInfo(userDTO.getNickname(),
                            userDTO.getAvatar(),
                            userDTO.getIntroduction(),
                            RoleConverter.toBase(userDTO.getRoles()),
                            routeService.getRoutesByCurrentUser());
    }

}
