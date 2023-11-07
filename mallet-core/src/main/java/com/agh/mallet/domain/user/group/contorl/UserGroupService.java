package com.agh.mallet.domain.user.group.contorl;

import com.agh.api.GroupBasicDTO;
import com.agh.mallet.domain.group.entity.GroupJPAEntity;
import com.agh.mallet.domain.user.user.control.service.UserService;
import com.agh.mallet.domain.user.user.entity.UserJPAEntity;
import com.agh.mallet.infrastructure.mapper.GroupBasicDTOMapper;
import com.agh.mallet.infrastructure.utils.NextChunkRebuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserGroupService {

    private final UserService userService;
    private final NextChunkRebuilder nextChunkRebuilder;

    public UserGroupService(UserService userService, NextChunkRebuilder nextChunkRebuilder) {
        this.userService = userService;
        this.nextChunkRebuilder = nextChunkRebuilder;
    }

    public GroupBasicDTO get(int startPosition,
                             int limit,
                             String userEmail) {
        UserJPAEntity userEntity = userService.getByEmail(userEmail);

        List<GroupJPAEntity> userGroups = userEntity.getUserGroups();
        String nextChunkUri = nextChunkRebuilder.rebuild(userGroups, startPosition, limit);

        List<GroupJPAEntity> limitedGroups = userGroups.stream()
                .limit(limit)
                .collect(Collectors.toList());

        return GroupBasicDTOMapper.from(limitedGroups, nextChunkUri);
    }
}
