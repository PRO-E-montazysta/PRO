package com.emontazysta.configuration;

import com.emontazysta.enums.*;
import com.emontazysta.mapper.*;
import com.emontazysta.model.*;
import com.emontazysta.repository.*;
import com.emontazysta.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@DependsOn("securityConfig")
@EnableConfigurationProperties(SecurityProperties.class)
public class DataSeeding {

    private final SecurityProperties securityProperties;
    private final CompanyService companyService;
    private final ClientService clientService;
    private final LocationService locationService;
    private final OrderRepository orderRepository;
    private final WarehouseRepository warehouseRepository;
    private final ToolTypeService toolTypeService;
    private final OrderStageRepository orderStageRepository;
    private final ToolReleaseService toolReleaseService;
    private final ElementInWarehouseRepository elementInWarehouseRepository;
    private final ElementReturnReleaseService elementReturnReleaseService;
    private final AppUserService appUserService;
    private final EmploymentRepository employmentRepository;
    private final ToolRepository toolRepository;
    private final ElementRepository elementRepository;
    private final CompanyMapper companyMapper;
    private final ClientMapper clientMapper;
    private final LocationMapper locationMapper;
    private final ToolTypeMapper toolTypeMapper;
    private final ToolReleaseMapper toolReleaseMapper;
    private final ElementReturnReleaseMapper elementReturnReleaseMapper;
    private final ToolsPlannedNumberRepository toolsPlannedNumberRepository;
    private final ElementsPlannedNumberRepository elementsPlannedNumberRepository;

    private Company addCompanyFromModel(Company company) {
        return companyMapper.toEntity(companyService.add(companyMapper.toDto(company)));
    }

    private Client addClientFromModel(Client client) {
        return clientMapper.toEntity(clientService.add(clientMapper.toDto(client)));
    }

    private Location addLocationFromModel(Location location) {
        return locationMapper.toEntity(locationService.add(locationMapper.toDto(location)));
    }

    private Orders addOrdersFromModel(Orders orders) {
        return orderRepository.save(orders);
    }

    private OrderStage addOrderStageFromModel(OrderStage orderStage) {
        return orderStageRepository.save(orderStage);
    }

    private Warehouse addWarehouseFromModel(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    private ToolType addToolTypeFromModel(ToolType toolType) {
        return toolTypeMapper.toEntity(toolTypeService.add(toolTypeMapper.toDto(toolType)));
    }

    private ToolRelease addToolReleaseFromModel(ToolRelease toolRelease) {
        return toolReleaseMapper.toEntity(toolReleaseService.add(toolReleaseMapper.toDto(toolRelease)));
    }

    private ElementInWarehouse addElementInWarehouseFromModel(ElementInWarehouse elementInWarehouse) {
        return elementInWarehouseRepository.save(elementInWarehouse);
    }

    private ElementReturnRelease addElementReturnReleaseFromModel(ElementReturnRelease elementReturnRelease) {
        return elementReturnReleaseMapper.toEntity(elementReturnReleaseService.add(elementReturnReleaseMapper.toDto(elementReturnRelease)));
    }

    @PostConstruct
    void setUp() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                securityProperties.getCloudManagerUsername(), securityProperties.getCloudManagerPassword());
        context.setAuthentication(authentication);

