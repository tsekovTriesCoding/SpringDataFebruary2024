package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TicketImportDto;
import softuni.exam.models.dto.TicketsWrapperDto;
import softuni.exam.models.entity.Passenger;
import softuni.exam.models.entity.Plane;
import softuni.exam.models.entity.Ticket;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.Messages.INVALID_TICKET;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_TICKET;
import static softuni.exam.constants.Paths.TICKETS_PATH;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TownRepository townRepository;
    private final PassengerRepository passengerRepository;
    private final PlaneRepository planeRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtils;
    private final XmlParser xmlParser;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository,
                             TownRepository townRepository,
                             PassengerRepository passengerRepository,
                             PlaneRepository planeRepository,
                             ModelMapper modelMapper,
                             ValidationUtil validationUtils,
                             XmlParser xmlParser) {
        this.ticketRepository = ticketRepository;
        this.townRepository = townRepository;
        this.passengerRepository = passengerRepository;
        this.planeRepository = planeRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(TICKETS_PATH));
    }

    @Override
    public String importTickets() throws JAXBException {
        TicketsWrapperDto ticketsWrapperDto = this.xmlParser.fromFile(TICKETS_PATH, TicketsWrapperDto.class);

        StringBuilder sb = new StringBuilder();
        for (TicketImportDto ticket : ticketsWrapperDto.getTickets()) {
            boolean isValid = this.validationUtils.isValid(ticket);

            if (isValid) {
                Ticket ticketToSave = this.modelMapper.map(ticket, Ticket.class);

                Town fromTown = this.townRepository.findByName(ticket.getFromTown().getName()).get();
                Town toTown = this.townRepository.findByName(ticket.getToTown().getName()).get();

                ticketToSave.setFromTown(fromTown);
                ticketToSave.setToTown(toTown);

                Passenger passenger = this.passengerRepository.findByEmail(ticket.getPassenger().getEmail()).get();

                ticketToSave.setPassenger(passenger);

                Plane plane = this.planeRepository.findByRegisterNumber(ticket.getPlane().getRegisterNumber()).get();

                ticketToSave.setPlane(plane);

                this.ticketRepository.saveAndFlush(ticketToSave);

                sb.append(String.format(SUCCESSFULLY_IMPORTED_TICKET, ticketToSave.getFromTown().getName(), ticketToSave.getToTown().getName()))
                        .append(System.lineSeparator());
            } else {
                sb.append(INVALID_TICKET).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
