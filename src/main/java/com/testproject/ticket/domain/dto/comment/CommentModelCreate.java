package com.testproject.ticket.domain.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentModelCreate {

    @NotEmpty(message = "Please enter your name")
    private String createdBy;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(as = LocalDateTime.class)
    private LocalDateTime date;

    @NotNull
    private Long ticketId;

    @NotEmpty(message = "Please provide a comment to the ticket")
    private String comment;

}
