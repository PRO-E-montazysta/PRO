package com.emontazysta.model.page;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Data
public class ToolPage {

    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "name";
}
