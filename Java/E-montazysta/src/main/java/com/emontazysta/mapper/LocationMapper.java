package com.emontazysta.mapper;

import com.emontazysta.model.Location;
import com.emontazysta.model.Orders;
import com.emontazysta.model.Warehouse;
import com.emontazysta.model.dto.LocationDto;
import com.emontazysta.repository.OrderRepository;
import com.emontazysta.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LocationMapper {

    private final OrderRepository orderRepository;
    private final WarehouseRepository warehouseRepository;

    public LocationDto toDto (Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .name(location.getName())
                .xCoordinate(location.getXCoordinate())
                .yCoordinate(location.getYCoordinate())
                .city(location.getCity())
                .street(location.getStreet())
                .propertyNumber(location.getPropertyNumber())
                .apartmentNumber(location.getApartmentNumber())
                .zipCode(location.getZipCode())
                .orders(location.getOrders().stream().map(Orders::getId).collect(Collectors.toList()))
                .warehouseId(location.getWarehouse() == null ? null : location.getWarehouse().getId())
                .build();
    }

    public Location toEntity(LocationDto locationDto) {

        List<Orders> ordersList = new ArrayList<>();
        locationDto.getOrders().forEach(locationId -> ordersList.add(orderRepository.getReferenceById(locationId)));

        return Location.builder()
                .id(locationDto.getId())
                .name(locationDto.getName())
                .xCoordinate(locationDto.getXCoordinate())
                .yCoordinate(locationDto.getYCoordinate())
                .city(locationDto.getCity())
                .street(locationDto.getStreet())
                .propertyNumber(locationDto.getPropertyNumber())
                .apartmentNumber(locationDto.getApartmentNumber())
                .zipCode(locationDto.getZipCode())
                .orders(ordersList)
                .warehouse(locationDto.getWarehouseId() == null ? null : warehouseRepository.getReferenceById(locationDto.getWarehouseId()))
                .build();
    }
}
