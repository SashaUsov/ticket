package com.testproject.ticket.controller;

import com.testproject.ticket.domain.dto.TicketModel;
import com.testproject.ticket.domain.dto.TicketModelCreate;
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
    public TicketModel createTicket(@Valid @RequestBody TicketModelCreate modelCreate) {
        return ticketService.create(modelCreate);
    }

    @GetMapping("all")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TicketModel> findAll() {
        return ticketService.getAll();
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteTicket(@PathVariable("id") Long id) {
        ticketService.deleteById(id);
    }
}
