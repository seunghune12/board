package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public String login(){
        return "login";
    }
//
//    @PostMapping("login")
//    public UserEntity login(@RequestBody UserDTO userDTO){
//        UserEntity user = userService.login(userDTO);
//        return user;
//    }

    @GetMapping("/join")
    public String join()  {
        return "join";  }

    @PostMapping("/join")
    public ResponseEntity<String> join(@ModelAttribute UserDTO userDTO){
        userDTO.setRole("USER");
        String rawPassword = userDTO.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        userDTO.setPassword(encPassword);
        userService.join(userDTO);
        return ResponseEntity.ok().body("회원가입이 성공 했습니다.");
    }



    @GetMapping(value = "/exception")
    public void exceptionTest() throws RuntimeException{
        throw new RuntimeException("접근 실패!");
    }


}


