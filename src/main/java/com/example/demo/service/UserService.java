package com.example.demo.service;

import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.SignUpResultDTO;
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

    public UserEntity login(UserDTO userDTO) {
        UserEntity user = userRepository.findByUsername(userDTO.getUsername());

        //패스워드 비교
        if(!bCryptPasswordEncoder.matches(userDTO.getPassword(),user.getPassword())){
            throw new RuntimeException();
        }

        return user;

    }



//    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
//    private void setSuccessResult(SignUpResultDTO result) {
//        result.setSuccess(true);
//        result.setCode(CommonResponse.SUCCESS.getCode());
//        result.setMsg(CommonResponse.SUCCESS.getMsg());
//    }
//
//    // 결과 모델에 api 요청 실패 데이터를 세팅해주는 메소드
//    private void setFailResult(SignUpResultDTO result) {
//        result.setSuccess(false);
//        result.setCode(CommonResponse.FAIL.getCode());
//        result.setMsg(CommonResponse.FAIL.getMsg());
//    }
}
