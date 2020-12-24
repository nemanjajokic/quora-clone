package io.neca.quoraclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerResponse {
    private int id;
    private String body;
    private int questionId;
    private String userName;
    private String duration;
}
