package org.shanzhaozhen.bestrepo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.shanzhaozhen.bestcommon.domain.sys.UserDO;
import org.shanzhaozhen.bestcommon.dto.UserDTO;

public interface UserMapper extends BaseMapper<UserDO> {

    @Select("select * from sys_user where username = #{username}")
    UserDO selectUserByUsername(@Param("username") String username);

    @Select("select count(*) from sys_user where username = #{username}")
    UserDO checkUserByUsername(@Param("username") String username);

    @Select("select count(*) from sys_user where username = #{username}")
    Integer countByUsername(@Param("username") String username);


    @Select("select id, username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, " +
            "name, nickname, sex, birthday, avatar, email, phone_number, address, introduction from sys_user where id = #{id}")
    UserDTO getUserByUserId(@Param("id") Long id);

    UserDTO getUserAndRolesByUserId(@Param("id") Long id);

    @Select("select id, username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, " +
            "name, nickname, sex, birthday, avatar, email, phone_number, address, introduction from sys_user where username = #{username}")
    UserDTO getUserByUsername(@Param("username") String username);

}
