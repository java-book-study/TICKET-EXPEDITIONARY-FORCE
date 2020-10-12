package com.ticket.captain.base;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.validation.Errors;

public class ErrorsResource extends RepresentationModel {
    @JsonUnwrapped
    private Errors errors;

    public ErrorsResource(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors(){
        return errors;
    }
}
