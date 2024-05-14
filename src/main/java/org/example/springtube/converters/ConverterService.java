package org.example.springtube.converters;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConverterService {

    private List<ConvertDto> data = new ArrayList<>();

    public List<ConvertDto> getFilteredData(
            LocalDate startDate,
            LocalTime startTime,
            LocalDate endDate,
            LocalTime endTime) {


        List<ConvertDto> filteredData = new ArrayList<>();
        for (ConvertDto dto : data) {
            LocalDate dtoStartDate = dto.getStartDate();
            LocalTime dtoStartTime = dto.getStartTime();
            LocalDate dtoEndDate = dto.getEndDate();
            LocalTime dtoEndTime = dto.getEndTime();

            boolean dateInRange = (startDate == null || dtoStartDate.isEqual(startDate) || dtoStartDate.isAfter(startDate))
                    && (endDate == null || dtoEndDate.isEqual(endDate) || dtoEndDate.isBefore(endDate));

            boolean timeInRange = (startTime == null || dtoStartTime == null || dtoStartTime.isAfter(startTime))
                    && (endTime == null || dtoEndTime == null || dtoEndTime.isBefore(endTime));

            if (dateInRange && timeInRange) {
                filteredData.add(dto);
            }
        }
        return filteredData;
    }
}
