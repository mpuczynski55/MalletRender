package com.agh.mallet.domain.user.group.contorl;

import com.agh.api.GroupBasicDTO;
import com.agh.mallet.domain.group.entity.GroupJPAEntity;
import com.agh.mallet.domain.user.user.control.repository.UserRepository;
import com.agh.mallet.infrastructure.mapper.GroupBasicDTOMapper;
import com.agh.mallet.infrastructure.utils.NextChunkRebuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupService {

    private final UserRepository userRepository;
    private final NextChunkRebuilder nextChunkRebuilder;

    public UserGroupService(UserRepository userRepository, NextChunkRebuilder nextChunkRebuilder) {
        this.userRepository = userRepository;
        this.nextChunkRebuilder = nextChunkRebuilder;
    }

    public GroupBasicDTO get(int startPosition,
                             int limit,
                             String userEmail) {

        PageRequest pageRequest = PageRequest.of(startPosition, startPosition + limit);
        List<GroupJPAEntity> userGroups = userRepository.findAllGroupsByUserEmail(userEmail, pageRequest)
                .getContent();

        String nextChunkUri = nextChunkRebuilder.rebuild(userGroups, startPosition, limit);

        return GroupBasicDTOMapper.from(userGroups, nextChunkUri);
    }
}
