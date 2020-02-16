package org.shanzhaozhen.bestserver.converter;

import com.google.common.base.Converter;
import org.shanzhaozhen.bestserver.domain.sys.UserDO;
import org.shanzhaozhen.bestserver.dto.UserDTO;
import org.springframework.beans.BeanUtils;

public class UserDTOConverter extends Converter<UserDTO, UserDO> {

    @Override
    protected UserDO doForward(UserDTO userDTO) {
        UserDO user = new UserDO();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }

    @Override
    protected UserDTO doBackward(UserDO userDO) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDO, userDTO);
        return userDTO;
    }
}
