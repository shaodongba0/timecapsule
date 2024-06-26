package org.example.timecapsule.controller;

import lombok.RequiredArgsConstructor;
import org.example.timecapsule.dto.request.RequestBoardDTO;
import org.example.timecapsule.dto.response.ResponseBoardDTO;
import org.example.timecapsule.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/save")
    public String save(RequestBoardDTO requestBoardDTO) {
        boardService.save(requestBoardDTO);
        return "detail";
    }

    @GetMapping("/findAll")
    public List findAll() {
        List<ResponseBoardDTO> responseBoardDTOList = boardService.findAll();
        return responseBoardDTOList;
    }

    @GetMapping("/findById/{id}")
    public ResponseBoardDTO findById(RequestBoardDTO requestBoardDTO) {
       ResponseBoardDTO responseBoardDTO = boardService.findById(requestBoardDTO);
       return responseBoardDTO;
    }

    @GetMapping({"/findByLike"})
    public List findByLike(RequestBoardDTO requestBoardDTO) {
        List<ResponseBoardDTO> responseBoardDTOList = boardService.findByLike(requestBoardDTO);
        return responseBoardDTOList;
    }

    @GetMapping("/{id}/delete")
    public String deleteById(RequestBoardDTO requestBoardDTO) {
        boardService.deleteById(requestBoardDTO);
        return "list";
    }

    @PatchMapping("/{id}/update")
    public String update(RequestBoardDTO requestBoardDTO) throws Exception {
        boardService.update(requestBoardDTO);
        return "redirect:/board/" + requestBoardDTO.getId();
    }


    @GetMapping("/like/{id}")
    public String addLike(@PathVariable UUID id, RequestBoardDTO requestBoardDTO) {
        boardService.addLike(id, requestBoardDTO);
        return "detail";
    }

    @GetMapping("/unlike/{id}")
    public String deleteLike(@PathVariable UUID id, RequestBoardDTO requestBoardDTO) {
        boardService.deleteLike(id, requestBoardDTO);
        return "detail";
    }
}
