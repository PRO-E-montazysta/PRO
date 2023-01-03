package com.emontazysta.service;

import com.emontazysta.model.Location;
import java.util.List;

public interface LocationService {

    List<Location> getAll();
    Location getById(Long id);
    void add(Location location);
    void delete(Long id);
    void update(Long id, Location location);
}
