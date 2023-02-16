package com.emontazysta.mapper;

import com.emontazysta.model.DemandAdHoc;
import com.emontazysta.model.Orders;
import com.emontazysta.model.Specialist;
import com.emontazysta.model.dto.SpecialistDto;
import com.emontazysta.repository.DemandAdHocRepository;
import com.emontazysta.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SpecialistMapper {

    private final OrderRepository orderRepository;
    private final DemandAdHocRepository demandAdHocRepository;

    public SpecialistDto toDto(Specialist specialist) {

        return SpecialistDto.builder()
                .id(specialist.getId())
                .firstName(specialist.getFirstName())
                .lastName(specialist.getLastName())
                .username(specialist.getUsername())
                .email(specialist.getEmail())
                .phone(specialist.getPhone())
                .pesel(specialist.getPesel())
                .orders(specialist.getOrders().stream().map(Orders::getId).collect(Collectors.toList()))
                .demandAdHocs(specialist.getDemandAdHocs().stream().map(DemandAdHoc::getId).collect(Collectors.toList()))
                .build();
    }

    public Specialist toEntity(SpecialistDto specialistDto) {

        List<Orders> ordersList = new ArrayList<>();
        specialistDto.getOrders().forEach(orderId -> ordersList.add(orderRepository.getReferenceById(orderId)));

        List<DemandAdHoc> demandAdHocList = new ArrayList<>();
        specialistDto.getDemandAdHocs().forEach(demandAdHocId -> demandAdHocList.add(demandAdHocRepository.getReferenceById(demandAdHocId)));

        Specialist specialist = new Specialist();
        specialist.setId(specialistDto.getId());
        specialist.setFirstName(specialistDto.getFirstName());
        specialist.setLastName(specialistDto.getLastName());
        specialist.setUsername(specialistDto.getUsername());
        specialist.setEmail(specialistDto.getEmail());
        specialist.setPhone(specialistDto.getPhone());
        specialist.setPesel(specialistDto.getPesel());
        specialist.setOrders(ordersList);
        specialist.setDemandAdHocs(demandAdHocList);

        return specialist;
    }
}
