package com.testproject.ticket.domain.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.testproject.ticket.domain.TicketStatus;
import com.testproject.ticket.domain.dto.comment.CommentModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class TicketModel {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    private String createdBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    private String lastModifiedBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime date;

    private String description;

    private Set<CommentModel> comment = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketModel that = (TicketModel) o;
        return id.equals(that.id) &&
                createdDate.equals(that.createdDate) &&
                createdBy.equals(that.createdBy) &&
                Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
                Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
                Objects.equals(date, that.date) &&
                Objects.equals(description, that.description) &&
                Objects.equals(comment, that.comment) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdDate, createdBy, lastModifiedDate, lastModifiedBy, date, description, comment, status);
    }
}
