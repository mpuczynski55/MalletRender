package com.agh.mallet.infrastructure.utils;

import com.agh.mallet.domain.group.control.GroupRepository;
import com.agh.mallet.domain.user.user.control.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class ObjectIdentifierProvider {

    private static final String HASHTAG = "#";
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public ObjectIdentifierProvider(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public String fromUsername(String username){
        long userCount = userRepository.countAllByUsername(username);

        return username + HASHTAG + userCount;
    }

    public String fromGroupName(String groupName){
        long userCount = groupRepository.countAllByName(groupName);

        return groupName + HASHTAG + userCount;
    }

}
