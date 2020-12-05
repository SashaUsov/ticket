package com.testproject.ticket.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class TicketModelCreate {

    @NotNull
    @NotEmpty(message = "Please enter your name")
    private String createdBy;

    @NotNull
    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime date;

    @NotNull
    @NotEmpty(message = "Please provide a description of the ticket")
    private String description;

    @NotNull
    @NotEmpty(message = "Please indicate current status")
    private String status;

}
