package com.agh.mallet.domain.term.boundary;

import com.agh.api.TermBasicListDTO;
import com.agh.mallet.domain.term.control.service.TermService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Term Resource")
@RestController
@RequestMapping(path = "term", produces = MediaType.APPLICATION_JSON_VALUE)
public class TermResource {

    private final TermService termService;

    public TermResource(TermService termService) {
        this.termService = termService;
    }

    @Operation(
            summary = "Get all terms by term name "
    )
    @GetMapping
    @Hidden
    public ResponseEntity<TermBasicListDTO> get(@RequestParam("term") String term,
                                                @RequestParam(name = "startPosition", defaultValue = "0") int startPosition,
                                                @RequestParam(name = "limit", defaultValue = "10") int limit) {
        TermBasicListDTO termBasicListDTO = termService.getByTerm(term, startPosition, limit);

        return new ResponseEntity<>(termBasicListDTO, HttpStatus.OK);
    }

}
