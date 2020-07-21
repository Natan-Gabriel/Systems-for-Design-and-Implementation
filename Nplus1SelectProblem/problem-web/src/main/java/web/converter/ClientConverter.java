package web.converter;


import core.domain.Client;
import org.springframework.stereotype.Component;
import web.dto.ClientDto;


/**
 * Created by radu.
 */
@Component
public class ClientConverter extends AbstractConverterBaseEntityConverter<Client, ClientDto> {
    @Override
    public Client convertDtoToModel(ClientDto dto) {
        Client client = Client.builder()
                .serialNumber(dto.getSerialNumber())
                .name(dto.getName())
                .age(dto.getAge())
                .build();
        client.setId(dto.getId());
        return client;
    }

    @Override
    public ClientDto convertModelToDto(Client client) {
        ClientDto dto = ClientDto.builder()
                .serialNumber(client.getSerialNumber())
                .name(client.getName())
                .age(client.getAge())
                .build();
        dto.setId(client.getId());
        return dto;
    }
}