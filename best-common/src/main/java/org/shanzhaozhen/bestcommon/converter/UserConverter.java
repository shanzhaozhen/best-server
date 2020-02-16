package org.shanzhaozhen.bestcommon.converter;

import org.shanzhaozhen.bestcommon.domain.sys.UserDO;
import org.shanzhaozhen.bestcommon.form.UserForm;
import org.shanzhaozhen.bestcommon.dto.UserDTO;
import org.shanzhaozhen.bestcommon.vo.UserVO;
import org.springframework.beans.BeanUtils;

public class UserConverter {

    public static UserDTO formToDTO(UserForm userForm) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userForm, userDTO);
        return userDTO;
    }

    public static UserDO dtoToDO(UserDTO userDTO) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO, userDO);
        return userDO;
    }

    public static UserDTO doToDTO(UserDO userDO) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDO, userDTO);
        return userDTO;
    }

    public static UserVO dtoToVO(UserDTO userDTO) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDTO, userVO);
        return userVO;
    }

    public static UserDTO voToDTO(UserVO userVO) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userVO, userDTO);
        return userDTO;
    }

}
