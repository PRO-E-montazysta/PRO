package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfAttachment;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class AttachmentDto {

    private Long id;
    private String name;
    private String url;
    private String description;
    private TypeOfAttachment typeOfAttachment;
    private Date createdAt;
}
