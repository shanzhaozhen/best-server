package org.shanzhaozhen.bestserver.service.impl;

import org.shanzhaozhen.bestserver.converter.RoleConverter;
import org.shanzhaozhen.bestserver.domain.sys.UserDO;
import org.shanzhaozhen.bestserver.dto.UserDTO;
import org.shanzhaozhen.bestserver.mapper.UserMapper;
import org.shanzhaozhen.bestserver.service.RouteService;
import org.shanzhaozhen.bestserver.service.UserService;
import org.shanzhaozhen.bestserver.utils.PasswordUtils;
import org.shanzhaozhen.bestserver.utils.UserDetailsUtils;
import org.shanzhaozhen.bestserver.vo.UserInfo;
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
