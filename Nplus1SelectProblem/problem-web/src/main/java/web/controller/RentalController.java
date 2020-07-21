package web.controller;

//import core.service.RentalService;
import core.domain.Client;
import core.domain.ClientMovie;
import core.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.ClientConverter;
import web.converter.ClientMovieConverter;
import web.dto.ClientDto;
import web.dto.ClientMovieDto;
import web.dto.EmptyJsonResponse;
import web.dto.RentalDto;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@RestController
public class RentalController {
    public static final Logger log= LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientConverter clientConverter;
    @Autowired
    private ClientMovieConverter clientMovieConverter;


    @RequestMapping(value = "/rentals/clients", method = RequestMethod.GET)
    public Set<ClientDto> getClientsWithRentalsAndMovies() {
        log.trace("getClientMovies: clientId={}");
        Set<ClientDto> rentals= (Set<ClientDto>) clientConverter.convertModelsToDtos(clientService.getClientsWithRentalsAndMovies());
        return rentals;
        //throw new RuntimeException("not yet implemented");
    }

    @RequestMapping(value = "/rentals/{clientId}", method = RequestMethod.GET)
    public Set<ClientDto> getClientMovies(
            @PathVariable final Long clientId) {
        log.trace("getClientMovies: clientId={}", clientId);
        Set<ClientDto> rentals= (Set<ClientDto>) clientConverter.convertModelsToDtos(clientService.getClientMovies(clientId));
        return rentals;
        //throw new RuntimeException("not yet implemented");
    }

    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    Set<ClientMovieDto> getRentals() throws SQLException, IOException, ClassNotFoundException {
        log.trace("getRentals - method entered");
        Set<ClientMovieDto> rentals= (Set<ClientMovieDto>) clientMovieConverter
                .convertModelsToDtos(clientService.getAllRentals());
        log.trace("getRentals - method finished");
        return rentals;

    }
    //    @RequestMapping(value = "/rentals/get/{id}", method = RequestMethod.GET)
//    ClientMovieDto getRental(@PathVariable long id)  {
//        log.trace("getRental - method entered:id={}",id);
//        Rental rental = rentalService.findOne(id).orElse(null);
//        ClientMovieDto result = rentalConverter.convertModelToDto(rental);
//        log.trace("getRental - method finished:id={}",id);
//        return result;
//
//    }
//
    //rentalService.updateRental(movieRental.getClientId(), movieRental.getMovieId(), movieRental.getRating());
//    @RequestMapping(value = "/rentals", method = RequestMethod.POST)
//    ClientMovieDto saveRental(@PathVariable final Long clientId,
//                              @RequestBody final Long movieId,
//                              @RequestBody final String serial
//                              ) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException, TransformerException, ParserConfigurationException {
//        log.trace("saveRental - method entered: ClientMovieDto={}");
//        clientService.addRental(
//                clientId,movieId,serial);
//
//        log.trace("saveClient - method finished: ClientMovieDto={}");
//        return new ClientMovieDto();
//    }
//    @RequestMapping(value = "/rentals/{clientId}", method = RequestMethod.POST)
//    ResponseEntity saveRental(@PathVariable final Long clientId,
//                              @RequestBody final Long movieId,
//                              @RequestBody final String serial
//                              ) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException, TransformerException, ParserConfigurationException {
//        log.trace("saveRental - method entered: ClientMovieDto={}");
//        clientService.addRental(
//                clientId,movieId,serial);
//
//        log.trace("saveClient - method finished: ClientMovieDto={}");
//        return new ResponseEntity(HttpStatus.OK);
//    }
    @RequestMapping(value = "/rentals", method = RequestMethod.POST)
    ClientMovieDto saveRental(@RequestBody ClientMovieDto rentalDto) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("saveRental - method entered: clientDto={}");
        clientService.addRental(
                rentalDto.getClient(),rentalDto.getMovie(),rentalDto.getRentalSerialNumber());
        //);
        log.trace("saveRental - method finished: clientDto={}");
        return new ClientMovieDto();
    }

    @RequestMapping(value = "/rentals/{clientId}/{movieId}", method = RequestMethod.PUT)
    ClientMovieDto updateRental(@PathVariable final Long clientId,
                                @PathVariable final Long movieId,
                                //@RequestBody final String serial) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
                                @RequestBody ClientMovieDto rentalDto) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("updateRental - method entered ClientMovieDto{}");
        clientService.updateRental(clientId,
                movieId,rentalDto.getRentalSerialNumber(),true);
        log.trace("updateRental - method finished ClientMovieDto{}");
        return new ClientMovieDto();
    }
    //
