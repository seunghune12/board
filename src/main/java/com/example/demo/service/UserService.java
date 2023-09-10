package com.example.demo.service;

import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${jwt.token.secret")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L;

    public String join(UserDTO userDTO) {

//        userName 중복 check
        Optional<UserEntity> existingUser = Optional.ofNullable(userRepository.findByUsername(userDTO.getUsername()));
        if (existingUser.isPresent()) {
            throw new AppException(ErrorCode.USERNAME_DUPLICATED, userDTO.getUsername() + "는 이미 있습니다.");
        }

//        저장
        UserEntity userEntity = UserEntity.toSaveEntity(userDTO);

        userRepository.save(userEntity);

        return "SUCCESS";
    }

    public UserEntity findByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        return userEntity;
    }

    public String login(UserDTO userDTO) {
//        userName 없음
        Optional<UserEntity> userOptional = Optional.ofNullable(userRepository.findByUsername(userDTO.getUsername()));

        if (!userOptional.isPresent()) {
            throw new AppException(ErrorCode.USERNAME_NOT_FOUND, userDTO.getUsername() + "이 없습니다");
        }

        UserEntity selectedUser = userOptional.get();
//        password 틀림
        if(!bCryptPasswordEncoder.matches(userDTO.getPassword(), selectedUser.getPassword()))
            throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력 했습니다");
//  `   앞에서 Excetion이 안 났으면 토큰 발행
        String token = JwtTokenUtil.createToken(userDTO.getUsername(), key, expireTimeMs);
        return token;
    }
}
