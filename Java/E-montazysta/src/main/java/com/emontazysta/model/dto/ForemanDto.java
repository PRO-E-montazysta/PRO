package com.emontazysta.model.dto;

import com.emontazysta.model.ToolRelease;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class ForemanDto extends FitterDto {

    private List<ToolRelease> toolReleaseList;
}
