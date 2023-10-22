package com.agh.mallet.domain.set.boundary;

import com.agh.api.SetBasicDTO;
import com.agh.api.SetBasicInformationDTO;
import com.agh.api.SetInformationDTO;
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

import java.util.List;
import java.util.Set;

@Tag(name = "Set Resource")
@RestController
@RequestMapping(path = "set", produces = MediaType.APPLICATION_JSON_VALUE)
public class SetResource {

    private static final String BASIC_PATH = "basic";
    private static final String ID_PATH = "id";

    private final SetService setService;

    public SetResource(SetService setService) {
        this.setService = setService;
    }

    @Operation(
            summary = "Get sets basic information",
            description = "Get sets basic information providing primary language. If the result exceeds limit param then next chunk uri is returned"
    )
    @GetMapping(value = BASIC_PATH)
    public ResponseEntity<SetBasicDTO> getBasics(@RequestParam(name = "startPosition", defaultValue = "0") int startPosition,
                                                 @RequestParam(name = "limit", defaultValue = "10") int limit,
                                                 @RequestParam(name = "language") String primaryLanguage) {

        SetBasicDTO setBasicDTO = setService.getBasics(startPosition, limit, primaryLanguage);

        return new ResponseEntity<>(setBasicDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Get set detail information",
            description = "Get set detail information providing primary language and set id. If the terms result exceeds termLimit param then next chunk uri is returned"
    )
    @GetMapping
    public ResponseEntity<SetInformationDTO> get(@RequestParam(name = "setId") long setId,
                                                 @RequestParam(name = "termStartPosition", defaultValue = "0") int termStartPosition,
                                                 @RequestParam(name = "termLimit", defaultValue = "20") int termLimit,
                                                 @RequestParam(name = "language") String primaryLanguage) {

        SetInformationDTO setDTO = setService.get(setId, termStartPosition, termLimit, primaryLanguage);

        return new ResponseEntity<>(setDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Get sets basic information",
            description = "Get sets basic information providing setIds"
    )
    @GetMapping(BASIC_PATH + ID_PATH)
    public ResponseEntity<List<SetBasicInformationDTO>> getBasicsByIds(@RequestParam(name = "ids") @Max(10) Set<Long> ids) {
        List<SetBasicInformationDTO> setBasicInformationDTOS = setService.getBasics(ids);

        return new ResponseEntity<>(setBasicInformationDTOS, HttpStatus.OK);
    }


}
