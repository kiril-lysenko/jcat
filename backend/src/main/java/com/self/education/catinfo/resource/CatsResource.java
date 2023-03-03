package com.self.education.catinfo.resource;

import com.self.education.catinfo.api.Attribute;
import com.self.education.catinfo.api.CatRequest;
import com.self.education.catinfo.api.CatsResponse;
import com.self.education.catinfo.api.ErrorResponse;
import com.self.education.catinfo.service.CatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@AllArgsConstructor
@RequestMapping("/cat-info/v1")
public class CatsResource {

    private final CatsService catsService;

    @Operation(
            summary = "Get all cats and order by attributes",
            description = "Endpoint for getting all cats",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("/cats")
    public ResponseEntity<Page<CatsResponse>> findAllCats(
            @RequestParam(value = "offset", defaultValue = "0", required = false) final Integer offset,
            @RequestParam(value = "limit", defaultValue = "10", required = false) final Integer limit,
            @RequestParam(value = "order", defaultValue = "ASC", required = false) final Sort.Direction order,
            @RequestParam(value = "attribute", defaultValue = "ID", required = false) final Attribute attribute) {
        return ok(catsService.findAllCats(offset, limit, order, attribute));
    }

    @Operation(
            summary = "Add new cat",
            description = "Endpoint for added new cat",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/cat")
    public ResponseEntity<Void> createCat(@RequestBody @Valid final CatRequest request) {
        catsService.createCat(request);
        return new ResponseEntity<>(CREATED);
    }
}
