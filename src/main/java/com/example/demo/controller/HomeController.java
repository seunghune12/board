package com.example.demo.controller;

import com.example.demo.dto.BoardDTO;
import com.example.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String afterIndex(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);

        return "freeBoard";
    }

    @GetMapping("/detail")
    public String detail11() {
        return "freeDetail";
    }

    @GetMapping("/newWrite")
    public String write() {
        return "freeWrite";
    }


    @GetMapping("/freeDetail")
    public String detail() {
        return "freeDetail";
    }
}


