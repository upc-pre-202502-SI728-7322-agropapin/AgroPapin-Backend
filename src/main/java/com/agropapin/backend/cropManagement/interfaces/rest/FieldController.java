package com.agropapin.backend.cropManagement.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/field", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Fields", description = "Field Management Endpoints")
public class FieldController {
}
