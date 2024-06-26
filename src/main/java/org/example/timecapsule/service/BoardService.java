package org.example.timecapsule.service;

import lombok.RequiredArgsConstructor;
import org.example.timecapsule.dto.request.RequestBoardDTO;
import org.example.timecapsule.dto.response.ResponseBoardDTO;
import org.example.timecapsule.entity.BoardEntity;
import org.example.timecapsule.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class BoardService {

    private final BoardRepository boardRepository;

    public void save(RequestBoardDTO requestBoardDTO) {
        // 1. dto -> entity 변환
        // 2. repository의 save 메서드 호출
        BoardEntity boardEntity = BoardEntity.toArticleEntity(requestBoardDTO);
        boardRepository.save(boardEntity);
    }

    public List<ResponseBoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<ResponseBoardDTO> responseBoardDTOList = new ArrayList<>();
        responseBoardDTOList.add(ResponseBoardDTO.toBoardDTO((BoardEntity) boardEntityList));

        return responseBoardDTOList;
    }

    public ResponseBoardDTO findById(RequestBoardDTO requestBoardDTO) {
        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(requestBoardDTO.getId());

        if (boardEntityOptional.isPresent()) {
            BoardEntity boardEntity = boardEntityOptional.get();
            // viewableDate가 지났으면 전체 내용 반환
            if (boardEntity.getViewableDate().isBefore(LocalDateTime.now())) {
                return ResponseBoardDTO.toBoardDTO(boardEntity);
            } else { // viewableDate가 지나지 않았으면 제한된 내용 반환
                return ResponseBoardDTO.toDTOWithLimitedContent(boardEntity);
            }
        } else {
            return null;
        }

//        ResponseBoardDTO responseBoardDTO = ResponseBoardDTO.toBoardDTO(boardEntity);
//        return responseBoardDTO;
    }



    public void deleteById(RequestBoardDTO requestBoardDTO) {
        boardRepository.deleteById(requestBoardDTO.getId());
    }

    public void update(RequestBoardDTO requestBoardDTO) throws Exception {
        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(requestBoardDTO.getId());

        if (boardEntityOptional.isPresent()) {

            BoardEntity boardEntity = boardEntityOptional.get();
            LocalDateTime originalCreatedDate = boardEntity.getCreatedDate();

            if (boardEntity.getViewableDate().isBefore(LocalDateTime.now())) {

                boardEntity = BoardEntity.builder()
                        .title(requestBoardDTO.getTitle())
                        .content(requestBoardDTO.getContent())
                        .createdDate(originalCreatedDate)
                        .updatedDate(LocalDateTime.now())
                        .build();
                boardRepository.save(boardEntity);

            } else {
                boardEntity = BoardEntity.builder()
                        .title(requestBoardDTO.getTitle())
                        .createdDate(originalCreatedDate)
                        .updatedDate(LocalDateTime.now())
                        .build();
                boardRepository.save(boardEntity);
            }
        } else {
            throw new RuntimeException(); // notfound error로 바꿔야함
        }
    }


}
