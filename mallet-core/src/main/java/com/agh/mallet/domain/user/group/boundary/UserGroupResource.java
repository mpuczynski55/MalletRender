package com.agh.mallet.domain.user.group.boundary;

import com.agh.api.GroupBasicDTO;
import com.agh.mallet.domain.user.group.contorl.UserGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Tag(name = "User's Group Resource")
@RestController
@RequestMapping(path = "user/group", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserGroupResource {

    private final UserGroupService userGroupService;

    public UserGroupResource(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }

    @Operation(
            summary = "Get all user's groups",
            description = "Get all user's groups. If the result exceeds limit param then next chunk uri is returned"
    )
    @GetMapping
    @ResponseBody
    public ResponseEntity<GroupBasicDTO> get(@RequestParam(name = "startPosition", defaultValue = "0") int startPosition,
                                           @RequestParam(name = "limit", defaultValue = "10") int limit,
                                           Principal principal) {
        GroupBasicDTO groupBasicDTO = userGroupService.get(startPosition, limit, principal.getName());

        return new ResponseEntity<>(groupBasicDTO, HttpStatus.OK);
    }
}
