package com.example.demo.controller;


import com.example.demo.entity.UserEntity;
import com.example.demo.service.HeartService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;
    private final UserService userService;

    @PostMapping("heart/{boardId}")
    public ResponseEntity addLike(@PathVariable("boardId") Long boardId,
                                  @AuthenticationPrincipal String email ) {
        //이메일을 불러옴
        UserEntity user = userService.findByEmail(email);
        //id 랑 멤버 추가해 버림
        heartService.addheart(boardId,user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}