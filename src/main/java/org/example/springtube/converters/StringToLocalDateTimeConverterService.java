package org.example.springtube.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StringToLocalDateTimeConverterService implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        // Creating a DateTimeFormatter with the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Parsing the String source into a LocalDateTime object using the formatter
        return LocalDateTime.parse(source, formatter);
    }
}
