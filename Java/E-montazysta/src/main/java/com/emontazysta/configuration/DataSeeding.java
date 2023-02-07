package com.emontazysta.configuration;

import com.emontazysta.enums.CompanyStatus;
import com.emontazysta.enums.TypeOfAttachment;
import com.emontazysta.enums.TypeOfPriority;
import com.emontazysta.enums.TypeOfStatus;
import com.emontazysta.model.*;
import com.emontazysta.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private final AttachmentService attachmentService;
    private final CommentService commentService;
    private final ToolTypeService toolTypeService;
    private final ToolEventService toolEventService;
    private final OrderStageService orderStageService;

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

        OrderStage orderStage1 = new OrderStage(null, "Test OrderStage 1", OrderStatus.TODO, new BigDecimal(1),
                1, null, null, null, 1, 1,
                1, new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), order1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        OrderStage orderStage2 = new OrderStage(null, "Test OrderStage 2", OrderStatus.TODO, new BigDecimal(2),
                2, null, null, null, 1, 1,
                1, new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), order1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        OrderStage orderStage3 = new OrderStage(null, "Test OrderStage 3", OrderStatus.TODO, new BigDecimal(3),
                3, null, null, null, 1, 1,
                1, new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), order1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        OrderStage orderStage4 = new OrderStage(null, "Test OrderStage 4", OrderStatus.TODO, new BigDecimal(4),
                1, null, null, null, 1, 1,
                1, new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), order2, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        List<OrderStage> orderStageList = List.of(orderStage1, orderStage2, orderStage3, orderStage4);

        Warehouse warehouse1 = new Warehouse(null, "Test Warehouse 1", "Description",
                "Opening Hours", company1, location1, new ArrayList<>(), new ArrayList<>());
        Warehouse warehouse2 = new Warehouse(null, "Test Warehouse 2", "Description",
                "Opening Hours", company1, location2, new ArrayList<>(), new ArrayList<>());
        Warehouse warehouse3 = new Warehouse(null, "Test Warehouse 3", "Description",
                "Opening Hours", company1, location3, new ArrayList<>(), new ArrayList<>());
        Warehouse warehouse4 = new Warehouse(null, "Test Warehouse 4", "Description",
                "Opening Hours", company2, location4, new ArrayList<>(), new ArrayList<>());
        List<Warehouse> warehouseList = List.of(warehouse1, warehouse2, warehouse3, warehouse4);

        ToolType toolType1 = new ToolType(null, "Test ToolType 1", 10,
                5, 10, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ToolType toolType2 = new ToolType(null, "Test ToolType 2", 10,
                5, 7, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ToolType toolType3 = new ToolType(null, "Test ToolType 3", 10,
                5, 5, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ToolType toolType4 = new ToolType(null, "Test ToolType 4", 10,
                5, 3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        List<ToolType> toolTypeList = List.of(toolType1, toolType2, toolType3, toolType4);

        Tool tool1 = new Tool(null, "Test Tool 1", null, null, new ArrayList<>(),
                warehouse1, new ArrayList<>(), toolType1);
        Tool tool2 = new Tool(null, "Test Tool 2", null, null, new ArrayList<>(),
                warehouse1, new ArrayList<>(), toolType2);
        Tool tool3 = new Tool(null, "Test Tool 3", null, null, new ArrayList<>(),
                warehouse2, new ArrayList<>(), toolType3);
        Tool tool4 = new Tool(null, "Test Tool 4", null, null, new ArrayList<>(),
                warehouse2, new ArrayList<>(), toolType4);
        List<Tool> toolList = List.of(tool1, tool2, tool3,tool4);

        ToolEvent toolEvent1 = new ToolEvent(null, LocalDateTime.now(), null, null,
                "Test ToolEvent 1", TypeOfStatus.IN_PROGRESS, null, null, tool1, new ArrayList<>());
        ToolEvent toolEvent2 = new ToolEvent(null, LocalDateTime.now(), null, null,
                "Test ToolEvent 2", TypeOfStatus.IN_PROGRESS, null, null, tool1, new ArrayList<>());
        ToolEvent toolEvent3 = new ToolEvent(null, LocalDateTime.now(), null, null,
                "Test ToolEvent 3", TypeOfStatus.IN_PROGRESS, null, null, tool2, new ArrayList<>());
        ToolEvent toolEvent4 = new ToolEvent(null, LocalDateTime.now(), null, null,
                "Test ToolEvent 4", TypeOfStatus.IN_PROGRESS, null, null, tool3, new ArrayList<>());
        List<ToolEvent> toolEventList = List.of(toolEvent1, toolEvent2, toolEvent3, toolEvent4);

        Comment comment1 = new Comment(null, "Test Comment 1", null, null,
                null, new ArrayList<>());
        Comment comment2 = new Comment(null, "Test Comment 2", null, null,
                null, new ArrayList<>());
        Comment comment3 = new Comment(null, "Test Comment 3", null, null,
                null, new ArrayList<>());
        Comment comment4 = new Comment(null, "Test Comment 4", null, null,
                null, new ArrayList<>());
        List<Comment> commentList = List.of(comment1, comment2, comment3, comment4);

        Attachment attachment1 = new Attachment(null, "Test Attachment 1", "URL", "Description",
                TypeOfAttachment.OTHER, null, null, comment1, null, null,
                null, null, null, null);
        Attachment attachment2 = new Attachment(null, "Test Attachment 2", "URL", "Description",
                TypeOfAttachment.MANUAL, null, null, comment1, null, null,
                null, null, null, null);
        Attachment attachment3 = new Attachment(null, "Test Attachment 3", "URL", "Description",
                TypeOfAttachment.PROFILE_PICTURE, null, null, null, null, toolEvent1,
                null, null, null, null);
        Attachment attachment4 = new Attachment(null, "Test Attachment 4", "URL", "Description",
                TypeOfAttachment.FAULT_PHOTO, null, null, null, null, toolEvent1,
                null, null, null, null);
        List<Attachment> attachmentList = List.of(attachment1, attachment2, attachment3, attachment4);

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
        orderStageList.forEach(orderStage -> {
            orderStageService.add(orderStage);
        });
        warehouseList.forEach(warehouse -> {
            warehouseService.add(warehouse);
        });
        toolTypeList.forEach(toolType -> {
            toolTypeService.add(toolType);
        });
        toolList.forEach(tool -> {
            toolService.add(tool);
        });
        toolEventList.forEach(toolEvent -> {
            toolEventService.add(toolEvent);
        });
        commentList.forEach(comment -> {
            commentService.add(comment);
        });
        attachmentList.forEach(attachment -> {
            attachmentService.add(attachment);
        });
    }
}
