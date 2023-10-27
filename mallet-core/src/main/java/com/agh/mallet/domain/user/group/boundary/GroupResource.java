package com.agh.mallet.domain.user.group.boundary;

import com.agh.api.GroupContributionDeleteDTO;
import com.agh.api.GroupCreateDTO;
import com.agh.api.GroupDTO;
import com.agh.api.GroupUpdateAdminDTO;
import com.agh.api.GroupUpdateDTO;
import com.agh.mallet.domain.user.group.control.GroupService;
import io.swagger.v3.oas.annotations.Operation;
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
    public void create(@RequestBody GroupCreateDTO groupCreateDTO, Principal principal) {
        groupService.create(groupCreateDTO, principal.getName());
    }

    @Operation(
            summary = "Update group's contributions",
            description = "Update group's contributions. Only admin (owner) of the group and contributor with GROUP_READ_WRITE permission can perform this operation"

    )
    @PutMapping(path = "/contribution")
    public void updateContributions(@RequestBody GroupUpdateDTO groupUpdateDTO, Principal principal) {
        groupService.updateContributions(groupUpdateDTO, principal.getName());
    }

    @Operation(
            summary = "Delete group's contributions",
            description = "Delete group's contributions providing contributor ids. Only admin (owner) of the group can perform this operation"
    )
    @DeleteMapping(path = "contribution")
    public void deleteContributions(@RequestBody GroupContributionDeleteDTO contributionDeleteDTO, Principal principal) {
        groupService.deleteContributions(contributionDeleteDTO, principal.getName());
    }

    @Operation(
            summary = "Delete group",
            description = "Delete group providing groupId. Only admin (owner) of the group can perform this operation"
    )
    @DeleteMapping
    public void delete(@RequestBody long groupId, Principal principal) {
        groupService.delete(groupId, principal.getName());
    }

    @Operation(
            summary = "Update group admin",
            description = "Update group admin. Only admin (owner) of the group can perform this operation"
    )
    @PutMapping(path = "/admin")
    public void updateGroupAdmin(@RequestBody GroupUpdateAdminDTO groupUpdateAdminDTO, Principal principal) {
        groupService.updateAdmin(groupUpdateAdminDTO, principal.getName());
    }

}
