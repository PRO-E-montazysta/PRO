package com.emontazysta.service.impl;

import com.emontazysta.mapper.UnavailabilityToCallendarMapper;
import com.emontazysta.mapper.UnavailabilityMapper;
import com.emontazysta.model.Manager;
import com.emontazysta.model.OrderStage;
import com.emontazysta.model.Unavailability;
import com.emontazysta.model.dto.UnavailabilityToCalendarDto;
import com.emontazysta.model.dto.UnavailabilityDto;
import com.emontazysta.model.dto.UnavailabilityWithLocalDateDto;
import com.emontazysta.model.dto.filterDto.UnavailabilityFilterDto;
import com.emontazysta.model.searchcriteria.UnavailabilitySearchCriteria;
import com.emontazysta.repository.UnavailabilityRepository;
import com.emontazysta.repository.criteria.UnavailabilityCriteriaRepository;
import com.emontazysta.service.UnavailabilityService;
import com.emontazysta.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UnavailabilityServiceImpl implements UnavailabilityService {

    private final UnavailabilityRepository repository;
    private final UnavailabilityMapper unavailabilityMapper;
    private final AuthUtils authUtils;
    private final UnavailabilityCriteriaRepository unavailabilityCriteriaRepository;

    private final UnavailabilityToCallendarMapper unavailabilityToCallendarMapper;
    @Override
    public List<UnavailabilityDto> getAll() {
        return repository.findAll().stream()
                .map(unavailabilityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UnavailabilityDto getById(Long id) {
        Unavailability unavailability = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return unavailabilityMapper.toDto(unavailability);
    }

    @Override
    public UnavailabilityDto add(UnavailabilityDto unavailabilityDto) {
        Unavailability unavailability = unavailabilityMapper.toEntity(unavailabilityDto);
        unavailability.setAssignedBy((Manager) authUtils.getLoggedUser());
        return unavailabilityMapper.toDto(repository.save(unavailability));
    }

    @Override
    public UnavailabilityDto addWithLocalDate(UnavailabilityWithLocalDateDto unavailabilityWithLocalDateDto) {
        UnavailabilityDto unavailabilityDto = UnavailabilityDto.builder()
                .id(null)
                .typeOfUnavailability(unavailabilityWithLocalDateDto.getTypeOfUnavailability())
                .description(unavailabilityWithLocalDateDto.getDescription())
                .unavailableFrom(unavailabilityWithLocalDateDto.getUnavailableFrom().atTime(LocalTime.MIN))
                .unavailableTo(unavailabilityWithLocalDateDto.getUnavailableTo().atTime(23, 59))
                .assignedToId(unavailabilityWithLocalDateDto.getAssignedToId())
                .assignedById(authUtils.getLoggedUser().getId())
                .build();
        return unavailabilityMapper.toDto(repository.save(unavailabilityMapper.toEntity(unavailabilityDto)));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public UnavailabilityDto update(Long id, UnavailabilityDto unavailabilityDto) {
        Unavailability updatedUnavailability = unavailabilityMapper.toEntity(unavailabilityDto);
        Unavailability unavailability = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        unavailability.setTypeOfUnavailability(updatedUnavailability.getTypeOfUnavailability());
        unavailability.setDescription(updatedUnavailability.getDescription());
        unavailability.setUnavailableFrom(updatedUnavailability.getUnavailableFrom());
        unavailability.setUnavailableTo(updatedUnavailability.getUnavailableTo());
        unavailability.setAssignedTo(updatedUnavailability.getAssignedTo());

        return unavailabilityMapper.toDto(repository.save(updatedUnavailability));
    }

    @Override
    public UnavailabilityDto updateWithLocalDate(Long id, UnavailabilityWithLocalDateDto unavailabilityWithLocalDateDto) {
        UnavailabilityDto unavailabilitydto = getById(id);

        unavailabilitydto.setTypeOfUnavailability(unavailabilityWithLocalDateDto.getTypeOfUnavailability());
        unavailabilitydto.setDescription(unavailabilityWithLocalDateDto.getDescription());
        unavailabilitydto.setUnavailableFrom(unavailabilityWithLocalDateDto.getUnavailableFrom().atTime(LocalTime.MIN));
        unavailabilitydto.setUnavailableTo(unavailabilityWithLocalDateDto.getUnavailableTo().atTime(23, 59));
        unavailabilitydto.setAssignedToId(unavailabilityWithLocalDateDto.getAssignedToId());

        return unavailabilityMapper.toDto(repository.save(unavailabilityMapper.toEntity(unavailabilitydto)));
    }

    @Override
    public List<UnavailabilityFilterDto> findAllWithFilters(UnavailabilitySearchCriteria unavailabilitySearchCriteria) {
        return unavailabilityCriteriaRepository.findAllWithFilters(unavailabilitySearchCriteria);
    }

    @Override
    public List<UnavailabilityToCalendarDto> getAllForCompanyLoggedUserInMonth(int month, int year) {
       List<UnavailabilityToCalendarDto> unavailabilityToCalendarDtoList = new ArrayList<>();
       List<Unavailability> unavailabilitiesfilteredByCompanyId = new ArrayList<>();


       Long loggedUserCompanyId = authUtils.getLoggedUserCompanyId();
       LocalDateTime startDate = createStartDate(month,year);
       LocalDateTime endDate = createEndDate(month,year);

       // wszystkie nieobecności dla danego miesiąca
       List<Unavailability> unavailabilities =
               repository.findAllByUnavailableFromGreaterThanAndUnavailableFromLessThan(startDate, endDate);

       // tylko nieobecności dla firmy zalogowanego użytkownika
        for (Unavailability unavailability : unavailabilities) {
            if(loggedUserCompanyId.equals(authUtils.getUserCompanyId(unavailability.getAssignedBy()))) {
                unavailabilitiesfilteredByCompanyId.add(unavailability);
            }
        }
        //mapowanie nieobecności
        for (Unavailability unavailability: unavailabilitiesfilteredByCompanyId) {
                unavailabilityToCalendarDtoList.add(unavailabilityToCallendarMapper.toDto(unavailability));
        }

       return unavailabilityToCalendarDtoList;
    }

    private LocalDateTime createEndDate(int month, int year) {
        List<Integer> thirtyDayMonth = List.of(4,6,9,11);
        int lastDay;

        if (month==2){
            lastDay=28;
        } else if (thirtyDayMonth.contains(month)) {
            lastDay=30;
        } else {
            lastDay=31;
        }

       return LocalDateTime.of(year,month,lastDay,23,59);
    }

    private LocalDateTime createStartDate(int month, int year) {
        return LocalDateTime.of(year,month,1,0,0);
    }
}
