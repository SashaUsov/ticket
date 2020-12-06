package com.testproject.ticket.service;

import com.testproject.ticket.domain.Ticket;
import com.testproject.ticket.exception.DataIsNotCorrectException;
import com.testproject.ticket.exception.EntityNotFoundException;
import com.testproject.ticket.exception.PermissionToActionIsAbsentException;
import com.testproject.ticket.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;

import static com.testproject.ticket.helpUtil.HelpUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    public void testCreateShouldThrowDataIsNotCorrectException() {
        assertThrows(DataIsNotCorrectException.class,
                () -> ticketService.create(null));
    }

    @Test
    public void testCreateShouldSaveTicketAndReturnTicketModel() {
        var initModel = createTicketModelCreate();
        var ticketModel = createTicketModel(initModel);
        var ticketEntity = createTicketEntity(ticketModel);

        when(ticketRepository.save(any(Ticket.class)))
                .thenReturn(ticketEntity);
        var model = ticketService.create(initModel);

        verify(ticketRepository, times(1)).save(any(Ticket.class));
        assertEquals(ticketModel, model);

        Mockito.reset(ticketRepository);
    }

    @Test
    public void testDeleteByIdShouldThrowDataIsNotCorrectException() {
        assertThrows(DataIsNotCorrectException.class, () -> ticketService.deleteById(null));
    }

    @Test
    public void testDeleteByIdShouldThrowEntityNotFoundException() {
        var model = createTicketModelDelete();
        when(ticketRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        try {
            ticketService.deleteById(model);
        } catch (EntityNotFoundException ex) {
            assertEquals("Ticket not found", ex.getMessage());
        }
        verify(ticketRepository, times(1)).findById(anyLong());

        Mockito.reset(ticketRepository);
    }

    @Test
    public void testDeleteByIdShouldPermissionToActionIsAbsentException() {
        var model = createTicketModelDelete();
        when(ticketRepository.findById(anyLong()))
                .thenReturn(Optional.of(createTicketWithDifCreator()));
        try {
            ticketService.deleteById(model);
        } catch (PermissionToActionIsAbsentException ex) {
            assertEquals("You cannot delete a ticket that you are not the author of", ex.getMessage());
        }
        verify(ticketRepository, times(1)).findById(anyLong());

        Mockito.reset(ticketRepository);
    }

    @Test
    public void testDeleteByIdShouldDoNothing() {
        var model = createTicketModelDelete();
        when(ticketRepository.findById(anyLong()))
                .thenReturn(Optional.of(createTicketWithSameCreator()));
        doNothing().when(ticketRepository).deleteById(anyLong());
        ticketService.deleteById(model);
        verify(ticketRepository, times(1)).findById(anyLong());
        verify(ticketRepository, times(1)).deleteById(anyLong());

        Mockito.reset(ticketRepository);
    }

    @Test
    public void testGetAllShouldThrowEntityNotFoundException() {
        when(ticketRepository.findAll())
                .thenReturn(Collections.emptyList());
        try {
            ticketService.getAll();
        } catch (EntityNotFoundException ex) {
            assertEquals("Tickets not found.", ex.getMessage());
        }
        verify(ticketRepository, times(1)).findAll();

        Mockito.reset(ticketRepository);
    }

    @Test
    public void testGetAllShouldReturnListOfTicket() {
        var initModel = createTicketModelCreate();
        var ticketModel = createTicketModel(initModel);
        var ticketEntity = createTicketEntity(ticketModel);

        when(ticketRepository.findAll())
                .thenReturn(Collections.singletonList(ticketEntity));

        var ticketList = ticketService.getAll();
        assertEquals(Collections.singletonList(ticketModel), ticketList);

        verify(ticketRepository, times(1)).findAll();

        Mockito.reset(ticketRepository);
    }

    @Test
    public void testEditTicketMethodShouldThrowDataIsNotCorrectException() {
        assertThrows(DataIsNotCorrectException.class,
                () -> ticketService.editTicket(null));
    }

    @Test
    public void testEditTicketMethodShouldThrowEntityNotFoundException() {
        var model = createTicketModelEdit();
        when(ticketRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        try {
            ticketService.editTicket(model);
        } catch (EntityNotFoundException ex) {
            assertEquals("Ticket not found", ex.getMessage());
        }
        verify(ticketRepository, times(1)).findById(anyLong());

        Mockito.reset(ticketRepository);
    }

    @Test
    public void testEditTicketMethodShouldReturnEditedTicket() {
        var model = createTicketModelEdit();
        var ticketModel = createTicketModel(createTicketModelCreate());
        var ticketEntity = createTicketEntity(ticketModel);
        var expected = createTicketModelAfterEdit(ticketModel, model);
        when(ticketRepository.findById(anyLong()))
                .thenReturn(Optional.of(ticketEntity));
        when(ticketRepository.save(any(Ticket.class)))
                .thenReturn(editTicketModel(model, ticketEntity));

        var actual = ticketService.editTicket(model);

        verify(ticketRepository, times(1)).findById(anyLong());
        verify(ticketRepository, times(1)).save(any(Ticket.class));

        assertEquals(expected, actual);

        Mockito.reset(ticketRepository);
    }
}
