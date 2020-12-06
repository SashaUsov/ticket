package com.testproject.ticket.service;

import com.testproject.ticket.domain.dto.CommentModeDelete;
import com.testproject.ticket.domain.dto.CommentModel;
import com.testproject.ticket.domain.dto.CommentModelCreate;
import com.testproject.ticket.domain.dto.CommentModelEdit;
import com.testproject.ticket.exception.DataIsNotCorrectException;
import com.testproject.ticket.exception.EntityNotFoundException;
import com.testproject.ticket.exception.PermissionToActionIsAbsentException;
import com.testproject.ticket.repository.TicketCommentRepository;
import com.testproject.ticket.repository.TicketRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            var ticketEntity = ticket.orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
            var entityFromModel = createNewCommentEntityFromModel(modelCreate);
            entityFromModel.setTicket(ticketEntity);
            var ticketComment = commentRepository.save(entityFromModel);

            return prepareCommentEntityToTransferModel(ticketComment);
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }

    public CommentModel editComment(CommentModelEdit modelEdit) {
        if (modelEdit != null) {
            var comment = commentRepository.findById(modelEdit.getId());
            var entity = comment.orElseThrow(() -> new EntityNotFoundException("Comment not found"));
            var updateModel = createUpdateCommentModel(modelEdit, entity);
            return prepareCommentEntityToTransferModel(commentRepository.save(updateModel));
        }
        throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }

    @Transactional
    public void deleteComment(CommentModeDelete deleteModel) {
        if (deleteModel != null) {
            var comment = commentRepository.findById(deleteModel.getId());
            var entity = comment.orElseThrow(() -> new EntityNotFoundException("Comment not found"));
            if (entity.getCreatedBy().equals(deleteModel.getCreatedBy())) {
                commentRepository.deleteById(deleteModel.getId());
            } else throw new PermissionToActionIsAbsentException("You cannot delete a comment that you are not the author of");
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }
}
