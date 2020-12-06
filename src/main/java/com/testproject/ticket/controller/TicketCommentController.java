package com.testproject.ticket.controller;

import com.testproject.ticket.domain.dto.CommentModel;
import com.testproject.ticket.domain.dto.CommentModelCreate;
import com.testproject.ticket.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("comment")
public class TicketCommentController {

    private final CommentService commentService;

    public TicketCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CommentModel createTicket(@RequestBody @Valid CommentModelCreate modelCreate) {
        return commentService.create(modelCreate);
    }
}
