package com.emontazysta.service.impl;

import com.emontazysta.mapper.LocationMapper;
import com.emontazysta.model.Location;
import com.emontazysta.model.Orders;
import com.emontazysta.model.Warehouse;
import com.emontazysta.model.dto.LocationDto;
import com.emontazysta.repository.LocationRepository;
import com.emontazysta.repository.OrderRepository;
import com.emontazysta.repository.WarehouseRepository;
import com.emontazysta.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository repository;
    private final LocationMapper locationMapper;
    private final OrderRepository orderRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public List<LocationDto> getAll() {
        return repository.findAll().stream()
                .map(locationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LocationDto getById(Long id) {
        Location location = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return locationMapper.toDto(location);
    }

    @Override
    public LocationDto add(LocationDto locationDto) {
        Location location = repository.save(locationMapper.toEntity(locationDto));

        if(locationDto.getOrderId()!=null){
            Orders order = location.getOrder();
            order.setLocation(location);
            orderRepository.save(order);
        }

        if(locationDto.getWarehouseId()!=null){
            Warehouse warehouse = location.getWarehouse();
            warehouse.setLocation(location);
            warehouseRepository.save(warehouse);
        }

        return locationMapper.toDto(location);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public LocationDto update(Long id, LocationDto locationDto) {
        Location updatedLocation = locationMapper.toEntity(locationDto);
        Location location = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        location.setXCoordinate(updatedLocation.getXCoordinate());
        location.setYCoordinate(updatedLocation.getYCoordinate());
        location.setCity(updatedLocation.getCity());
        location.setStreet(updatedLocation.getStreet());
        location.setPropertyNumber(updatedLocation.getPropertyNumber());
        location.setApartmentNumber(updatedLocation.getApartmentNumber());
        location.setZipCode(updatedLocation.getZipCode());

        return locationMapper.toDto(repository.save(location));
    }
}
