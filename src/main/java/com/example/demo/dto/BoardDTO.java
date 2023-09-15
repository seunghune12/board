package com.example.demo.dto;

import com.example.demo.entity.BoardEntity;
import com.example.demo.entity.UserEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
        private Long id;
        private String username;
        private String boardTitle;
        private String boardContents;
        private int boardHits;
        private LocalDateTime boardCreatedTime;
        private LocalDateTime boardUpdatedTime;
        private MultipartFile boardFile;//save.html -> Controller 파일 담는 용도
        private String originalFileName;
        private String storedFileName;
        private int fileAttached;//파일 첨부 여부(첨부 1, 미첨부 0)
        private String boardPass;
        private Long grp;
        private Long seq;
        private Long dep; //계층형 게시판 : 글 그룹 / 글 순서 / 글 깊이
        private Long like;
        private Long dislike;


        public static BoardDTO toBoardDTO(BoardEntity boardEntity){
                BoardDTO boardDTO = new BoardDTO();
                boardDTO.setId(boardEntity.getId());
                boardDTO.setUsername(boardEntity.getUsername());
                boardDTO.setBoardPass(boardEntity.getBoardPass());
                boardDTO.setBoardTitle(boardEntity.getBoardTitle());
                boardDTO.setBoardContents(boardEntity.getBoardContents());
                boardDTO.setBoardHits(boardEntity.getBoardHits());
                boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
                boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
                boardDTO.setGrp(boardEntity.getGrp());
                boardDTO.setDep(boardEntity.getDep());
                boardDTO.setSeq(boardEntity.getSeq());
                boardDTO.setLike(boardEntity.getBoardLike());
                boardDTO.setDislike(boardEntity.getBoardDislike());


                if(boardEntity.getFileAttached()==0){
                        boardDTO.setFileAttached(boardEntity.getFileAttached());
                }else{
                        boardDTO.setFileAttached(boardEntity.getFileAttached());
                        boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
                        boardDTO.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
                }


                return boardDTO;
        }

        public BoardDTO(Long id, String username, String boardTitle, int boardHits, LocalDateTime boardCreatedTime, Long grp, Long seq, Long dep, Long like, Long dislike) {
                this.id = id;
                this.username = username;
                this.boardTitle = boardTitle;
                this.boardHits = boardHits;
                this.boardCreatedTime = boardCreatedTime;
                this.grp = grp;
                this.seq = seq;
                this.dep = dep;
                this.like = like;
                this.dislike = dislike;
        }
}
