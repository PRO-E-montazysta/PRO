package com.emontazysta.model.searchcriteria;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ToolReleaseSearchCriteria {

    private Long id;
    private LocalDateTime releaseTime;
    private LocalDateTime returnTime;
    private Long receivedBy_Id;
    private Long releasedBy;
    private Long tool;
    private Long demandAdHoc;
    private Long orderStage;


}
