package com.emontazysta.util;

import com.emontazysta.enums.CompanyStatus;
import com.emontazysta.enums.Role;
import com.emontazysta.mapper.CompanyMapper;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.Company;
import com.emontazysta.model.Employment;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.service.AppUserService;
import com.emontazysta.service.CompanyService;
import com.emontazysta.service.EmploymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final AppUserService appUserService;
    private final EmploymentService employmentService;
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public AppUser getLoggedUser() {
        Authentication authentication = getAuthentication();
        String username = authentication.getName();
        AppUser appUser = appUserService.findByUsername(username);
        log.debug("AppUser found for logged username {}: {}", username, appUser);
        return appUser;
    }

    public Long getLoggedUserCompanyId() {
        AppUser appUser = getLoggedUser();
        Optional<EmploymentDto> employment = employmentService.getCurrentEmploymentByEmployeeId(appUser.getId());
        if(employment.isPresent()) {
            log.debug("Employment found for logged user: {}", employment.get());
            Long companyId = employment.get().getCompanyId();
            log.debug("CompanyId found for logged used: {}", companyId);
            return employment.get().getCompanyId();
        }
        return null;
    }

    public boolean userCanLogin(String username) {
        //Get user
        AppUser appUser = appUserService.findByUsername(username);

        //Check if user exists
        if(appUser == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //Check if user is Cloud_Admin
        if(appUser.getRoles().contains(Role.CLOUD_ADMIN)){
            return true;
        }

        //Get user last employment
        Optional<EmploymentDto> employment = employmentService.getCurrentEmploymentByEmployeeId(appUser.getId());

        //Check if user is still working in company
        if(employment.isEmpty()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        
        //Check if company is active
        Company empCompany = companyMapper.toEntity(companyService.getById(employment.get().getCompanyId()));
        if(empCompany.getStatus() != CompanyStatus.ACTIVE){
            throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED);
        }

        return true;
    }
}
