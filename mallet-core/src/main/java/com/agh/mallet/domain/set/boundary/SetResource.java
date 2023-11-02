package com.agh.mallet.domain.set.boundary;

import com.agh.api.SetBasicDTO;
import com.agh.api.SetDetailDTO;
import com.agh.mallet.domain.set.control.service.SetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Tag(name = "Set Resource")
@RestController
@RequestMapping(path = "set", produces = MediaType.APPLICATION_JSON_VALUE)
public class SetResource {

    private static final String BASIC_PATH = "basic";

    private final SetService setService;

    public SetResource(SetService setService) {
        this.setService = setService;
    }

    @Operation(
            summary = "Get sets basic information",
            description = "Get sets basic information providing primary language." +
                    "If ids parameter is provided and result exceeds limit param then next chunk uri is returned"
    )
    @GetMapping(value = BASIC_PATH)
    public ResponseEntity<SetBasicDTO> getBasics(@Max(10) @RequestParam(name = "ids", required = false) Set<Long> ids,
                                                 @RequestParam(name = "startPosition", defaultValue = "0") int startPosition,
                                                 @RequestParam(name = "limit", defaultValue = "10") int limit,
                                                 @RequestParam(name = "language", required = false, defaultValue = "EN") String primaryLanguage) {
        SetBasicDTO setBasicDTO = setService.getBasics(ids, startPosition, limit, primaryLanguage);

        return new ResponseEntity<>(setBasicDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Get set detail information",
            description = "Get set detail information providing primary language and set id. If the terms result exceeds termLimit param then next chunk uri is returned"
    )
    @GetMapping
    public ResponseEntity<SetDetailDTO> get(@RequestParam(name = "id") long id,
                                            @RequestParam(name = "termStartPosition", defaultValue = "0") int termStartPosition,
                                            @RequestParam(name = "termLimit", defaultValue = "20") int termLimit,
                                            @RequestParam(name = "language") String primaryLanguage) {

        SetDetailDTO setDTO = setService.get(id, termStartPosition, termLimit, primaryLanguage);

        return new ResponseEntity<>(setDTO, HttpStatus.OK);
    }

}
