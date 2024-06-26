package org.example.timecapsule.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.timecapsule.dto.request.RequestBoardDTO;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@Setter
@Table(name = "board_table")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updatedDate;

    @Column
    private LocalDateTime viewableDate;

    @Column
    private boolean like;

    public static BoardEntity toArticleEntity(RequestBoardDTO requestBoardDTO) {
        BoardEntity boardEntity = new BoardEntity();

//        boardEntity.setId(requestBoardDTO.getId());
        boardEntity.setTitle(requestBoardDTO.getTitle());
        boardEntity.setContent(requestBoardDTO.getContent());
        boardEntity.setCreatedDate(LocalDateTime.now());

//        boardEntity.setViewableDate(boardEntity.getCreatedDate().plusYears(1));

        return  boardEntity;
    }

//    private boolean favorite;

}