//
    @RequestMapping(value = "/rentals/{clientId}/{movieId}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteRental(@PathVariable final Long clientId,
                                   @PathVariable final Long movieId) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("deleteRental - method entered id{}");
        clientService.deleteRental(clientId,movieId);
        log.trace("deleteRental - method finished id{}");
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
//    Set<ClientMovieDto> getRentals() throws SQLException, IOException, ClassNotFoundException {
//        log.trace("getRentals - method entered");
//        Set<ClientMovieDto> rentals=rentalConverter
//                .convertModelsToDtos(rentalService.getAllRentals());
//        log.trace("getRentals - method finished");
//        return rentals;
//
//    }
//    @RequestMapping(value = "/rentals/get/{id}", method = RequestMethod.GET)
//    ClientMovieDto getRental(@PathVariable long id)  {
//        log.trace("getRental - method entered:id={}",id);
//        Rental rental = rentalService.findOne(id).orElse(null);
//        ClientMovieDto result = rentalConverter.convertModelToDto(rental);
//        log.trace("getRental - method finished:id={}",id);
//        return result;
//
//    }
//
//    @RequestMapping(value = "/rentals", method = RequestMethod.POST)
//    ClientMovieDto saveRental(@RequestBody ClientMovieDto ClientMovieDto) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
//        log.trace("saveRental - method entered: ClientMovieDto={}", ClientMovieDto);
//        ClientMovieDto r=rentalConverter.convertModelToDto(rentalService.addRental(
//                rentalConverter.convertDtoToModel(ClientMovieDto)
//        ));
//        log.trace("saveClient - method finished: ClientMovieDto={}", ClientMovieDto);
//        return r;
//    }
//
//    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.PUT)
//    ClientMovieDto updateRental(@RequestBody ClientMovieDto ClientMovieDto) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
//        log.trace("updateRental - method entered ClientMovieDto{}", ClientMovieDto);
//        ClientMovieDto r=rentalConverter.convertModelToDto( rentalService.updateRental(ClientMovieDto.getId(),
//                rentalConverter.convertDtoToModel(ClientMovieDto)));
//        log.trace("updateRental - method finished ClientMovieDto{}", ClientMovieDto);
//        return r;
//    }
//
//
//    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.DELETE)
//    ResponseEntity<?> deleteRental(@PathVariable Long id) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
//        log.trace("deleteRental - method entered id{}", id);
//        rentalService.deleteRental(id);
//        log.trace("deleteRental - method finished id{}", id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//    @RequestMapping(value = "/rentals/stats/mostActive", method = RequestMethod.GET)
//    Set<ClientDto> getMostActiveClients() throws IOException, ClassNotFoundException {
//        log.trace("getMostActiveClient - method entered filter{}");
//        Set<ClientDto> l=clientConverter
//                .convertModelsToDtos(rentalService.getMostActiveClient());
//        log.trace("getMostActiveClient - method finished filter{}");
//        return l;
//    }
//    @RequestMapping(value = "/rentals/sorted/{size}/{page}/{field}/{direction}", method = RequestMethod.GET)
//    List<ClientMovieDto> getRentalsSorted(@PathVariable long size, @PathVariable long page, @PathVariable String field, @PathVariable String direction) {
//        log.trace("getRentalsSorted - method entered ");
//        List<ClientMovieDto> l=rentalConverter
//                .convertModelsToDtosList(rentalService.getAllRentalsSorted(size,page,field,direction));
//        log.trace("getRentalsSorted - method finished");
//        return l;
//    }
}

