package com.testproject.ticket.service;

import com.testproject.ticket.domain.dto.CommentModel;
import com.testproject.ticket.domain.dto.CommentModelCreate;
import com.testproject.ticket.exception.DataIsNotCorrectException;
import com.testproject.ticket.exception.TicketNotFoundException;
import com.testproject.ticket.repository.TicketCommentRepository;
import com.testproject.ticket.repository.TicketRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static com.testproject.ticket.Util.*;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentService {

    TicketRepository ticketRepository;

    TicketCommentRepository commentRepository;


    public CommentService(TicketRepository ticketRepository, TicketCommentRepository commentRepository) {
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
    }

    public CommentModel create(CommentModelCreate modelCreate) {
        if (modelCreate != null) {
            var ticket = ticketRepository.findById(modelCreate.getTicketId());
            var ticketEntity = ticket.orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
            var entityFromModel = createNewCommentEntityFromModel(modelCreate);
            entityFromModel.setTicket(ticketEntity);
            var ticketComment = commentRepository.save(entityFromModel);

            return prepareCommentEntityToTransferModel(ticketComment);
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }
}
