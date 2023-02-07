package com.emontazysta.configuration;

import com.emontazysta.enums.CompanyStatus;
import com.emontazysta.enums.TypeOfPriority;
import com.emontazysta.enums.TypeOfStatus;
import com.emontazysta.model.*;
import com.emontazysta.service.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataSeeding {

    private final CompanyService companyService;
    private final ClientService clientService;
    private final LocationService locationService;
    private final OrdersService ordersService;
    private final WarehouseService warehouseService;
    private final ToolService toolService;

    @PostConstruct
    void setUp() {
        Company company1 = new Company(null, "Test Comapny 1", null, CompanyStatus.ACTIVE,
                "They pay", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Company company2 = new Company(null, "Test Comapny 2", null, CompanyStatus.ACTIVE,
                "They pay", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Company company3 = new Company(null, "Test Comapny 3", null, CompanyStatus.SUSPENDED,
                "They don't pay", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Company company4 = new Company(null, "Test Comapny 4", null, CompanyStatus.DISABLED,
                "Closed company", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        List<Company> companyList = List.of(company1, company2, company3, company4);

        Client client1 = new Client(null, "Test Client 1 - from Company 1", "em@i.l",
                company1, new ArrayList<>());
        Client client2 = new Client(null, "Test Client 2 - from Company 1", "em@i.l",
                company1, new ArrayList<>());
        Client client3 = new Client(null, "Test Client 3 - from Company 3", "em@i.l",
                company3, new ArrayList<>());
        Client client4 = new Client(null, "Test Client 4 - from Company 4", "em@i.l",
                company4, new ArrayList<>());
        List<Client> clientList = List.of(client1, client2, client3, client4);

        Location location1 = new Location(null, "Test Location 1", 1.1, 1.1, "City",
                "Street", "Property Number", "Apartment Number", "Zip Code",
                new ArrayList<>(), new ArrayList<>());
        Location location2 = new Location(null, "Test Location 2", 1.1, 1.1, "City",
                "Street", "Property Number", "Apartment Number", "Zip Code",
                new ArrayList<>(), new ArrayList<>());
        Location location3 = new Location(null, "Test Location 3", 1.1, 1.1, "City",
                "Street", "Property Number", "Apartment Number", "Zip Code",
                new ArrayList<>(), new ArrayList<>());
        Location location4 = new Location(null, "Test Location 4", 1.1, 1.1, "City",
                "Street", "Property Number", "Apartment Number", "Zip Code",
                new ArrayList<>(), new ArrayList<>());
        List<Location> locationList = List.of(location1, location2, location3, location4);

        Orders order1 = new Orders(null, "Test Order 1 - from Client 1", TypeOfStatus.PLANNED, new Date(),
                new Date(), null, null, TypeOfPriority.NORMAL, company1, null,
                null, null, null, location1, client1, new ArrayList<>(), new ArrayList<>());
        Orders order2 = new Orders(null, "Test Order 2 - from Client 1", TypeOfStatus.PLANNED, new Date(),
                new Date(), null, null, TypeOfPriority.NORMAL, company1, null,
                null, null, null, location2, client1, new ArrayList<>(), new ArrayList<>());
        Orders order3 = new Orders(null, "Test Order 3 - from Client 2", TypeOfStatus.PLANNED, new Date(),
                new Date(), null, null, TypeOfPriority.NORMAL, company1, null,
                null, null, null, location3, client2, new ArrayList<>(), new ArrayList<>());
        Orders order4 = new Orders(null, "Test Order 4 - from Client 4", TypeOfStatus.PLANNED, new Date(),
                new Date(), null, null, TypeOfPriority.NORMAL, company4, null,
                null, null, null, location4, client4, new ArrayList<>(), new ArrayList<>());
        List<Orders> ordersList = List.of(order1, order2, order3, order4);

        Warehouse warehouse1 = new Warehouse(null, "Test Warehouse 1", "Description",
                "Opening Hours", company1, location1, new ArrayList<>(), new ArrayList<>());
        Warehouse warehouse2 = new Warehouse(null, "Test Warehouse 2", "Description",
                "Opening Hours", company1, location2, new ArrayList<>(), new ArrayList<>());
        Warehouse warehouse3 = new Warehouse(null, "Test Warehouse 3", "Description",
                "Opening Hours", company1, location3, new ArrayList<>(), new ArrayList<>());
        Warehouse warehouse4 = new Warehouse(null, "Test Warehouse 4", "Description",
                "Opening Hours", company2, location4, new ArrayList<>(), new ArrayList<>());
        List<Warehouse> warehouseList = List.of(warehouse1, warehouse2, warehouse3, warehouse4);

        Tool tool1 = new Tool(null, "Test Tool 1", null, null, new ArrayList<>(),
                warehouse1, new ArrayList<>(), null);
        Tool tool2 = new Tool(null, "Test Tool 2", null, null, new ArrayList<>(),
                warehouse1, new ArrayList<>(), null);
        Tool tool3 = new Tool(null, "Test Tool 3", null, null, new ArrayList<>(),
                warehouse2, new ArrayList<>(), null);
        Tool tool4 = new Tool(null, "Test Tool 4", null, null, new ArrayList<>(),
                warehouse2, new ArrayList<>(), null);
        List<Tool> toolList = List.of(tool1, tool2, tool3,tool4);

        companyList.forEach(company -> {
            companyService.add(company);
        });
        clientList.forEach(client -> {
            clientService.add(client);
        });
        locationList.forEach(location -> {
            locationService.add(location);
        });
        ordersList.forEach(order -> {
            ordersService.add(order);
        });
        warehouseList.forEach(warehouse -> {
            warehouseService.add(warehouse);
        });
        toolList.forEach(tool -> {
            toolService.add(tool);
        });
    }
}
