package com.agh.mallet.domain.user.set.boundary;

import com.agh.api.SetBasicDTO;
import com.agh.mallet.domain.user.set.control.UserSetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Tag(name = "User Resource")
@RestController
@RequestMapping(path = "user/set", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserSetResource {

    private final UserSetService userSetService;

    public UserSetResource(UserSetService userSetService) {
        this.userSetService = userSetService;
    }

    @Operation(
            summary = "Get all user's sets",
            description = "Get all user's sets. If the terms result exceeds limit param then next chunk uri is returned"
    )
    @GetMapping
    @ResponseBody
    public ResponseEntity<SetBasicDTO> get(@RequestParam(name = "startPosition", defaultValue = "0") int startPosition,
                                           @RequestParam(name = "limit", defaultValue = "10") int limit,
                                           Principal principal) {
        SetBasicDTO userSets = userSetService.get(startPosition, limit, principal.getName());

        return new ResponseEntity<>(userSets, HttpStatus.OK);
    }

    @Operation(
            summary = "Add set to user's set collection",
            description = "Add set to user's set collection providing setId"
    )
    @PutMapping
    public ResponseEntity<Object> add(Principal principal, @RequestParam long setId) {
        userSetService.add(principal.getName(), setId);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(
            summary = "Remove set from user's set collection",
            description = "Remove set from user's set collection providing setId"
    )
    @DeleteMapping
    public ResponseEntity<Object> remove(Principal principal, @RequestParam long setId) {
        userSetService.remove(setId, principal.getName());

        return new ResponseEntity<>( HttpStatus.OK);
    }

}
