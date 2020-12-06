package com.testproject.ticket.service;

import com.testproject.ticket.domain.Ticket;
import com.testproject.ticket.domain.dto.ticket.TicketModeDelete;
import com.testproject.ticket.domain.dto.ticket.TicketModel;
import com.testproject.ticket.domain.dto.ticket.TicketModelCreate;
import com.testproject.ticket.domain.dto.ticket.TicketModelEdit;
import com.testproject.ticket.exception.DataIsNotCorrectException;
import com.testproject.ticket.exception.EntityNotFoundException;
import com.testproject.ticket.exception.PermissionToActionIsAbsentException;
import com.testproject.ticket.repository.TicketRepository;
import com.testproject.ticket.utils.Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.testproject.ticket.utils.Util.*;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public TicketModel create(TicketModelCreate modelCreate) {
        if (modelCreate != null) {
            var entityFromModel = createNewTicketEntityFromModel(modelCreate);
            var ticket = ticketRepository.save(entityFromModel);

            return prepareTicketEntityToTransferModel(ticket);
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }

    public List<TicketModel> getAll() {
        var ticketList = ticketRepository.findAll();
        if (!ticketList.isEmpty()) {
            return ticketList.stream().map(Util::prepareTicketEntityToTransferModel)
                    .sorted(Comparator.comparing(TicketModel::getId)).collect(Collectors.toList());
        } else throw new EntityNotFoundException("Tickets not found.");
    }

    @Transactional
    public void deleteById(TicketModeDelete deleteModel) {
        if (deleteModel != null) {
            var entity = findTicket(deleteModel.getId());
            if (entity.getCreatedBy().equals(deleteModel.getCreatedBy())) {
                ticketRepository.deleteById(deleteModel.getId());
            } else
                throw new PermissionToActionIsAbsentException("You cannot delete a ticket that you are not the author of");
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }

    @Transactional
    public TicketModel editTicket(TicketModelEdit modelEdit) {
        if (modelEdit != null) {
            var entity = findTicket(modelEdit.getId());
            var updateModel = createUpdateTicketModel(modelEdit, entity);
            return prepareTicketEntityToTransferModel(ticketRepository.save(updateModel));
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }

    private Ticket findTicket(long id) {
        var ticket = ticketRepository.findById(id);
        return ticket.orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
    }
}
