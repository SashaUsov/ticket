package com.testproject.ticket.domain.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class TicketModelEdit {

    @NotNull
    private Long id;

    @NotEmpty(message = "Please enter your name")
    private String createdBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(as = LocalDateTime.class)
    private LocalDateTime date;

    private String description;

    private String status;
}
