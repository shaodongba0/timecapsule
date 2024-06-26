package org.example.timecapsule.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestBoardDTO {

    private UUID id;

    private String title;

    private String content;

    private boolean like;

}
