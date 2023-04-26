package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Comparator;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolHistoryDto implements Comparable<ToolHistoryDto> {

    private String orderStageName;
    private LocalDateTime orderStageStartDate;
    private LocalDateTime orderStageEndDate;
    private String foremanName;

    @Override
    public int compareTo(ToolHistoryDto o) {
        return this.orderStageStartDate.compareTo(o.getOrderStageStartDate());
    }
}
