package com.testproject.ticket.controller;

import com.testproject.ticket.domain.dto.ticket.TicketModeDelete;
import com.testproject.ticket.domain.dto.ticket.TicketModel;
import com.testproject.ticket.domain.dto.ticket.TicketModelCreate;
import com.testproject.ticket.domain.dto.ticket.TicketModelEdit;
import com.testproject.ticket.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TicketModel createTicket(@RequestBody @Valid TicketModelCreate modelCreate) {
        return ticketService.create(modelCreate);
    }

    @GetMapping("all")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TicketModel> findAll() {
        return ticketService.getAll();
    }

    @DeleteMapping("delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteTicket(@RequestBody @Valid TicketModeDelete deleteModel) {
        ticketService.deleteById(deleteModel);
    }

    @PutMapping("edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TicketModel editTicket(@RequestBody @Valid TicketModelEdit modelEdit) {
        return ticketService.editTicket(modelEdit);
    }
}
