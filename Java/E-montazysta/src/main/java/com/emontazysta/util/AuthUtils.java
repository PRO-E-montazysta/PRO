package com.emontazysta.util;

import com.emontazysta.model.AppUser;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.service.AppUserService;
import com.emontazysta.service.EmploymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final AppUserService appUserService;
    private final EmploymentService employmentService;

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
        EmploymentDto employment = employmentService.getCurrentEmploymentByEmployeeId(appUser.getId());
        log.debug("Employment found for logged user: {}", employment);
        Long companyId = employment.getCompanyId();
        log.debug("CompanyId found for logged used: {}", companyId);
        return employment.getCompanyId();
    }
}