        Fitter fitter1 = new Fitter(null, "Fitter1", "Montażysta1", "fitter1@ema.il",
                "password", "fitter1", null, Set.of(Role.FITTER), "+48123123123",
                "92122637693", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Fitter fitter2 = new Fitter(null, "Fitter2", "Montażysta2", "fitter2@ema.il",
                "password", "fitter2", null, Set.of(Role.FITTER), "+48231231231",
                "81081748975", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        Foreman foreman1 = new Foreman(null, "Foreman1", "Brygadzista1", "foreman1@ema.il",
                "password", "foreman1", null, Set.of(Role.FOREMAN), "+48123123123",
                "81050878511", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());
        Foreman foreman2 = new Foreman(null, "Foreman2", "Brygadzista2", "foreman2@ema.il",
                "password", "foreman2", null, Set.of(Role.FOREMAN), "+48231231231",
                "96082483486", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());

        Warehouseman warehouseman1 = new Warehouseman(null, "Warehouseman1", "Magazynier1",
                "warehouseman1@ema.il", "password", "warehouseman1", null,
                Set.of(Role.WAREHOUSE_MAN), "+48312312312", "95100198759", new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Warehouseman warehouseman2 = new Warehouseman(null, "Warehouseman2", "Magazynier2",
                "warehouseman2@ema.il", "password", "warehouseman2", null,
                Set.of(Role.WAREHOUSE_MAN), "+48123123123", "61121073726", new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        WarehouseManager warehouseManager1 = new WarehouseManager(null, "WarehouseManager1", "KierMag1",
                "warehouseManager1@ema.il", "password", "warehouseManager1", null,
                Set.of(Role.WAREHOUSE_MANAGER), "+48231231231", "77021937941", new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        WarehouseManager warehouseManager2 = new WarehouseManager(null, "WarehouseManager2", "KierMag2",
                "warehouseManager2@ema.il", "password", "warehouseManager2", null,
                Set.of(Role.WAREHOUSE_MANAGER), "+48312312312", "95082943437", new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        Specialist specialist1 = new Specialist(null, "Specialist1", "Specialista1",
                "specialist1@ema.il", "password", "specialist1", null,
                Set.of(Role.SPECIALIST), "+48123123123", "98051125221", new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Specialist specialist2 = new Specialist(null, "Specialist2", "Specialista12",
                "specialist2@ema.il", "password", "specialist2", null,
                Set.of(Role.SPECIALIST), "+48231231231", "98050326791", new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        SalesRepresentative salesRepresentative1 = new SalesRepresentative(null, "SalesRepresentative1",
                "Handlowiec1", "salesRepresentative1@ema.il", "password",
                "sales1", null, Set.of(Role.SALES_REPRESENTATIVE),
                "+48312312312", "81021337793", new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        SalesRepresentative salesRepresentative2 = new SalesRepresentative(null, "SalesRepresentative2",
                "Handlowiec2", "salesRepresentative2@ema.il", "password",
                "sales2", null, Set.of(Role.SALES_REPRESENTATIVE),
                "+48123123123", "60102488227", new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        Manager manager1 = new Manager(null, "Manager1", "Manager1", "manager1@ema.il",
                "password", "manager1", null, Set.of(Role.MANAGER),
                "+48231231231", "67091057683", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Manager manager2 = new Manager(null, "Manager2", "Manager2", "manager2@ema.il",
                "password", "manager2", null, Set.of(Role.MANAGER),
                "+48312312312", "85021697141", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        CompanyAdmin companyAdmin1 = new CompanyAdmin(null, "CompanyAdmin", "Administrator", "admin@ema.il",
                "password", "companyAdmin1", null, Set.of(Role.ADMIN),
                "+48123123123", "98121238453", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        List<AppUser> appUserList = List.of(fitter1, fitter2, foreman1, foreman2, warehouseman1, warehouseman2,
                warehouseManager1, warehouseManager2, specialist1, specialist2, salesRepresentative1, salesRepresentative2,
                manager1, manager2, companyAdmin1);
        appUserList.forEach(appUserService::add);

        Company company1 = addCompanyFromModel(new Company(null, "Test Comapny 1", null,
                CompanyStatus.ACTIVE, "They pay", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>()));
        Company company2 = addCompanyFromModel(new Company(null, "Test Comapny 3", null,
                CompanyStatus.SUSPENDED, "They don't pay", new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

        employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, fitter1));
        employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, fitter2));
        employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, foreman1));
        employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, foreman2));
        employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, warehouseman1));
        employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, warehouseman2));
        employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, warehouseManager1));
        employmentRepository.save(new Employment(null, LocalDateTime.now(), LocalDateTime.now(), company1, warehouseManager2));
        employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, specialist1));
        employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, specialist2));
        employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, salesRepresentative1));
        employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, salesRepresentative2));
        employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, manager1));
        employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company2, manager2));
        employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, companyAdmin1));

        context.setAuthentication(null);
        Authentication authenticationMng = new UsernamePasswordAuthenticationToken(manager1.getUsername(), manager1.getPassword());
        context.setAuthentication(authenticationMng);

        Client client1 = addClientFromModel(new Client(null, "Test Client 1",
                "em@i.l", company1, new ArrayList<>()));

        Location location1 = addLocationFromModel(new Location(null, 52.2327899,
                21.0118499, "Warszawa", "Złota", "7",
                "", "00-019", null, null));
        Location location2 = addLocationFromModel(new Location(null, 52.22375776342417,
                20.994122028350834, "Warszawa", "Koszykowa", "86",
                "", "02-008", null, null));

        Orders order1 = addOrdersFromModel(new Orders(null, "Test Order 1 - from Client 1",
                OrderStatus.ACCEPTED, LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now(), null, TypeOfPriority.IMPORTANT,
                company1, manager1, foreman1, specialist1, salesRepresentative1, location1, client1, new ArrayList<>(),
                new ArrayList<>()));
        Orders order5 = addOrdersFromModel(new Orders(null, "ZLECENIE DO WYDAWANIA/ZWROTOW",
                OrderStatus.IN_PROGRESS, LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now(), null, TypeOfPriority.IMPORTANT,
                company1, manager1, foreman1, specialist1, salesRepresentative1, location1, client1, new ArrayList<>(),
                new ArrayList<>()));

        OrderStage orderStage1 = addOrderStageFromModel(new OrderStage(null, "Test OrderStage 1",
                OrderStageStatus.ADDING_FITTERS, new BigDecimal(1), LocalDateTime.now(), LocalDateTime.now().plusHours(5), null, null,
                1, 1, 1, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), order1, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        OrderStage orderStage2 = addOrderStageFromModel(new OrderStage(null, "Test OrderStage 2",
                OrderStageStatus.ADDING_FITTERS, new BigDecimal(2), LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2), null, null,
                1, 1, 1, List.of(fitter2,fitter1),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), order1, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        OrderStage orderStage3 = addOrderStageFromModel(new OrderStage(null, "Test OrderStage 3",
                OrderStageStatus.ADDING_FITTERS, new BigDecimal(3), LocalDateTime.now().plusHours(2).plusMinutes(30), LocalDateTime.now().plusHours(3), null, null,
                1, 1, 1, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), order1, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        OrderStage orderStage5 = addOrderStageFromModel(new OrderStage(null, "Etap zwracania",
                OrderStageStatus.RETURN, new BigDecimal(1), LocalDateTime.now(), LocalDateTime.now().plusHours(1), LocalDateTime.now(), LocalDateTime.now().plusHours(1),
                1, 1, 1, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), order5, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        OrderStage orderStage6 = addOrderStageFromModel(new OrderStage(null, "Etap wydawania",
                OrderStageStatus.PICK_UP, new BigDecimal(1), LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(2), null,
                1, 1, 1, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), order5, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

        Warehouse warehouse1 = addWarehouseFromModel(new Warehouse(null, "Test Warehouse 1", "Warehouse 1",
                "8:00 - 16:00", company1, location1, new ArrayList<>(), new ArrayList<>()));
        Warehouse warehouse2 = addWarehouseFromModel(new Warehouse(null, "Test Warehouse 2", "Warehouse 2",
                "9:00 - 17:00", company1, location2, new ArrayList<>(), new ArrayList<>()));

        ToolType toolType1 = addToolTypeFromModel(new ToolType(null, "ToolType 1",
                1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), company1));
        ToolType toolType2 = addToolTypeFromModel(new ToolType(null, "ToolType 2",
                2, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), company1));

        Tool tool1 = toolRepository.save(new Tool(null, "Tool 1", LocalDateTime.now(), "T|HARDCODED-TOOL1",
                new ArrayList<>(), warehouse1, new ArrayList<>(), toolType1));
        Tool tool2 = toolRepository.save(new Tool(null, "Tool 2", LocalDateTime.now(), "T|HARDCODED-TOOL2",
                new ArrayList<>(), warehouse2, new ArrayList<>(), toolType2));

        ToolRelease toolRelease1 = addToolReleaseFromModel(new ToolRelease(null, LocalDateTime.now(), LocalDateTime.now(),
                null, tool1, null, orderStage5));
        ToolRelease toolRelease2 = addToolReleaseFromModel(new ToolRelease(null, LocalDateTime.now(), LocalDateTime.now(),
                null, tool2, null, orderStage5));
        ToolRelease toolRelease3 = addToolReleaseFromModel(new ToolRelease(null, LocalDateTime.now(), null,
                null, tool1, null, orderStage5));
        ToolRelease toolRelease4 = addToolReleaseFromModel(new ToolRelease(null, LocalDateTime.now(), null,
                null, tool2, null, orderStage5));


        Element element1 = elementRepository.save(new Element(null, "Element 1", "E|HARDCODED-ELEMENT1", TypeOfUnit.KILOGRAM,
                1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>()));
        Element element2 = elementRepository.save(new Element(null, "Element 2", "E|HARDCODED-ELEMENT2", TypeOfUnit.LITER,
                1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>()));

        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                1, "1", "1", element1, warehouse1));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                2, "2", "1", element2, warehouse1));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                1, "1", "2", element1, warehouse2));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                2, "2", "2", element2, warehouse2));

        ElementReturnRelease elementReturnRelease1 = addElementReturnReleaseFromModel(new ElementReturnRelease(null,
                LocalDateTime.now(), 10, 1, LocalDateTime.now(), warehouseman1, element1,
                null, orderStage5));
        ElementReturnRelease elementReturnRelease2 = addElementReturnReleaseFromModel(new ElementReturnRelease(null,
                LocalDateTime.now(), 5, 0, null, warehouseman1, element2,
                null, orderStage5));

        toolsPlannedNumberRepository.save(new ToolsPlannedNumber(null, 1, toolType1, orderStage6, null));
        toolsPlannedNumberRepository.save(new ToolsPlannedNumber(null, 2, toolType2, orderStage6, null));
        elementsPlannedNumberRepository.save(new ElementsPlannedNumber(null, 1, element1, orderStage6, null));
        elementsPlannedNumberRepository.save(new ElementsPlannedNumber(null, 2, element2, orderStage6, null));

        context.setAuthentication(null);
    }
}
