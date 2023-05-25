package com.emontazysta.controller;

import com.emontazysta.model.dto.NotificationDto;
import com.emontazysta.model.searchcriteria.NotificationSearchCriteria;
import com.emontazysta.service.NotificationService;
import com.emontazysta.util.AuthUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationsController {

    private final NotificationService notificationService;
    private final AuthUtils authUtils;

    @GetMapping("/my-all")
    @Operation(description = "Allows to get all Notifications for logged user.", security = @SecurityRequirement(name = "bearer-key"))
    public List<NotificationDto> getMyAllNotifications(NotificationSearchCriteria notificationSearchCriteria){
        return notificationService.findAllWithFilters(notificationSearchCriteria);
    }

    @GetMapping
    @Operation(description = "Allows to get Notifications for logged user.", security = @SecurityRequirement(name = "bearer-key"))
    public List<NotificationDto> getNotifications(){
        return notificationService.getAllNotReaded(authUtils.getLoggedUser().getId());
    }

    @PutMapping("/{notificationId}")
    @Operation(description = "Allows to update Notifications for logged user.", security = @SecurityRequirement(name = "bearer-key"))
    public NotificationDto update(@PathVariable Long notificationId) {
        return notificationService.update(notificationId);
    }


}
