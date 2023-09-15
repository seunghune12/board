package com.example.demo.controller;

import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class freeController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping(value = "/freeWrite")
    public String freeWrite(@AuthenticationPrincipal UserDTO user, Model model){

        model.addAttribute("user", user);
        return "freeWrite";
    }


    @PostMapping("/freeWrite")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        //새글일 경우 - 등록 후 grp를 번호와 동일하게 갱신
        //답글일 경우 - 원본글 정보를 이용하여 grp, seq ,dep를 계산

        boolean isReply = boardDTO.getId() != null;

        if(isReply) {//답글일 경우 - grp,seq,dep 계산
            BoardDTO origin = boardService.findById(boardDTO.getId());

            Long seq = boardService.getAvailableSeq(origin);
            if (seq == null) {
                seq = boardService.countByGrp(origin);
                origin.setSeq(seq);
            } else {
                BoardDTO increaseDTO = new BoardDTO();
                increaseDTO.setGrp(origin.getGrp());
                increaseDTO.setSeq(origin.getSeq());
                boardService.increaseSequence(increaseDTO);

            }//if-else

            boardDTO.setId(null);
            boardDTO.setGrp(origin.getGrp());
            boardDTO.setSeq(origin.getSeq());
            boardDTO.setDep(origin.getDep() + 1);

        }//if

        BoardDTO result = boardService.resultDTO(boardDTO);

        if (!isReply) {//새글일 경우 - grp 갱신
            boardService.moidifyDep(result.getId());

        }


        return "index";
    }
}
