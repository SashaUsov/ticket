package com.testproject.ticket.helpUtil;

import com.testproject.ticket.domain.Ticket;
import com.testproject.ticket.domain.TicketStatus;
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
}
