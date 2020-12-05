package com.testproject.ticket.service;

import com.testproject.ticket.Util;
import com.testproject.ticket.domain.dto.TicketModel;
import com.testproject.ticket.domain.dto.TicketModelCreate;
import com.testproject.ticket.exception.DataIsNotCorrectException;
import com.testproject.ticket.repository.TicketCommentRepository;
import com.testproject.ticket.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.testproject.ticket.Util.createNewTicketEntityFromModel;
import static com.testproject.ticket.Util.prepareTicketEntityToTransferModel;

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
        var all = ticketRepository.findAll();
        return all.stream().map(Util::prepareTicketEntityToTransferModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        ticketRepository.deleteById(id);
    }

}
