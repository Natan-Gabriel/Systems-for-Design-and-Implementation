package web.controller;

import core.domain.Client;
import core.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import web.converter.ClientConverter;
import web.dto.ClientDto;
import web.dto.ClientsDto;


import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by radu.
 */

@RestController
public class ClientController {
    public static final Logger log= LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientConverter clientConverter;


    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    Set<ClientDto> getClients() throws SQLException, IOException, ClassNotFoundException {
        log.trace("getClients - method entered");
        Set<ClientDto> clients= (Set<ClientDto>) clientConverter.convertModelsToDtos(clientService.getAllClients());
        log.trace("getClients - method finished");
        return clients;
        //return new ArrayList<ClientDto>();

    }

    @RequestMapping(value = "/clients/distinct/name/{name}", method = RequestMethod.GET)
    Set<ClientDto> findDistinctByName(@PathVariable String name)  {
        log.trace("findDistinctByName - method entered:id={}",name);
        List<Client> clients = clientService.findDistinctByName(name);
        log.trace("findDistinctByName - method finished:id={}",name);
        return (Set<ClientDto>) clientConverter.convertModelsToDtos(clients);

    }
    @RequestMapping(value = "/clients/distinct/serial/{serial}", method = RequestMethod.GET)
    Set<ClientDto> findDistinctBySerial(@PathVariable String serial)  {
        log.trace("findDistinctByName - method entered:id={}",serial);
        List<Client> clients = clientService.findDistinctBySerial(serial);
        log.trace("findDistinctByName - method finished:id={}",serial);
        return (Set<ClientDto>) clientConverter.convertModelsToDtos(clients);

    }

    @RequestMapping(value = "/clients/get/{id}", method = RequestMethod.GET)
    ClientDto getClient(@PathVariable long id)  {
        log.trace("getClient - method entered:id={}",id);
        Client client = clientService.findOne(id).orElse(null);
        ClientDto result = clientConverter.convertModelToDto(client);
        log.trace("getClient - method finished:id={}",id);
        return result;

    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    ClientDto saveClient(@RequestBody ClientDto clientDto) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("saveClient - method entered: clientDto={}", clientDto);
        ClientDto cl=clientConverter.convertModelToDto(clientService.addClient(
                clientConverter.convertDtoToModel(clientDto)
        ));
        log.trace("saveClient - method finished: clientDto={}", clientDto);
        return cl;
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
    ClientDto updateClient(@RequestBody ClientDto clientDto) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("updateClient - method entered clientDto{}", clientDto);
        ClientDto cl=clientConverter.convertModelToDto( clientService.updateClient(clientDto.getId(),
                clientConverter.convertDtoToModel(clientDto)));
        log.trace("updateClient - method finished clientDto{}", clientDto);
        return cl;
    }


    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClient(@PathVariable Long id) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("deleteClient - method entered id{}", id);
        clientService.deleteClient(id);
        log.trace("deleteClient - method finished id{}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(value = "/clients/filter/{filter}/{size}/{page}", method = RequestMethod.GET)
    Set<ClientDto> filterClientsByName(@PathVariable String filter,@PathVariable long size,@PathVariable long page) {
        log.trace("filterClientsByName - method entered filter{}",filter);
        Set<ClientDto> l= (Set<ClientDto>) clientConverter
                .convertModelsToDtos(clientService.filterClientsByName(filter,size,page));
        log.trace("filterClientsByName - method finished filter{}",filter);
        return l;
    }
    @RequestMapping(value = "/clients/sorted/{size}/{page}/{field}/{direction}", method = RequestMethod.GET)
    List<ClientDto> getClientsSorted(@PathVariable long size,@PathVariable long page,@PathVariable String field,@PathVariable String direction) {
        log.trace("getClientsSorted - method entered ");
        List<ClientDto> l= (List<ClientDto>) clientConverter
                .convertModelsToDtos(clientService.getAllClientsSorted(size,page,field,direction));
        log.trace("getClientsSorted - method finished");
        return l;
    }
    /*
    @GetMapping(params = { "page", "size" })
    Set<ClientDto> findPaginated(@RequestParam("page") int page,
                                   @RequestParam("size") int size, UriComponentsBuilder uriBuilder,
                                   HttpServletResponse response) {
        Set<ClientDto> resultPage = service.findPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<Foo>(
                Foo.class, uriBuilder, response, page, resultPage.getTotalPages(), size));

        return resultPage.getContent();
    }*/
    @RequestMapping(value = "/clients/{size}/{page}", method = RequestMethod.GET)
    List<ClientDto> getClientsSortedandPaginated(@PathVariable long size,@PathVariable long page) {
        log.trace("filterClientsByName - method entered filter{}");
        List<ClientDto> l= (List<ClientDto>) clientConverter
                .convertModelsToDtos(clientService.getAllClientsSortedandPaginated(size,page));
        log.trace("filterClientsByName - method finished filter{}");
        return l;
    }

}
