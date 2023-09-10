package com.example.demo.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartDTO {

    private Long memberId;
    private Long boardId;

    public HeartDTO(Long memberId, Long boardId) {
        this.memberId = memberId;
        this.boardId = boardId;
    }
}

