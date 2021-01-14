package io.neca.quoraclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponse {
    private int id;
    private String name;
    private String description;
    private String topicName;
    private String userName;
    private String imageUri;
    private String duration;
}
