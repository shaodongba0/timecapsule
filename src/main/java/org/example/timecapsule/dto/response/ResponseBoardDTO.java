package org.example.timecapsule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.timecapsule.entity.BoardEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBoardDTO {

    private UUID id;

    private String title;

    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime viewableDate;

    private boolean like;


    public static ResponseBoardDTO toBoardDTO(BoardEntity boardEntityList) {
        ResponseBoardDTO responseBoardDTO = new ResponseBoardDTO();

//        responseBoardDTO.setId(boardEntityList.getId());
        responseBoardDTO.setTitle(boardEntityList.getTitle());
        responseBoardDTO.setContent(boardEntityList.getContent());
        responseBoardDTO.setCreatedDate(boardEntityList.getCreatedDate());
        responseBoardDTO.setUpdatedDate(boardEntityList.getUpdatedDate());
        responseBoardDTO.setLike(boardEntityList.isLike());
//        responseBoardDTO.setViewableDate(boardEntityList.getViewableDate());

        return responseBoardDTO;
    }

    public static ResponseBoardDTO toDTOWithLimitedContent(BoardEntity boardEntity) {
        ResponseBoardDTO responseBoardDTO = new ResponseBoardDTO();

        responseBoardDTO.setTitle(boardEntity.getTitle());
        responseBoardDTO.setCreatedDate(boardEntity.getCreatedDate());
        responseBoardDTO.setUpdatedDate(boardEntity.getUpdatedDate());

        return responseBoardDTO;
    }

//    private boolean favorite;

}
