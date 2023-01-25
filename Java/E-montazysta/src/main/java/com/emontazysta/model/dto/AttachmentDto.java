package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfAttachment;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import java.util.Date;

@Data
@SuperBuilder
public class AttachmentDto {

    private Long id;
    private String name;
    private String url;
    private String description;
    private TypeOfAttachment typeOfAttachment;
    private Date createdAt;
}
