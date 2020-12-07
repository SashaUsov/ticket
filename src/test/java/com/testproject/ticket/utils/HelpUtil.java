package com.testproject.ticket.utils;

import com.testproject.ticket.domain.Ticket;
import com.testproject.ticket.domain.TicketComment;
import com.testproject.ticket.domain.TicketStatus;
import com.testproject.ticket.domain.dto.comment.CommentModeDelete;
import com.testproject.ticket.domain.dto.comment.CommentModel;
import com.testproject.ticket.domain.dto.comment.CommentModelCreate;
import com.testproject.ticket.domain.dto.comment.CommentModelEdit;
import com.testproject.ticket.domain.dto.ticket.TicketModeDelete;
import com.testproject.ticket.domain.dto.ticket.TicketModel;
import com.testproject.ticket.domain.dto.ticket.TicketModelCreate;
import com.testproject.ticket.domain.dto.ticket.TicketModelEdit;

import java.time.LocalDateTime;
import java.util.HashSet;

public class HelpUtil {

    public static Ticket createTicketEntity(TicketModel model) {
        var entity = new Ticket();
        entity.setId(model.getId());
        entity.setCreatedBy(model.getCreatedBy());
        entity.setCreatedDate(model.getCreatedDate());
        entity.setDate(model.getDate());
        entity.setDescription(model.getDescription());
        entity.setLastModifiedBy(model.getCreatedBy());
        entity.setLastModifiedDate(model.getLastModifiedDate());
        entity.setComment(new HashSet<>());
        entity.setStatus(model.getStatus());

        return entity;
    }

    public static TicketModel createTicketModel(TicketModelCreate initModel) {
        var model = new TicketModel();
        model.setId(1L);
        model.setCreatedBy(initModel.getCreatedBy());
        model.setCreatedDate(LocalDateTime.now());
        model.setDescription(initModel.getDescription());
        model.setDate(initModel.getDate());
        model.setStatus(TicketStatus.valueOf(initModel.getStatus().toUpperCase()));
        model.setLastModifiedBy("user");
        model.setLastModifiedDate(LocalDateTime.now());
        model.setComment(new HashSet<>());

        return model;
    }

    public static TicketModelCreate createTicketModelCreate() {
        var model = new TicketModelCreate();
        model.setCreatedBy("user");
        model.setDate(LocalDateTime.now());
        model.setDescription("for test");
        model.setStatus("OPEN");

        return model;
    }

    public static TicketModeDelete createTicketModelDelete() {
        var model = new TicketModeDelete();
        model.setCreatedBy("test");
        model.setId(1L);

        return model;
    }

    public static Ticket createTicketWithDifCreator() {
        var ticket = new Ticket();
        ticket.setCreatedBy("user");
        return ticket;
    }

    public static Ticket createTicketWithSameCreator() {
        var ticket = new Ticket();
        ticket.setCreatedBy("test");
        return ticket;
    }

    public static TicketModelEdit createTicketModelEdit() {
        var model = new TicketModelEdit();
        model.setCreatedBy("test");
        model.setId(1L);
        model.setDate(LocalDateTime.now());
        model.setStatus("OPEN");
        model.setDescription("description");

        return model;
    }

    public static Ticket editTicketModel(TicketModelEdit modelEdit, Ticket entity) {
        entity.setDescription(modelEdit.getDescription());
        entity.setStatus(TicketStatus.valueOf(modelEdit.getStatus().toUpperCase()));
        entity.setCreatedBy(modelEdit.getCreatedBy());
        entity.setId(modelEdit.getId());
        entity.setDate(modelEdit.getDate());
        return entity;
    }

    public static TicketModel createTicketModelAfterEdit(TicketModel model, TicketModelEdit modelEdit) {
        model.setDescription(modelEdit.getDescription());
        model.setStatus(TicketStatus.valueOf(modelEdit.getStatus().toUpperCase()));
        model.setCreatedBy(modelEdit.getCreatedBy());
        model.setId(modelEdit.getId());
        model.setDate(modelEdit.getDate());
        return model;
    }

    public static CommentModelCreate getCommentModelCreate() {
        var model = new CommentModelCreate();
        model.setComment("comment");
        model.setCreatedBy("user");
        model.setDate(LocalDateTime.now());
        model.setTicketId(1L);

        return model;
    }

    public static Ticket createTicketEntity(CommentModelCreate model) {
        var entity = new Ticket();
        entity.setId(1L);
        entity.setCreatedBy(model.getCreatedBy());
        entity.setCreatedDate(model.getDate());
        entity.setDate(model.getDate());
        entity.setDescription("description");
        entity.setLastModifiedBy(model.getCreatedBy());
        entity.setLastModifiedDate(model.getDate());
        entity.setComment(new HashSet<>());
        entity.setStatus(TicketStatus.OPEN);

        return entity;
    }

    public static TicketComment createTicketComment(CommentModelCreate model, Ticket ticket) {
        var entity = new TicketComment();
        entity.setId(1L);
        entity.setDate(model.getDate());
        entity.setTicket(ticket);
        entity.setLastModifiedBy(model.getCreatedBy());
        entity.setLastModifiedDate(model.getDate());
        entity.setCreatedDate(model.getDate());
        entity.setCreatedBy(model.getCreatedBy());
        entity.setComment(model.getComment());

        return entity;
    }

    public static CommentModel createCommentModel(TicketComment comment) {
        var model = new CommentModel();
        model.setId(comment.getId());
        model.setLastModifiedDate(comment.getLastModifiedDate());
        model.setLastModifiedBy(comment.getLastModifiedBy());
        model.setDate(comment.getDate());
        model.setCreatedDate(comment.getCreatedDate());
        model.setCreatedBy(comment.getCreatedBy());
        model.setComment(comment.getComment());

        return model;
    }

    public static CommentModelEdit getCommentModelEdit() {
        var model = new CommentModelEdit();
        model.setId(1L);
        model.setComment("new comment");
        model.setDate(LocalDateTime.now());
        model.setCreatedBy("test");

        return model;
    }

    public static TicketComment createTicketComment() {
        var entity = new TicketComment();
        entity.setId(1L);
        entity.setCreatedBy("user");
        entity.setCreatedDate(LocalDateTime.now());
        entity.setDate(LocalDateTime.now());
        entity.setComment("comment");
        entity.setLastModifiedBy("user");
        entity.setLastModifiedDate(LocalDateTime.now());
        entity.setTicket(new Ticket());

        return entity;
    }

    public static CommentModeDelete createCommentModeDelete() {
        var model = new CommentModeDelete();
        model.setCreatedBy("test");
        model.setId(1L);

        return model;
    }
}
