package org.example.springtube.converters;

import lombok.*;
import org.springframework.core.convert.converter.Converter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StringToLocalTimeConverter implements Converter<String, LocalTime> {
    private String timePattern = "HH:mm";

    @Override
    public LocalTime convert(String timeString) {
        return LocalTime.parse(timeString, DateTimeFormatter.ofPattern(timePattern));
    }
    
}
