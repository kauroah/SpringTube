// ReactionDto.java
package org.example.springtube.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReactionDto {
//    private Long videoId;
    private int numberOfLikes;
    private int numberOfDislikes;









}
