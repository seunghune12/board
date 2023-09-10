package com.example.demo.service;

import com.example.demo.Repository.BoardRepository;
import com.example.demo.Repository.HeartRepository;
import com.example.demo.entity.BoardEntity;
import com.example.demo.entity.HeartEntity;
import com.example.demo.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HeartService {

    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final HeartRepository heartRepository;

    public void addheart(Long boardId, UserEntity user) {

        BoardEntity board = BoardEntity.toSaveEntity(boardService.findById(boardId));
        if (!heartRepository.existsByUserAndBoard(user, board)) {
            board.setLikeCount(board.getLikeCount() + 1);
            heartRepository.save(new HeartEntity(user, board));
        }else
        board.setLikeCount(board.getLikeCount() - 1);
        heartRepository.deleteByUserAndBoard(user, board);
    }

}
