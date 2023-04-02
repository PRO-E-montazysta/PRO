package com.emontazysta.service.impl;

import com.emontazysta.enums.Role;
import com.emontazysta.mapper.FitterMapper;
import com.emontazysta.model.Fitter;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.model.dto.FitterDto;
import com.emontazysta.repository.FitterRepository;
import com.emontazysta.service.EmploymentService;
import com.emontazysta.service.FitterService;
import com.emontazysta.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FitterServiceImpl implements FitterService {

    private final FitterRepository repository;
    private final FitterMapper fitterMapper;
    private final EmploymentService employmentService;
    private final AuthUtils authUtils;

    @Override
    public List<FitterDto> getAll() {
        return repository.findAll().stream()
                .map(fitterMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public FitterDto getById(Long id) {
        Fitter fitter = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return fitterMapper.toDto(fitter);
    }

    @Override
    public FitterDto add(FitterDto fitterDto) {
        fitterDto.setRoles(Set.of(Role.FITTER));
        fitterDto.setUnavailabilities(new ArrayList<>());
        fitterDto.setNotifications(new ArrayList<>());
        fitterDto.setEmployeeComments(new ArrayList<>());
        fitterDto.setElementEvents(new ArrayList<>());
        fitterDto.setEmployments(new ArrayList<>());
        fitterDto.setAttachments(new ArrayList<>());
        fitterDto.setToolEvents(new ArrayList<>());
        fitterDto.setWorkingOn(new ArrayList<>());

        Fitter fitter = repository.save(fitterMapper.toEntity(fitterDto));

        EmploymentDto employmentDto = EmploymentDto.builder()
                .dateOfEmployment(LocalDateTime.now())
                .dateOfDismiss(null)
                .companyId(authUtils.getLoggedUserCompanyId())
                .employeeId(fitter.getId())
                .build();
        employmentService.add(employmentDto);

        return fitterMapper.toDto(fitter);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public FitterDto update(Long id, FitterDto fitterDto) {
        Fitter updatedFitter = fitterMapper.toEntity(fitterDto);
        Fitter fitter = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        fitter.setFirstName(updatedFitter.getFirstName());
        fitter.setLastName(updatedFitter.getLastName());
        fitter.setUsername(updatedFitter.getUsername());
        fitter.setEmail(updatedFitter.getEmail());
        fitter.setPhone(updatedFitter.getPhone());
        fitter.setPesel(updatedFitter.getPesel());
        fitter.setUnavailabilities(updatedFitter.getUnavailabilities());
        fitter.setNotifications(updatedFitter.getNotifications());
        fitter.setEmployeeComments(updatedFitter.getEmployeeComments());
        fitter.setElementEvents(updatedFitter.getElementEvents());
        fitter.setEmployments(updatedFitter.getEmployments());
        fitter.setAttachments(updatedFitter.getAttachments());
        fitter.setToolEvents(updatedFitter.getToolEvents());
        fitter.setWorkingOn(updatedFitter.getWorkingOn());
        return fitterMapper.toDto(repository.save(fitter));
    }
}
