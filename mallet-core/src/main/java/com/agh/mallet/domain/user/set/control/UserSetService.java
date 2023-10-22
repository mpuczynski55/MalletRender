package com.agh.mallet.domain.user.set.control;

import com.agh.api.SetBasicDTO;
import com.agh.mallet.domain.set.control.repository.SetRepository;
import com.agh.mallet.domain.set.control.service.SetService;
import com.agh.mallet.domain.set.entity.SetJPAEntity;
import com.agh.mallet.domain.user.user.control.service.UserService;
import com.agh.mallet.domain.user.user.entity.UserJPAEntity;
import com.agh.mallet.infrastructure.mapper.SetBasicsDTOMapper;
import com.agh.mallet.infrastructure.utils.NextChunkRebuilder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserSetService {

    private final UserService userService;
    private final SetService setService;
    private final SetRepository setRepository;
    private final NextChunkRebuilder nextChunkRebuilder;


    public UserSetService(UserService userService, SetService setService, SetRepository setRepository, NextChunkRebuilder nextChunkRebuilder) {
        this.userService = userService;
        this.setService = setService;
        this.setRepository = setRepository;
        this.nextChunkRebuilder = nextChunkRebuilder;
    }

    public SetBasicDTO get(int startPosition,
                           int limit,
                           String userEmail) {
        UserJPAEntity userEntity = userService.getByEmail(userEmail);


        Set<SetJPAEntity> userSets = userEntity.getUserSets();
        String nextChunkUri = nextChunkRebuilder.rebuild(userSets, startPosition, limit);

        return SetBasicsDTOMapper.from(userSets, nextChunkUri);
    }

    public void add(String userEmail, long setId) {
        UserJPAEntity userEntity = userService.getByEmail(userEmail);

        SetJPAEntity setEntity = setService.getById(setId);

        add(setEntity, userEntity);
    }

    private void add(SetJPAEntity set, UserJPAEntity user) {
        SetJPAEntity clonedSet = new SetJPAEntity(set);

        user.addUserSet(clonedSet);

        userService.save(user);
    }


    public void remove(long setId, String userEmail) {
        UserJPAEntity userEntity = userService.getByEmail(userEmail);

        SetJPAEntity setEntity = setService.getById(setId);

        remove(setEntity, userEntity);
    }

    private void remove(SetJPAEntity set,
                       UserJPAEntity user) {
        user.removeUserSet(set);

        userService.save(user);
    }

}
