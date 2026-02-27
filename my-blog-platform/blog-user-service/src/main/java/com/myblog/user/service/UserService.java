package com.myblog.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myblog.user.dto.UserLoginDTO;
import com.myblog.user.dto.UserRegisterDTO;
import com.myblog.user.entity.User;

public interface UserService extends IService<User> {

    /**
     * User registration
     * @param userRegisterDTO user registration info
     */
    void register(UserRegisterDTO userRegisterDTO);

    /**
     * User login
     * @param userLoginDTO user login info
     * @return JWT token
     */
    String login(UserLoginDTO userLoginDTO);
}
