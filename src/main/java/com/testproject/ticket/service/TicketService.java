package com.testproject.ticket.service;

import com.testproject.ticket.Util;
import com.testproject.ticket.domain.Ticket;
import com.testproject.ticket.domain.dto.TicketModel;
import com.testproject.ticket.domain.dto.TicketModelCreate;
import com.testproject.ticket.domain.dto.TicketModelEdit;
import com.testproject.ticket.exception.DataIsNotCorrectException;
import com.testproject.ticket.exception.TicketNotFoundException;
import com.testproject.ticket.exception.TicketsNotFoundException;
import com.testproject.ticket.repository.TicketCommentRepository;
import com.testproject.ticket.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.testproject.ticket.Util.*;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final TicketCommentRepository commentRepository;

    public TicketService(TicketRepository ticketRepository, TicketCommentRepository commentRepository) {
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
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
                    .collect(Collectors.toList());
        } else throw new TicketsNotFoundException("Tickets not found.");
    }

    @Transactional
    public void deleteById(Long id) {
        ticketRepository.deleteById(id);
    }

    @Transactional
    public TicketModel editTicket(TicketModelEdit modelEdit) {
        var ticket = ticketRepository.findById(modelEdit.getId());
        var entity = ticket.orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
        Ticket updateModel = createUpdateTicketModel(modelEdit, entity);
        return prepareTicketEntityToTransferModel(ticketRepository.save(updateModel));
    }
}
