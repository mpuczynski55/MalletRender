package com.agh.mallet.domain.user.group.boundary;

import com.agh.api.ContributionDTO;
import com.agh.api.GroupDTO;
import com.agh.mallet.domain.user.group.control.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "group", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupResource {

    private final GroupService groupService;


    public GroupResource(GroupService groupService) {
        this.groupService = groupService;
    }

    @Operation(
            summary = "Create group"
    )
    @PostMapping
    public void create(@RequestBody GroupDTO groupDTO, Principal principal) {
        groupService.create(groupDTO, principal.getName());
    }

    @Operation(
            summary = "Update group's contributions"
    )
    @PutMapping(name = "/contribution/add")
    public void addContributions(@RequestBody GroupDTO groupDTO, Principal principal) {
        groupService.addContributions(groupDTO, principal.getName());
    }

    @Operation(
            summary = "Create group"
    )
    @DeleteMapping
    public void delete(@RequestBody GroupDTO groupDTO, Principal principal) {
        groupService.delete(groupDTO, principal.getName());
    }

}
