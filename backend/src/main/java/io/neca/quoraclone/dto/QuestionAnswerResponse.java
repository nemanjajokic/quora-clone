package io.neca.quoraclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionAnswerResponse {
    private int id;
    private String name;
    private String description;
    private String topicName;
    private String userName;
    private String imageUri;
    private String duration;
    private List<AnswerResponse> answers;
}
