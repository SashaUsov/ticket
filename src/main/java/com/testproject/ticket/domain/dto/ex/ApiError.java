package com.testproject.ticket.domain.dto.ex;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class ApiError {
    private final String reason;
    private final Set<ErrorInfo> errors;
}
