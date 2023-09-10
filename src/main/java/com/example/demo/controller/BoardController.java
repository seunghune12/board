package com.example.demo.controller;

import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.CommentDTO;
import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/save")
    public String saveForm(){

        return "save";
    }


    @GetMapping("/reply")
    public String saveForm(@RequestParam ("id") Long id, Model model){

        if(id != null){
        model.addAttribute("id", id);}

        return "save";
    }

    @PostMapping("/save")
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

    @GetMapping("/list")

    public String findAll(Model model){

        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

    @GetMapping("{id}")
    public String saveComment(@PathVariable Long id, Model model,
                            @PageableDefault(page=1) Pageable pageable){
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
//      댓글 목록 가져오기
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList", commentDTOList);

        model.addAttribute("board", boardDTO);
        model.addAttribute("page",pageable.getPageNumber());
        return "detail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model, Principal principal){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate",boardDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model){
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        return "detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1)
                            @SortDefault.SortDefaults({
                             @SortDefault(sort="grp", direction= Sort.Direction.DESC),
                             @SortDefault(sort="seq", direction=Sort.Direction.ASC)}) Pageable pageable, Model model){
        pageable.getPageNumber(); //page == 1
        Page<BoardDTO> boardList = boardService.paging(pageable);  //page == 0
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "paging";

    }

    @PostMapping("/like")
    public void handleLikeOrDislike(@RequestParam("boardId") Long id,@RequestParam("boardWriter") String writer,
                                                     @RequestParam("action") String action, Principal principal, User user) {

//        if (principal.getName().equals(writer)) return null;

        if(action == "like"){
            boardService.updateLike(id);
        }else{
            boardService.updateDislike(id);
        }

    }




}//end class
