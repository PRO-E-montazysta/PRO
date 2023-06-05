package com.emontazysta.configuration;

import com.emontazysta.enums.*;
import com.emontazysta.mapper.*;
import com.emontazysta.model.*;
import com.emontazysta.model.dto.OrderStageWithToolsAndElementsDto;
import com.emontazysta.model.dto.ToolsPlannedNumberDto;
import com.emontazysta.model.dto.UnavailabilityWithLocalDateDto;
import com.emontazysta.repository.*;
import com.emontazysta.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.DependsOn;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    private final WarehouseService warehouseService;
    private final ToolService toolService;
    private final AttachmentService attachmentService;
    private final CommentService commentService;
    private final ToolTypeService toolTypeService;
    private final ToolEventRepository toolEventRepository;
    private final OrderStageService orderStageService;
    private final ToolReleaseService toolReleaseService;
    private final ElementService elementService;
    private final DemandAdHocService demandAdHocService;
    private final ElementEventRepository elementEventRepository;
    private final ElementInWarehouseRepository elementInWarehouseRepository;
    private final ElementReturnReleaseService elementReturnReleaseService;
    private final AppUserService appUserService;
    private final EmploymentRepository employmentRepository;
    private final UnavailabilityService unavailabilityService;
    private final ToolRepository toolRepository;
    private final ElementRepository elementRepository;

    private final CompanyMapper companyMapper;
    private final ClientMapper clientMapper;
    private final LocationMapper locationMapper;
    private final OrdersMapper ordersMapper;
    private final OrderStageMapper orderStageMapper;
    private final WarehouseMapper warehouseMapper;
    private final ToolTypeMapper toolTypeMapper;
    private final ToolMapper toolMapper;
    private final ToolEventMapper toolEventMapper;
    private final CommentMapper commentMapper;
    private final AttachmentMapper attachmentMapper;
    private final DemandAdHocMapper demandAdHocMapper;
    private final ToolReleaseMapper toolReleaseMapper;
    private final ElementMapper elementMapper;
    private final ElementEventMapper elementEventMapper;
    private final ElementInWarehouseMapper elementInWarehouseMapper;
    private final ElementReturnReleaseMapper elementReturnReleaseMapper;
    private final UnavailabilityMapper unavailabilityMapper;

    private Company addCompanyFromModel(Company company) {
        return companyMapper.toEntity(companyService.add(companyMapper.toDto(company)));
    }

    private Unavailability addUnavailabilityFromModel(Unavailability unavailability) {
        return unavailabilityMapper.toEntity(unavailabilityService.add(unavailabilityMapper.toDto(unavailability)));
    }

    private Unavailability addUnavailabilityWithLocalDateDtoFromModel(UnavailabilityWithLocalDateDto unavailability) {
        return unavailabilityMapper.toEntity(unavailabilityService.addWithLocalDate(unavailability));
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
        return orderStageMapper.toEntity(orderStageService.add(orderStageMapper.toDto(orderStage)));
    }

    private DemandAdHoc addDemandAdHocFromModel(DemandAdHoc demandAdHoc) {
        return demandAdHocMapper.toEntity(demandAdHocService.add(demandAdHocMapper.toDto(demandAdHoc)));
    }

    private Warehouse addWarehouseFromModel(Warehouse warehouse) {
        return warehouseMapper.toEntity(warehouseService.add(warehouseMapper.toDto(warehouse)));
    }

    private ToolType addToolTypeFromModel(ToolType toolType) {
        return toolTypeMapper.toEntity(toolTypeService.add(toolTypeMapper.toDto(toolType)));
    }

    private Tool addToolFromModel(Tool tool) {
        return toolMapper.toEntity(toolService.add(toolMapper.toDto(tool)));
    }

    private ToolEvent addToolEventFromModel(ToolEvent toolEvent) {
        return toolEventRepository.save(toolEvent);
    }

    private Comment addCommentFromModel(Comment comment) {
        return commentMapper.toEntity(commentService.add(commentMapper.toDto(comment)));
    }

    private Attachment addAttachmentFromModel(Attachment attachment) {
        return attachmentMapper.toEntity(attachmentService.add(attachmentMapper.toDto(attachment), new MockMultipartFile("test", "test.txt", null, new byte[0])));
    }

    private ToolRelease addToolReleaseFromModel(ToolRelease toolRelease) {
        return toolReleaseMapper.toEntity(toolReleaseService.add(toolReleaseMapper.toDto(toolRelease)));
    }

    private Element addElementFromModel(Element element) {
        return elementMapper.toEntity(elementService.add(elementMapper.toDto(element)));
    }

    private ElementEvent addElementEventFromModel(ElementEvent elementEvent) {
        return elementEventRepository.save(elementEvent);
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

        AppUser appUser = new AppUser(null, "Test AppUser", "Test AppUser", "em@i.l",
                "password", "testuser", null, Set.of(Role.CLOUD_ADMIN));

        Fitter fitter1 = new Fitter(null, "Fitter1", "Montażysta1", "fitter1@ema.il",
                "password", "fitter1", null, Set.of(Role.FITTER), "+48123123123",
                "92122637693", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Fitter fitter2 = new Fitter(null, "Fitter2", "Montażysta2", "fitter2@ema.il",
                "password", "fitter2", null, Set.of(Role.FITTER), "+48231231231",
                "81081748975", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Fitter fitter3 = new Fitter(null, "Fitter3", "Montażysta3", "fitter1@ema.il",
                "password", "fitter3", null, Set.of(Role.FITTER), "+48312312312",
                "03261768113", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
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
        List<AppUser> appUserList = List.of(appUser, fitter1, fitter2, fitter3, foreman1, foreman2, warehouseman1, warehouseman2,
                warehouseManager1, warehouseManager2, specialist1, specialist2, salesRepresentative1, salesRepresentative2,
                manager1, manager2, companyAdmin1);
        appUserList.forEach(appUserService::add);

        Company company1 = addCompanyFromModel(new Company(null, "Test Comapny 1", null,
                CompanyStatus.ACTIVE, "They pay", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>()));
        Company company2 = addCompanyFromModel(new Company(null, "Test Comapny 2", null,
                CompanyStatus.ACTIVE, "They pay", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>()));
        Company company3 = addCompanyFromModel(new Company(null, "Test Comapny 3", null,
                CompanyStatus.SUSPENDED, "They don't pay", new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Company company4 = addCompanyFromModel(new Company(null, "Test Comapny 4", null,
                CompanyStatus.DISABLED, "Closed company", new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

        Employment employment1 = employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, fitter1));
        Employment employment2 = employmentRepository.save(new Employment(null, LocalDateTime.now(), LocalDateTime.now(), company1, fitter2));
        Employment employment3 = employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, foreman1));
        Employment employment4 = employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, foreman2));
        Employment employment5 = employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, warehouseman1));
        Employment employment6 = employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, warehouseman2));
        Employment employment7 = employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, warehouseManager1));
        Employment employment8 = employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, warehouseManager2));
        Employment employment9 = employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, specialist1));
        Employment employment10 = employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company2, specialist2));
        Employment employment11 = employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, salesRepresentative1));
        Employment employment12 = employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company2, salesRepresentative2));
        Employment employment13 = employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, manager1));
        Employment employment14 = employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company3, manager2));
        Employment employment15 = employmentRepository.save(new Employment(null, LocalDateTime.now(), null, company1, companyAdmin1));

        context.setAuthentication(null);
        Authentication authenticationMng = new UsernamePasswordAuthenticationToken(
                manager1.getUsername(), manager1.getPassword());
        context.setAuthentication(authenticationMng);

        Unavailability unavailability1 = addUnavailabilityFromModel(new Unavailability(null,
                TypeOfUnavailability.BUSY, "Test Unavailability 1", LocalDateTime.parse( "2023-03-06T12:00:00.000"),
                LocalDateTime.parse("2023-03-06T23:00:00.000"), fitter1, manager1));
        Unavailability unavailability2 = addUnavailabilityWithLocalDateDtoFromModel(new UnavailabilityWithLocalDateDto(
                null, TypeOfUnavailability.BEREAVEMENT_LEAVE,"Test Unavailability 2", LocalDate.now(),
                LocalDate.now(), fitter2.getId(), manager1.getId()));

        Client client1 = addClientFromModel(new Client(null, "Test Client 1",
                "em@i.l", company1, new ArrayList<>()));
        Client client2 = addClientFromModel(new Client(null, "Test Client 2",
                "em@i.l", company1, new ArrayList<>()));
        Client client3 = addClientFromModel(new Client(null, "Test Client 3",
                "em@i.l", company1, new ArrayList<>()));
        Client client4 = addClientFromModel(new Client(null, "Test Client 4",
                "em@i.l", company1, new ArrayList<>()));

        Location location1 = addLocationFromModel(new Location(null, 1.1,
                1.1, "Miasto1", "Miła", "1",
                "1A", "01-123", null, null));
        Location location2 = addLocationFromModel(new Location(null, 1.1,
                1.1, "Miasto1", "Miła", "1",
                "1B", "01-123", null, null));
        Location location3 = addLocationFromModel(new Location(null, 1.1,
                1.1, "Miasto2", "Ładna", "1",
                null, "01-124", null, null));
        Location location4 = addLocationFromModel(new Location(null, 1.1,
                1.1, "Miasto3", "Pogodna", "1",
                null, null, null, null));

        Orders order1 = addOrdersFromModel(new Orders(null, "Test Order 1 - from Client 1",
                OrderStatus.CREATED, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), null, TypeOfPriority.IMPORTANT,
                company1, manager1, foreman1, specialist1, salesRepresentative1, location1, client1, new ArrayList<>(),
                new ArrayList<>()));
        Orders order2 = addOrdersFromModel(new Orders(null, "Test Order 2 - from Client 1",
                OrderStatus.CREATED, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), null, TypeOfPriority.NORMAL,
                company1, manager2, foreman2, specialist2, salesRepresentative2, location2, client1, new ArrayList<>(),
                new ArrayList<>()));
        Orders order3 = addOrdersFromModel(new Orders(null, "Test Order 3 - from Client 2",
                OrderStatus.CREATED, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), null, TypeOfPriority.IMMEDIATE,
                company1, manager1, foreman1, specialist1, salesRepresentative1, location3, client2, new ArrayList<>(),
                new ArrayList<>()));
        Orders order4 = addOrdersFromModel(new Orders(null, "Test Order 4 - from Client 4",
                OrderStatus.CREATED, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), null, TypeOfPriority.NORMAL,
                company4, null, null, null, null, null, client4,
                new ArrayList<>(), new ArrayList<>()));

        OrderStage orderStage1 = addOrderStageFromModel(new OrderStage(null, "Test OrderStage 1",
                OrderStageStatus.PLANNING, new BigDecimal(1), LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),
                1, 1, 1, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), order1, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        OrderStage orderStage2 = addOrderStageFromModel(new OrderStage(null, "Test OrderStage 2",
                OrderStageStatus.PLANNING, new BigDecimal(2), LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), null,
                1, 1, 1, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), order1, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        OrderStage orderStage3 = addOrderStageFromModel(new OrderStage(null, "Test OrderStage 3",
                OrderStageStatus.PLANNING, new BigDecimal(3), LocalDateTime.now(), LocalDateTime.now(), null, null,
                1, 1, 1, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), order1, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        OrderStage orderStage4 = addOrderStageFromModel(new OrderStage(null, "Test OrderStage 4",
                OrderStageStatus.PLANNING, new BigDecimal(4), LocalDateTime.now(), LocalDateTime.now(), null, null,
                1, 1, 1, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), order2, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

        DemandAdHoc demandAdHoc1 = addDemandAdHocFromModel(new DemandAdHoc(null, "Test DemandAdHoc 1",
                LocalDateTime.now(), new ArrayList<>(), new ArrayList<>(), warehouseManager1, specialist1, foreman1,
                orderStage1, new ArrayList<>(), new ArrayList<>()));
        DemandAdHoc demandAdHoc2 = addDemandAdHocFromModel(new DemandAdHoc(null, "Test DemandAdHoc 2",
                LocalDateTime.now(), new ArrayList<>(), new ArrayList<>(), warehouseManager1, specialist1, foreman1,
                orderStage1, new ArrayList<>(), new ArrayList<>()));
        DemandAdHoc demandAdHoc3 = addDemandAdHocFromModel(new DemandAdHoc(null, "Test DemandAdHoc 3",
                LocalDateTime.now(), new ArrayList<>(), new ArrayList<>(), warehouseManager2, specialist2, foreman2,
                orderStage2, new ArrayList<>(), new ArrayList<>()));
        DemandAdHoc demandAdHoc4 = addDemandAdHocFromModel(new DemandAdHoc(null, "Test DemandAdHoc 4",
                LocalDateTime.now(), new ArrayList<>(), new ArrayList<>(), warehouseManager2, specialist2, foreman2,
                orderStage4, new ArrayList<>(), new ArrayList<>()));

        Warehouse warehouse1 = addWarehouseFromModel(new Warehouse(null, "Test Warehouse 1", "Warehouse 1",
                "8:00 - 16:00", company1, null, new ArrayList<>(), new ArrayList<>()));
        Warehouse warehouse2 = addWarehouseFromModel(new Warehouse(null, "Test Warehouse 2", "Warehouse 2",
                "9:00 - 17:00", company1, location2, new ArrayList<>(), new ArrayList<>()));
        Warehouse warehouse3 = addWarehouseFromModel(new Warehouse(null, "Test Warehouse 3", "Warehouse 3",
                "8:00 - 10:00", company1, location3, new ArrayList<>(), new ArrayList<>()));
        Warehouse warehouse4 = addWarehouseFromModel(new Warehouse(null, "Test Warehouse 4", "Warehouse 4",
                "8:00 - 15:00", company2, location4, new ArrayList<>(), new ArrayList<>()));

        ToolType toolType1 = addToolTypeFromModel(new ToolType(null, "Test ToolType 1",
                5, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), company1));
        ToolType toolType2 = addToolTypeFromModel(new ToolType(null, "Test ToolType 2",
                5, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), company1));
        ToolType toolType3 = addToolTypeFromModel(new ToolType(null, "Test ToolType 3",
                5, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), company1));
        ToolType toolType4 = addToolTypeFromModel(new ToolType(null, "Test ToolType 4",
                5, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), company1));

        Tool tool1 = addToolFromModel(new Tool(null, "Test Tool 1", null, null, new ArrayList<>(),
                warehouse1, new ArrayList<>(), toolType1));
        Tool tool2 = addToolFromModel(new Tool(null, "Test Tool 2", null, null, new ArrayList<>(),
                warehouse1, new ArrayList<>(), toolType2));
        Tool tool3 = addToolFromModel(new Tool(null, "Test Tool 3", null, null, new ArrayList<>(),
                warehouse2, new ArrayList<>(), toolType3));
        Tool tool4 = addToolFromModel(new Tool(null, "Test Tool 4", null, null, new ArrayList<>(),
                warehouse2, new ArrayList<>(), toolType4));
        toolRepository.save(new Tool(null, "Tool 5", LocalDateTime.now(), "T|HARDCODED-TOOL5",
                new ArrayList<>(), warehouse1, new ArrayList<>(), toolType1));
        toolRepository.save(new Tool(null, "Tool 6", LocalDateTime.now(), "T|HARDCODED-TOOL6",
                new ArrayList<>(), warehouse2, new ArrayList<>(), toolType2));

        ToolEvent toolEvent1 = addToolEventFromModel(new ToolEvent(null, LocalDateTime.now(), null, null,
                "Test ToolEvent 1", EventStatus.CREATED, fitter1, null, tool1, new ArrayList<>()));
        ToolEvent toolEvent2 = addToolEventFromModel(new ToolEvent(null, LocalDateTime.now(), null, null,
                "Test ToolEvent 2", EventStatus.CREATED, fitter1, null, tool1, new ArrayList<>()));
        ToolEvent toolEvent3 = addToolEventFromModel(new ToolEvent(null, LocalDateTime.now(), null, null,
                "Test ToolEvent 3", EventStatus.CREATED, foreman1, null, tool2, new ArrayList<>()));
        ToolEvent toolEvent4 = addToolEventFromModel(new ToolEvent(null, LocalDateTime.now(), null, null,
                "Test ToolEvent 4", EventStatus.CREATED, warehouseman1, null, tool3, new ArrayList<>()));

        Comment comment1 = addCommentFromModel(new Comment(null, "Test Comment 1", null, fitter1,
                orderStage1, new ArrayList<>()));
        Comment comment2 = addCommentFromModel(new Comment(null, "Test Comment 2", null, fitter1,
                orderStage1, new ArrayList<>()));
        Comment comment3 = addCommentFromModel(new Comment(null, "Test Comment 3", null, fitter2,
                orderStage1, new ArrayList<>()));
        Comment comment4 = addCommentFromModel(new Comment(null, "Test Comment 4", null, fitter2,
                orderStage1, new ArrayList<>()));

        Attachment attachment1 = addAttachmentFromModel(new Attachment(null, "Test Attachment 1", "URL",
                "Description", TypeOfAttachment.OTHER, null, null, comment1, null,
                null, null, null, null, null));
        Attachment attachment2 = addAttachmentFromModel(new Attachment(null, "Test Attachment 2", "URL",
                "Description", TypeOfAttachment.MANUAL, null, null, comment1, null,
                null, null, null, null, null));
        Attachment attachment3 = addAttachmentFromModel(new Attachment(null, "Test Attachment 3", "URL",
                "Description", TypeOfAttachment.PROFILE_PICTURE, null, null, null,
                manager1, null, null, null, null, null));
        Attachment attachment4 = addAttachmentFromModel(new Attachment(null, "Test Attachment 4", "URL",
                "Description", TypeOfAttachment.FAULT_PHOTO, null, null, null,
                null, toolEvent1, null, null, null, null));

        ToolRelease toolRelease1 = addToolReleaseFromModel(new ToolRelease(null, LocalDateTime.now(), LocalDateTime.now(),
                null, tool1, demandAdHoc1, orderStage1));
        ToolRelease toolRelease2 = addToolReleaseFromModel(new ToolRelease(null, LocalDateTime.now(), LocalDateTime.now(),
                null, tool2, null, orderStage1));
        ToolRelease toolRelease3 = addToolReleaseFromModel(new ToolRelease(null, LocalDateTime.now(), null,
                null, tool1, null, orderStage2));
        ToolRelease toolRelease4 = addToolReleaseFromModel(new ToolRelease(null, LocalDateTime.now(), null,
                null, tool2, null, orderStage3));

        Element element1 = addElementFromModel(new Element(null, "Test Element 1", null, TypeOfUnit.KILOGRAM,
                1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>()));
        Element element2 = addElementFromModel(new Element(null, "Test Element 2", null, TypeOfUnit.KILOGRAM,
                2, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>()));
        Element element3 = addElementFromModel(new Element(null, "Test Element 3", null, TypeOfUnit.LITER,
                3.3f, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>()));
        Element element4 = addElementFromModel(new Element(null, "Test Element 4", null, TypeOfUnit.PIECE,
                1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>()));
        Element element5 = elementRepository.save(new Element(null, "Element 5", "E|HARDCODED-ELEMENT5", TypeOfUnit.KILOGRAM,
                1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>()));
        Element element6 = elementRepository.save(new Element(null, "Element 6", "E|HARDCODED-ELEMENT6", TypeOfUnit.LITER,
                1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>()));

        ElementEvent elementEvent1 = addElementEventFromModel(new ElementEvent(null, LocalDateTime.now(),
                null, null, "Test ElementEvent 1", EventStatus.CREATED, 1,
                null, fitter1, element1, new ArrayList<>()));
        ElementEvent elementEvent2 = addElementEventFromModel(new ElementEvent(null, LocalDateTime.now(),
                null, null, "Test ElementEvent 2", EventStatus.CREATED, 1,
                null, fitter1, element1, new ArrayList<>()));
        ElementEvent elementEvent3 = addElementEventFromModel(new ElementEvent(null, LocalDateTime.now(),
                null, null, "Test ElementEvent 3", EventStatus.CREATED, 1,
                null, foreman1, element2, new ArrayList<>()));
        ElementEvent elementEvent4 = addElementEventFromModel(new ElementEvent(null, LocalDateTime.now(),
                null, null, "Test ElementEvent 4", EventStatus.CREATED, 1,
                null, warehouseman1, element3, new ArrayList<>()));

        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                1, "1", "1", element1, warehouse1));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                2, "2", "1", element2, warehouse1));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                3, "3", "1", element3, warehouse1));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                4, "4", "1", element4, warehouse1));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                1, "1", "2", element1, warehouse2));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                2, "2", "2", element2, warehouse2));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                3, "3", "2", element3, warehouse2));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                4, "4", "2", element4, warehouse2));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                1, "1", "3", element1, warehouse3));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                2, "2", "3", element2, warehouse3));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                3, "3", "3", element3, warehouse3));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                4, "4", "3", element4, warehouse3));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                51, "5", "1", element5, warehouse1));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                61, "6", "1", element6, warehouse1));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                52, "5", "2", element5, warehouse2));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                62, "6", "2", element6, warehouse2));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                53, "5", "3", element5, warehouse3));
        addElementInWarehouseFromModel(new ElementInWarehouse(null,
                63, "6", "3", element6, warehouse3));

        ElementReturnRelease elementReturnRelease1 = addElementReturnReleaseFromModel(new ElementReturnRelease(null,
                LocalDateTime.now(), 1, 1, LocalDateTime.now(), warehouseman1, element1,
                demandAdHoc1, orderStage1));
        ElementReturnRelease elementReturnRelease2 = addElementReturnReleaseFromModel(new ElementReturnRelease(null,
                LocalDateTime.now(), 1, 0, null, warehouseman1, element2,
                demandAdHoc1, orderStage1));
        ElementReturnRelease elementReturnRelease3 = addElementReturnReleaseFromModel(new ElementReturnRelease(null,
                LocalDateTime.now(), 1, 0, null, warehouseManager1, element3,
                demandAdHoc3, orderStage1));
        ElementReturnRelease elementReturnRelease4 = addElementReturnReleaseFromModel(new ElementReturnRelease(null,
                LocalDateTime.now(), 1, 0, null, warehouseman2, element4,
                demandAdHoc4, orderStage1));

        context.setAuthentication(null);
    }
}
