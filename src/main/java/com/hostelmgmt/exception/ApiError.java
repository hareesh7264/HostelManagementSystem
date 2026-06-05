package com.hostelmgmt.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;
    private String errors;
    private List<String> message;

    public ApiError(HttpStatus status, String errors, List<String> message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.errors = errors;
        this.message = message;
    }
}

