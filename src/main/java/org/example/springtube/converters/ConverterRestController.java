package org.example.springtube.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/convert", produces = "application/json")
public class ConverterRestController {

    @Autowired
    private ConverterService converterService;

    @GetMapping("/filter")
    public List<ConvertDto> getFiltered(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalTime startTime,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) LocalTime endTime) {
        System.out.println("startDate: " + startDate + ", startTime: " + startTime + ", endDate: " + endDate + ", endTime: " + endTime);

        return converterService.getFilteredData(startDate, startTime, endDate, endTime);
    }
}