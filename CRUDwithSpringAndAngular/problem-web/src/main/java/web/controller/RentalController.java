package web.controller;

import core.domain.Rental;
import core.service.RentalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.ClientConverter;
import web.converter.RentalConverter;
import web.dto.*;

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
    private RentalService rentalService;

    @Autowired
    private RentalConverter rentalConverter;
    @Autowired
    private ClientConverter clientConverter;


    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    Set<RentalDto> getRentals() throws SQLException, IOException, ClassNotFoundException {
        log.trace("getRentals - method entered");
        Set<RentalDto> rentals=rentalConverter
                .convertModelsToDtos(rentalService.getAllRentals());
        log.trace("getRentals - method finished");
        return rentals;

    }
    @RequestMapping(value = "/rentals/get/{id}", method = RequestMethod.GET)
    RentalDto getRental(@PathVariable long id)  {
        log.trace("getRental - method entered:id={}",id);
        Rental rental = rentalService.findOne(id).orElse(null);
        RentalDto result = rentalConverter.convertModelToDto(rental);
        log.trace("getRental - method finished:id={}",id);
        return result;

    }

    @RequestMapping(value = "/rentals", method = RequestMethod.POST)
    RentalDto saveRental(@RequestBody RentalDto rentalDto) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("saveRental - method entered: rentalDto={}", rentalDto);
        RentalDto r=rentalConverter.convertModelToDto(rentalService.addRental(
                rentalConverter.convertDtoToModel(rentalDto)
        ));
        log.trace("saveClient - method finished: rentalDto={}", rentalDto);
        return r;
    }

    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.PUT)
    RentalDto updateRental(@RequestBody RentalDto rentalDto) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("updateRental - method entered rentalDto{}", rentalDto);
        RentalDto r=rentalConverter.convertModelToDto( rentalService.updateRental(rentalDto.getId(),
                rentalConverter.convertDtoToModel(rentalDto)));
        log.trace("updateRental - method finished rentalDto{}", rentalDto);
        return r;
    }


    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteRental(@PathVariable Long id) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("deleteRental - method entered id{}", id);
        rentalService.deleteRental(id);
        log.trace("deleteRental - method finished id{}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(value = "/rentals/stats/mostActive", method = RequestMethod.GET)
    Set<ClientDto> getMostActiveClients() throws IOException, ClassNotFoundException {
        log.trace("getMostActiveClient - method entered filter{}");
        Set<ClientDto> l=clientConverter
                .convertModelsToDtos(rentalService.getMostActiveClient());
        log.trace("getMostActiveClient - method finished filter{}");
        return l;
    }
    @RequestMapping(value = "/rentals/sorted/{size}/{page}/{field}/{direction}", method = RequestMethod.GET)
    List<RentalDto> getRentalsSorted(@PathVariable long size, @PathVariable long page, @PathVariable String field, @PathVariable String direction) {
        log.trace("getRentalsSorted - method entered ");
        List<RentalDto> l=rentalConverter
                .convertModelsToDtosList(rentalService.getAllRentalsSorted(size,page,field,direction));
        log.trace("getRentalsSorted - method finished");
        return l;
    }
}

