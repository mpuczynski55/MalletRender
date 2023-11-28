package com.agh.mallet.domain.user.term.boundary;

import com.agh.mallet.domain.user.term.control.UserTermService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Set;

@Tag(name = "User's Term Resource")
@RestController
@RequestMapping(path = "user/term", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserTermResource {

    private static final String ADD_PATH = "/add";

    private final UserTermService userTermService;

    public UserTermResource(UserTermService userTermService) {
        this.userTermService = userTermService;
    }

    @Operation(
            summary = "Add user known terms",
            description = "Add terms to user known terms providing ids of the terms"
    )
    @PutMapping(ADD_PATH)
    @ResponseBody
    @Hidden
    public void addUserKnownTerms(@RequestBody Set<Long> userKnownTermIds, Principal principal) {
        userTermService.updateKnown(userKnownTermIds,principal.getName());
    }

}
