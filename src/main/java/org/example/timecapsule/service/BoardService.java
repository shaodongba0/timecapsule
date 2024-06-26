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

        return boardEntityOptional.map(boardEntity -> {
            if (boardEntity.getViewableDate().isBefore(LocalDateTime.now())) {
                return ResponseBoardDTO.toBoardDTO(boardEntity);
            } else {
                return ResponseBoardDTO.toDTOWithLimitedContent(boardEntity);
            }
        }).orElse(null);
    }


    public List<ResponseBoardDTO> findByLike(RequestBoardDTO requestBoardDTO) {
        List<BoardEntity> boardEntityList = boardRepository.findAll();

        // like가 true인 게시물 필터링
        List<BoardEntity> likedBoards = boardEntityList.stream()
                .filter(BoardEntity::isLike) // BoardEntity의 isLike 메서드를 사용하여 필터링
                .collect(Collectors.toList()); // 필터링된 결과를 List로 수집하여 likedBoards에 저장

    // 필터링된 게시물들을 ResponseBoardDTO로 변환
        List<ResponseBoardDTO> responseBoardDTOList = likedBoards.stream()
                .map(ResponseBoardDTO::toBoardDTO) // ResponseBoardDTO의 toBoardDTO 메서드를 사용하여 BoardEntity를 ResponseBoardDTO로 변환
                .collect(Collectors.toList()); // 변환된 결과를 List로 수집하여 responseBoardDTOList에 저장

        return responseBoardDTOList;
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
