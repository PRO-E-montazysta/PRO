package com.emontazysta.service.impl;

import com.emontazysta.enums.Role;
import com.emontazysta.mail.MailTemplates;
import com.emontazysta.mapper.EmploymentMapper;
import com.emontazysta.mapper.SpecialistMapper;
import com.emontazysta.model.EmailData;
import com.emontazysta.model.Specialist;
import com.emontazysta.model.dto.EmployeeDto;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.model.dto.SpecialistDto;
import com.emontazysta.model.searchcriteria.AppUserSearchCriteria;
import com.emontazysta.repository.SpecialistRepository;
import com.emontazysta.repository.criteria.AppUserCriteriaRepository;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.service.EmailService;
import com.emontazysta.service.SpecialistService;
import com.emontazysta.util.AuthUtils;
import com.emontazysta.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository repository;
    private final SpecialistMapper specialistMapper;
    private final EmploymentRepository employmentRepository;
    private final EmploymentMapper employmentMapper;
    private final AuthUtils authUtils;
    private final AppUserCriteriaRepository appUserCriteriaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;

    @Override
    public List<SpecialistDto> getAll(Principal principal) {
        List<EmployeeDto> appUsers = appUserCriteriaRepository.findAllWithFilters(new AppUserSearchCriteria(), principal);
        List<SpecialistDto> result = new ArrayList<>();

        for(EmployeeDto employeeDto : appUsers) {
            if(employeeDto.getRoles().contains(Role.SPECIALIST)) {
                result.add(specialistMapper.toDto(repository.getReferenceById(employeeDto.getId())));
            }
        }

        return result;
    }

    @Override
    public SpecialistDto getById(Long id) {
        Specialist specialist = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        SpecialistDto result = specialistMapper.toDto(specialist);

        if(!authUtils.getLoggedUser().getRoles().contains(Role.ADMIN)) {
            result.setUsername(null);
        }
        if(!(authUtils.getLoggedUser().getRoles().contains(Role.ADMIN) ||
                authUtils.getLoggedUser().getRoles().contains(Role.MANAGER))) {
            result.setPesel(null);
        }

        return result;
    }

    @Override
    public SpecialistDto add(SpecialistDto specialistDto) {
        String password = PasswordGenerator.generatePassword(10);
        specialistDto.setUsername(specialistDto.getUsername().toLowerCase());
        specialistDto.setPassword(bCryptPasswordEncoder.encode(password));
        specialistDto.setRoles(Set.of(Role.SPECIALIST));
        specialistDto.setUnavailabilities(new ArrayList<>());
        specialistDto.setNotifications(new ArrayList<>());
        specialistDto.setEmployeeComments(new ArrayList<>());
        specialistDto.setElementEvents(new ArrayList<>());
        specialistDto.setEmployments(new ArrayList<>());
        specialistDto.setAttachments(new ArrayList<>());
        specialistDto.setToolEvents(new ArrayList<>());
        specialistDto.setOrders(new ArrayList<>());
        specialistDto.setDemandAdHocs(new ArrayList<>());

        Specialist specialist = repository.save(specialistMapper.toEntity(specialistDto));

        EmploymentDto employmentDto = EmploymentDto.builder()
                .dateOfEmployment(LocalDateTime.now())
                .dateOfDismiss(null)
                .companyId(authUtils.getLoggedUserCompanyId())
                .employeeId(specialist.getId())
                .build();
        employmentRepository.save(employmentMapper.toEntity(employmentDto));

        emailService.sendEmail(
                EmailData.builder()
                        .to(specialistDto.getEmail())
                        .message(MailTemplates.employeeCreate(specialistDto.getUsername(),
                                password, specialistDto.getFirstName(), specialistDto.getLastName()))
                        .subject("Witaj w E-Montażysta!")
                        .build()
        );

        return specialistMapper.toDto(specialist);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public SpecialistDto update(Long id, SpecialistDto specialistDto) {
        Specialist updatedSpecialist = specialistMapper.toEntity(specialistDto);
        Specialist specialist = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        specialist.setFirstName(updatedSpecialist.getFirstName());
        specialist.setLastName(updatedSpecialist.getLastName());
        specialist.setEmail(updatedSpecialist.getEmail());
        specialist.setPhone(updatedSpecialist.getPhone());
        specialist.setPesel(updatedSpecialist.getPesel());
        specialist.setUnavailabilities(updatedSpecialist.getUnavailabilities());
        specialist.setNotifications(updatedSpecialist.getNotifications());
        specialist.setEmployeeComments(updatedSpecialist.getEmployeeComments());
        specialist.setElementEvents(updatedSpecialist.getElementEvents());
        specialist.setEmployments(updatedSpecialist.getEmployments());
        specialist.setAttachments(updatedSpecialist.getAttachments());
        specialist.setToolEvents(updatedSpecialist.getToolEvents());
        specialist.setOrders(updatedSpecialist.getOrders());
        specialist.setDemandAdHocs(updatedSpecialist.getDemandAdHocs());
        specialist.setOrders(updatedSpecialist.getOrders());
        specialist.setDemandAdHocs(updatedSpecialist.getDemandAdHocs());
        return specialistMapper.toDto(repository.save(specialist));
    }
}
