package com.emontazysta.mapper;

import com.emontazysta.model.Client;
import com.emontazysta.model.dto.ClientDto;

public class ClientMapper {

    public static ClientDto clientToDto(Client client){
        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .contactDetails(client.getContactDetails())
                .build();
    }
}
