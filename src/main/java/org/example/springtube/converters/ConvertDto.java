package org.example.springtube.converters;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ConvertDto {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
}
