package com.example.demo.entity;

import com.example.demo.entity.BoardEntity;
import com.example.demo.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Heart") //이거 이름 안바꿔 주면 충돌남 SQL 예약어라서..
@Setter
@Getter
@NoArgsConstructor
public class HeartEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserEntity user;
    // MEMBER_ID

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private BoardEntity board;


    //likeId는 생성자가 필요없어서 @AllArgsConstructor 사용 안했음.
    public HeartEntity(UserEntity user, BoardEntity board) {
        this.user = user;
        this.board = board;

    }
}