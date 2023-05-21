package com.emontazysta.service.impl;

import com.emontazysta.enums.Role;
import com.emontazysta.enums.TypeOfAttachment;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.Attachment;
import com.emontazysta.model.dto.AboutMeDto;
import com.emontazysta.service.AboutMeService;
import com.emontazysta.service.CompanyService;
import com.emontazysta.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AboutMeServiceImpl implements AboutMeService {

    private final AuthUtils authUtils;
    private final CompanyService companyService;

    @Override
    public AboutMeDto getInfoAboutMe() {
        AppUser loggedUser = authUtils.getLoggedUser();
        Long companyId = authUtils.getLoggedUserCompanyId();

        String companyName;
        if(companyId != null) {
            companyName = companyService.getById(companyId).getCompanyName();
        } else {
            companyName = "E-Montażysta Team";
        }

        String profilePhotoUrl= null;
        if(!loggedUser.getRoles().contains(Role.CLOUD_ADMIN)) {
            Optional<Attachment> profilePhotoAttachment = loggedUser.getAttachments().stream()
                    .filter(attachment -> attachment.getTypeOfAttachment().equals(TypeOfAttachment.PROFILE_PICTURE))
                    .findFirst();

            if (profilePhotoAttachment.isPresent()) {
                profilePhotoUrl = profilePhotoAttachment.get().getUrl();
            }
        }


        return AboutMeDto.builder()
                .firstName(loggedUser.getFirstName())
                .lastName(loggedUser.getLastName())
                .roles(loggedUser.getRoles())
                .companyName(companyName)
                .profilePhotoUrl(profilePhotoUrl)
                .build();
    }
}
