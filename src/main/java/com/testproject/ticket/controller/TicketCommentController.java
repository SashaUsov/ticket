package com.testproject.ticket.controller;

import com.testproject.ticket.domain.dto.comment.CommentModeDelete;
import com.testproject.ticket.domain.dto.comment.CommentModel;
import com.testproject.ticket.domain.dto.comment.CommentModelCreate;
import com.testproject.ticket.domain.dto.comment.CommentModelEdit;
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

    @PutMapping("edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CommentModel editTicket(@RequestBody @Valid CommentModelEdit modelEdit) {
        return commentService.editComment(modelEdit);
    }

    @DeleteMapping("delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteTicket(@RequestBody @Valid CommentModeDelete modelDelete) {
        commentService.deleteComment(modelDelete);
    }
}
