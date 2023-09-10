package com.example.demo.service;


import com.example.demo.Repository.BoardFileRepository;
import com.example.demo.Repository.BoardRepository;
import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.BoardEntity;
import com.example.demo.entity.BoardFileEntity;
import com.example.demo.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    public void save(BoardDTO boardDTO) throws IOException {

        if(boardDTO.getBoardFile()!=null){
            MultipartFile boardFile = boardDTO.getBoardFile();
            String originalFileName = boardFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "-" + originalFileName;
            String savePath = "/Users/mac/Documents/apps/spring/demo/src/main/resources/static/files";
            boardFile.transferTo(new File(savePath));
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity board = boardRepository.findById(savedId).get();

            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFileName, storedFileName);
            boardFileRepository.save(boardFileEntity);
        }else{
        BoardEntity boardEntity = BoardEntity.toSaveEntity((boardDTO));
        boardRepository.save(boardEntity);}
    }

    public BoardDTO resultDTO(BoardDTO boardDTO){
        BoardEntity saveEntity = BoardEntity.toSaveEntity(boardDTO);
        BoardEntity resultEntity= boardRepository.save(saveEntity);
        BoardDTO resultDTO = BoardDTO.toBoardDTO(resultEntity);
        return resultDTO;
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }
    @Transactional
    public void updateHits(Long id){
        boardRepository.updateHist((id));
    }

    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById((id));
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        }else{
            return null;
        }
    }
    public BoardDTO update(BoardDTO boardDTO){
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page= pageable.getPageNumber() -1;
        int pageLimit = 10;

        Sort sort = Sort.by(
                Sort.Order.desc("grp"),
                Sort.Order.asc("seq"));

        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, sort));

        Page<BoardDTO> boardDTOS =
                boardEntities.map(board-> new BoardDTO(board.getId(),board.getUser(),board.getBoardTitle() ,board.getBoardHits(),board.getCreatedTime(),board.getGrp(),board.getSeq(),board.getDep()
                                ,board.getBoardLike(),board.getBoardDislike()));

        return boardDTOS;
    }

    public Long getAvailableSeq(BoardDTO originDTO){
        BoardEntity originEntity = BoardEntity.toUpdateEntity(originDTO);
        Long seq = boardRepository.getAvailableSeq(originEntity);

        return seq;
    }

    public Long countByGrp(BoardDTO originDTO){
        Long Grp = boardRepository.countByGrp(originDTO.getGrp());
        return Grp;
    }

    public void increaseSequence(BoardDTO increaseDTO){
        BoardEntity increaseEntity = BoardEntity.toUpdateEntity(increaseDTO);
        boardRepository.increaseSequence(increaseEntity);
    }

    public void moidifyDep(Long id){
        boardRepository.modifyDep(id);
    }

    public void updateLike(Long id) {
        boardRepository.updateLike(id);
    }

    public void updateDislike(Long id) {
        boardRepository.updateDisike(id);
    }
}
