package org.example.springtube.converters;

import lombok.*;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    private String datePattern = "yyyy-MM-dd";

    @Override
    public LocalDate convert(String dateString) {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(datePattern));
    }
}