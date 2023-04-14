package com.emontazysta.service;

import com.emontazysta.model.AppUser;
import com.emontazysta.model.Unavailability;

public interface StatusService {

    Unavailability checkUnavailability(AppUser appUser);
}
