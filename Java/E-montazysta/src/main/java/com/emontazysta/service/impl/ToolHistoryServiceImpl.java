package com.emontazysta.service.impl;

import com.emontazysta.model.OrderStage;
import com.emontazysta.model.Tool;
import com.emontazysta.model.dto.ToolHistoryDto;
import com.emontazysta.repository.*;
import com.emontazysta.service.ToolHistoryService;
import com.emontazysta.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ToolHistoryServiceImpl implements ToolHistoryService {

    private final ToolRepository toolRepository;
    private final AuthUtils authUtils;

    @Override
    public List<ToolHistoryDto> getToolHistory(Long id) {
        Tool tool = toolRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!tool.getWarehouse().getCompany().getId().equals(authUtils.getLoggedUserCompanyId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        List<ToolHistoryDto> result = new ArrayList<>();

        tool.getToolReleases().forEach(toolRelease -> {
            OrderStage orderStage = toolRelease.getOrderStage();
            if(orderStage.getEndDate() != null) {
                result.add(
                        ToolHistoryDto.builder()
                                .orderStageName(orderStage.getName())
                                .orderStageId(orderStage.getId())
                                .orderStageStartDate(orderStage.getStartDate())
                                .orderStageEndDate(orderStage.getEndDate())
                                .foremanName(orderStage.getOrders().getAssignedTo().getFirstName() + " " + orderStage.getOrders().getAssignedTo().getLastName())
                                .foremanId(orderStage.getOrders().getAssignedTo().getId())
                                .build()
                );
            }
        });

        Collections.sort(result, Collections.reverseOrder());
        return result;
    }
}
