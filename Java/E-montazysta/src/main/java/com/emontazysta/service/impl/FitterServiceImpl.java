package com.emontazysta.service.impl;

import com.emontazysta.enums.Role;
import com.emontazysta.mail.MailTemplates;
import com.emontazysta.mapper.EmploymentMapper;
import com.emontazysta.mapper.FitterMapper;
import com.emontazysta.mapper.WorkingOnMapper;
import com.emontazysta.model.EmailData;
import com.emontazysta.model.Fitter;
import com.emontazysta.model.dto.EmployeeDto;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.model.dto.FitterDto;
import com.emontazysta.model.dto.WorkingOnDto;
import com.emontazysta.model.searchcriteria.AppUserSearchCriteria;
import com.emontazysta.repository.FitterRepository;
import com.emontazysta.repository.criteria.AppUserCriteriaRepository;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.service.EmailService;
import com.emontazysta.service.FitterService;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FitterServiceImpl implements FitterService {

    private final FitterRepository repository;
    private final FitterMapper fitterMapper;
    private final EmploymentRepository employmentRepository;
    private final EmploymentMapper employmentMapper;
    private final AuthUtils authUtils;
    private final AppUserCriteriaRepository appUserCriteriaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final WorkingOnMapper workingOnMapper;
    private final EmailService emailService;

    @Override
    public List<FitterDto> getAll(Principal principal) {
        List<EmployeeDto> appUsers = appUserCriteriaRepository.findAllWithFilters(new AppUserSearchCriteria(), principal);
        List<FitterDto> result = new ArrayList<>();

        for(EmployeeDto employeeDto : appUsers) {
            if(employeeDto.getRoles().contains(Role.FITTER)) {
                result.add(fitterMapper.toDto(repository.getReferenceById(employeeDto.getId())));
            }
        }

        return result;
    }


    @Override
    public FitterDto getById(Long id) {
        Fitter fitter = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        FitterDto result = fitterMapper.toDto(fitter);

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
    public FitterDto add(FitterDto fitterDto) {
        String password = PasswordGenerator.generatePassword(10);
        fitterDto.setUsername(fitterDto.getUsername().toLowerCase());
        fitterDto.setPassword(bCryptPasswordEncoder.encode(password));
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
        employmentRepository.save(employmentMapper.toEntity(employmentDto));

        emailService.sendEmail(
                EmailData.builder()
                        .to(fitterDto.getEmail())
                        .message(MailTemplates.employeeCreate(fitterDto.getUsername(),
                                password, fitterDto.getFirstName(), fitterDto.getLastName()))
                        .subject("Witaj w E-Montażysta!")
                        .build()
        );

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

    @Override
    public List<FitterDto> getAvailable(AppUserSearchCriteria appUserSearchCriteria, Principal principal) {
        appUserSearchCriteria.setRoles(List.of("FITTER"));
        List<EmployeeDto> appUsers = appUserCriteriaRepository.findAllWithFilters(appUserSearchCriteria, principal);
        List<FitterDto> result = new ArrayList<>();

        for(EmployeeDto employeeDto : appUsers) {
            result.add(fitterMapper.toDto(repository.getReferenceById(employeeDto.getId())));
        }

        return result;
    }

    @Override
    public List<WorkingOnDto> getWorkingOn(Long id) {
        Fitter fitter = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return fitter.getWorkingOn().stream().map(workingOnMapper::fitterWorks).collect(Collectors.toList());
    }
}
