package com.agh.mallet.domain.group.boundary;

import com.agh.api.GroupContributionDeleteDTO;
import com.agh.api.GroupCreateDTO;
import com.agh.api.GroupDTO;
import com.agh.api.GroupSetCreateDTO;
import com.agh.api.GroupSetDTO;
import com.agh.api.GroupUpdateAdminDTO;
import com.agh.api.GroupUpdateDTO;
import com.agh.mallet.domain.group.control.GroupService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Tag(name = "Group Resource")
@RestController
@RequestMapping(path = "group", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupResource {

    private final GroupService groupService;

    public GroupResource(GroupService groupService) {
        this.groupService = groupService;
    }

    @Operation(
            summary = "Get group",
            description = "Get group providing id"
    )
    @GetMapping
    public ResponseEntity<GroupDTO> get(@RequestParam("id") long id) {
        GroupDTO groupDTO = groupService.get(id);

        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Create group"
    )
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody GroupCreateDTO groupCreateDTO, Principal principal) {
        Long groupId = groupService.create(groupCreateDTO, principal.getName());

        return new ResponseEntity<>(groupId, HttpStatus.OK);
    }

    @Operation(
            summary = "Update group's contributions",
            description = "Update group's contributions. Only admin (owner) of the group and contributor with GROUP_READ_WRITE permission can perform this operation"

    )
    @PutMapping(path = "/contribution")
    public ResponseEntity<Object> updateContributions(@RequestBody GroupUpdateDTO groupUpdateDTO, Principal principal) {
        groupService.updateContributions(groupUpdateDTO, principal.getName());

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Operation(
            summary = "Delete group's contributions",
            description = "Delete group's contributions providing contributor ids. Only admin (owner) of the group can perform this operation"
    )
    @PutMapping(path = "/contribution/delete")
    public ResponseEntity<Object> deleteContributions(@RequestBody GroupContributionDeleteDTO contributionDeleteDTO, Principal principal) {
        groupService.deleteContributions(contributionDeleteDTO, principal.getName());

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Operation(
            summary = "Delete group",
            description = "Delete group providing groupId. Only admin (owner) of the group can perform this operation"
    )
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestParam("id") long groupId, Principal principal) {
        groupService.delete(groupId, principal.getName());

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Operation(
            summary = "Update group admin",
            description = "Update group admin. Only admin (owner) of the group can perform this operation"
    )
    @PutMapping(path = "/admin")
    @Hidden
    public ResponseEntity<Object> updateGroupAdmin(@RequestBody GroupUpdateAdminDTO groupUpdateAdminDTO, Principal principal) {
        groupService.updateAdmin(groupUpdateAdminDTO, principal.getName());

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Operation(
            summary = "Add set to group",
            description = "Add set to group. Only user with set READ_WRITE permission can perform this operation"
    )
    @PutMapping(path = "/set")
    public ResponseEntity<Object> addSet(@RequestBody GroupSetDTO groupSetDTO, Principal principal) {
        groupService.addSet(groupSetDTO, principal.getName());

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Operation(
            summary = "Remove set from group",
            description = "Remove set from group. Only user with set READ_WRITE permission can perform this operation"
    )
    @DeleteMapping(path = "/set")
    public ResponseEntity<Object> removeSet(@RequestBody GroupSetDTO groupSetDTO, Principal principal) {
        groupService.removeSet(groupSetDTO, principal.getName());

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Operation(
            summary = "Create set group",
            description = "Create set group. Only user with set READ_WRITE permission can perform this operation"
    )
    @PostMapping(path = "/set")
    public ResponseEntity<Object> createSet(@RequestBody GroupSetCreateDTO groupSetCreateDTO, Principal principal) {
        groupService.createSet(groupSetCreateDTO, principal.getName());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
