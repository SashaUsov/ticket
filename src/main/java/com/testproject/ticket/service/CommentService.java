package com.testproject.ticket.service;

import com.testproject.ticket.repository.TicketCommentRepository;
import com.testproject.ticket.repository.TicketRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class CommentService {

    TicketRepository ticketRepository;

    TicketCommentRepository commentRepository;


    public CommentService(TicketRepository ticketRepository, TicketCommentRepository commentRepository) {
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
    }
}
