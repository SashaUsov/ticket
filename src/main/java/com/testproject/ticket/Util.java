package com.testproject.ticket;

import com.testproject.ticket.domain.Ticket;
import com.testproject.ticket.domain.TicketComment;
import com.testproject.ticket.domain.TicketStatus;
import com.testproject.ticket.domain.dto.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Util {

    public static Ticket createNewTicketEntityFromModel(TicketModelCreate modelCreate) {
        var entity = new Ticket();
        entity.setCreatedBy(modelCreate.getCreatedBy());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setDate(modelCreate.getDate());
        entity.setDescription(modelCreate.getDescription());
        entity.setLastModifiedBy(modelCreate.getCreatedBy());
        entity.setLastModifiedDate(LocalDateTime.now());
        entity.setComment(new HashSet<>());
        entity.setStatus(TicketStatus.valueOf(modelCreate.getStatus().toUpperCase()));

        return entity;
    }

    public static TicketModel prepareTicketEntityToTransferModel(Ticket entity) {
        var model = new TicketModel();
        model.setId(entity.getId());
        model.setCreatedBy(entity.getCreatedBy());
        model.setCreatedDate(entity.getCreatedDate());
        model.setDescription(entity.getDescription());
        model.setDate(entity.getDate());
        model.setStatus(entity.getStatus());
        model.setLastModifiedBy(entity.getLastModifiedBy());
        model.setLastModifiedDate(entity.getLastModifiedDate());
        if (entity.getComment() == null || entity.getComment().isEmpty()) {
            model.setComment(new HashSet<>());
        } else {
            var modelSet = entity.getComment().stream()
                    .map(Util::prepareCommentEntityToTransferModel)
                    .collect(Collectors.toSet());
            model.setComment(modelSet);
        }

        return model;
    }

    public static CommentModel prepareCommentEntityToTransferModel(TicketComment entity) {
        var model = new CommentModel();
        model.setId(entity.getId());
        model.setComment(entity.getComment());
        model.setCreatedBy(entity.getCreatedBy());
        model.setCreatedDate(entity.getCreatedDate());
        model.setDate(entity.getDate());
        model.setLastModifiedBy(entity.getLastModifiedBy());
        model.setLastModifiedDate(entity.getLastModifiedDate());

        return model;
    }

    public static Ticket createUpdateTicketModel(TicketModelEdit modelEdit, Ticket entity) {
        var updateModel = new Ticket();
        updateModel.setId(entity.getId());
        updateModel.setLastModifiedBy(modelEdit.getCreatedBy());
        updateModel.setLastModifiedDate(LocalDateTime.now());
        updateModel.setCreatedBy(entity.getCreatedBy());
        updateModel.setCreatedDate(entity.getCreatedDate());
        var ticketStatus = modelEdit.getStatus() != null && !modelEdit.getStatus().isEmpty() ?
                TicketStatus.valueOf(modelEdit.getStatus().toUpperCase()) : entity.getStatus();
        updateModel.setStatus(ticketStatus);
        var description = modelEdit.getDescription() != null && !modelEdit.getDescription().isEmpty() ?
                modelEdit.getDescription() : entity.getDescription();
        updateModel.setDescription(description);
        var date = modelEdit.getDate() != null ?
                modelEdit.getDate() : entity.getDate();
        updateModel.setDate(date);
        return updateModel;
    }

    public static TicketComment createNewCommentEntityFromModel(CommentModelCreate model) {
        var entity = new TicketComment();
        entity.setCreatedBy(model.getCreatedBy());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setComment(model.getComment());
        entity.setDate(LocalDateTime.now());
        entity.setLastModifiedBy(model.getCreatedBy());
        entity.setLastModifiedDate(LocalDateTime.now());

        return entity;
    }
}
