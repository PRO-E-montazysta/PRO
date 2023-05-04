package com.emontazysta.model.dto.filterDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandAdHocFilterDto {

    private Long id;
    @NotBlank(message = "Description cannot be empty")
    private String description;
    private LocalDateTime createdAt;
    private String createdByName;
    private String orderStageName;
}
