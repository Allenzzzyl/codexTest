package com.myblog.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myblog.user.dto.UserLoginDTO;
import com.myblog.user.dto.UserRegisterDTO;
import com.myblog.user.entity.User;
import com.myblog.user.exception.BizException;
import com.myblog.user.mapper.UserMapper;
import com.myblog.user.security.JwtTokenService;
import com.myblog.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public UserServiceImpl(PasswordEncoder passwordEncoder, JwtTokenService jwtTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        Long count = this.baseMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, userRegisterDTO.getUsername()));
        if (count > 0) {
            throw new BizException("Username already exists");
        }

        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        this.baseMapper.insert(user);
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        User user = this.baseMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, userLoginDTO.getUsername()));
        if (user == null) {
            throw new BizException("User not found");
        }

        boolean match = passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword());
        if (!match) {
            // Backward compatibility for legacy MD5 passwords; upgrade hash after successful login.
            String md5Password = DigestUtils.md5DigestAsHex(userLoginDTO.getPassword().getBytes());
            if (md5Password.equals(user.getPassword())) {
                user.setPassword(passwordEncoder.encode(userLoginDTO.getPassword()));
                user.setUpdateTime(LocalDateTime.now());
                this.baseMapper.updateById(user);
                match = true;
            }
        }
        if (!match) {
            throw new BizException("Invalid password");
        }

        return jwtTokenService.generateToken(user.getId(), user.getUsername());
    }
}
