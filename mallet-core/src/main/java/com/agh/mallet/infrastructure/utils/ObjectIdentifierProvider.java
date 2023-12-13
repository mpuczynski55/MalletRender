package com.agh.mallet.infrastructure.utils;

import com.agh.mallet.domain.group.control.GroupRepository;
import com.agh.mallet.domain.set.control.repository.SetRepository;
import com.agh.mallet.domain.user.user.control.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class ObjectIdentifierProvider {

    private static final String HASHTAG = "#";
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final SetRepository setRepository;

    public ObjectIdentifierProvider(UserRepository userRepository, GroupRepository groupRepository, SetRepository setRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.setRepository = setRepository;
    }

    public String fromUsername(String username){
        long userCount = userRepository.countAllByUsername(username);

        String identifier = trimWhitespaces(username + HASHTAG + userCount);
        if (userRepository.existsByIdentifier(identifier)) {
            return trimWhitespaces(username + HASHTAG + userCount + 1);
        }
        return identifier;
    }

    public String fromGroupName(String groupName) {
        long userCount = groupRepository.countAllByName(groupName);

        String identifier = trimWhitespaces(groupName + HASHTAG + userCount);
        if (groupRepository.existsByIdentifier(identifier)) {
            return trimWhitespaces(groupName + HASHTAG + userCount + 1);
        }
        return identifier;
    }

    public String fromSetName(String setName) {
        long userCount = setRepository.countAllByName(setName);

        String identifier = trimWhitespaces(setName + HASHTAG + userCount);
        if (setRepository.existsByIdentifier(identifier)) {
            return trimWhitespaces(setName + HASHTAG + userCount + 1);
        }
        return identifier;
    }

    private String trimWhitespaces(String value) {
        return value.replaceAll("\\s", "");
    }
}
