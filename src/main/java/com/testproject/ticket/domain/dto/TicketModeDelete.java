package com.testproject.ticket.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TicketModeDelete {

    @NotNull
    private Long id;

    @NotEmpty(message = "Please enter your name")
    private String createdBy;
}
