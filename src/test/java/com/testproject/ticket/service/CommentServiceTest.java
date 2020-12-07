package com.testproject.ticket.service;

import com.testproject.ticket.domain.TicketComment;
import com.testproject.ticket.exception.DataIsNotCorrectException;
import com.testproject.ticket.exception.EntityNotFoundException;
import com.testproject.ticket.exception.PermissionToActionIsAbsentException;
import com.testproject.ticket.repository.TicketCommentRepository;
import com.testproject.ticket.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.testproject.ticket.utils.HelpUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class CommentServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketCommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    public void testCreateShouldThrowDataIsNotCorrectException() {
        assertThrows(DataIsNotCorrectException.class,
                () -> commentService.create(null));
    }

    @Test
    public void testCreateShouldThrowEntityNotFoundException() {
        var model = getCommentModelCreate();
        when(ticketRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        try {
            commentService.create(model);
        } catch (EntityNotFoundException ex) {
            assertEquals("Ticket not found", ex.getMessage());
        }

        verify(ticketRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testCreateShouldReturnCommentModel() {
        var model = getCommentModelCreate();
        var comment = createTicketComment(model, createTicketEntity(model));
        var expected = createCommentModel(comment);
        when(ticketRepository.findById(anyLong()))
                .thenReturn(Optional.of(createTicketEntity(model)));
        when(commentRepository.save(any(TicketComment.class)))
                .thenReturn(comment);

        var actual = commentService.create(model);

        assertEquals(expected, actual);

        verify(ticketRepository, times(1)).findById(anyLong());
        verify(commentRepository, times(1)).save(any(TicketComment.class));
    }

    @Test
    public void testEditCommentShouldThrowDataIsNotCorrectException() {
        assertThrows(DataIsNotCorrectException.class,
                () -> commentService.editComment(null));
    }

    @Test
    public void testEditCommentShouldThrowEntityNotFoundException() {
        var model = getCommentModelEdit();
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        try {
            commentService.editComment(model);
        } catch (EntityNotFoundException ex) {
            assertEquals("Comment not found", ex.getMessage());
        }
        verify(commentRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testEditCommentShouldReturnCommentModel() {
        var model = getCommentModelEdit();
        var ticketComment = createTicketComment();
        var expected = createCommentModel(ticketComment);
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(new TicketComment()));
        when(commentRepository.save(any(TicketComment.class)))
                .thenReturn(ticketComment);

        var actual = commentService.editComment(model);
        assertEquals(expected, actual);

        verify(commentRepository, times(1)).findById(anyLong());
        verify(commentRepository, times(1)).save(any(TicketComment.class));
    }

    @Test
    public void testDeleteCommentShouldThrowDataIsNotCorrectException() {
        assertThrows(DataIsNotCorrectException.class,
                () -> commentService.deleteComment(null));
    }

    @Test
    public void testDeleteCommentShouldThrowEntityNotFoundException() {
        var model = createCommentModeDelete();
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        try {
            commentService.deleteComment(model);
        } catch (EntityNotFoundException ex) {
            assertEquals("Comment not found", ex.getMessage());
        }
        verify(commentRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testDeleteCommentShouldThrowPermissionToActionIsAbsentException() {
        var model = createCommentModeDelete();
        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(createTicketComment()));
        try {
            commentService.deleteComment(model);
        } catch (PermissionToActionIsAbsentException ex) {
            assertEquals("You cannot delete a comment that you are not the author of", ex.getMessage());
        }
        verify(commentRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testDeleteByIdShouldDoNothing() {
        var model = createCommentModeDelete();
        var ticketComment = createTicketComment();
        ticketComment.setCreatedBy(model.getCreatedBy());

        when(commentRepository.findById(anyLong()))
                .thenReturn(Optional.of(ticketComment));
        doNothing().when(commentRepository).deleteById(anyLong());

        commentService.deleteComment(model);

        verify(commentRepository, times(1)).findById(anyLong());
        verify(commentRepository, times(1)).deleteById(anyLong());
    }
}
