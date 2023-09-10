package com.example.demo.entity;

import com.example.demo.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


//DB 테이블 역활을 하는 클래스
@Entity
@Getter
@Setter
@Table( name ="board_table")
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String boardWriter;


    @Column
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    @Column
    private int fileAttached;

    @Column
    private Long grp;

    @Column
    private Long seq;

    @Column
    private Long dep;

    @Column
    private Long BoardLike;

    @Column
    private Long BoardDislike;
    @Column
    private Integer likeCount =0;

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntities = new ArrayList<>();

    public static BoardEntity toSaveEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setUser(boardDTO.getUser());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(0);
        boardEntity.setDep(boardDTO.getDep());
        boardEntity.setGrp(boardDTO.getGrp());
        boardEntity.setSeq(boardDTO.getSeq());
        boardEntity.setBoardLike(boardDTO.getLike());
        boardEntity.setBoardDislike(boardDTO.getDislike());

        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setUser(boardDTO.getUser());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setDep(boardDTO.getDep());
        boardEntity.setGrp(boardDTO.getGrp());
        boardEntity.setSeq(boardDTO.getSeq());
        boardEntity.setBoardLike(boardDTO.getLike());
        boardEntity.setBoardDislike(boardDTO.getDislike());
        return boardEntity;
    }

    public static BoardEntity toSaveFileEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setUser(boardDTO.getUser());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(1);

        return boardEntity;
    }

}
