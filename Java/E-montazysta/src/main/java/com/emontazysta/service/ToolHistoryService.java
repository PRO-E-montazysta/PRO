package com.emontazysta.service;

import com.emontazysta.model.dto.ToolHistoryDto;
import java.util.List;

public interface ToolHistoryService {

    List<ToolHistoryDto> getToolHistory(Long id);
}
