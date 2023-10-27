package com.agh.mallet.domain.user.group.control;

import com.agh.mallet.domain.user.group.entity.ContributionJPAEntity;
import com.agh.mallet.domain.user.group.entity.GroupJPAEntity;
import com.agh.mallet.domain.user.group.entity.PermissionType;
import com.agh.mallet.domain.user.user.entity.UserJPAEntity;
import com.agh.mallet.infrastructure.exception.MalletForbiddenException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.function.Supplier;

@Service
public class UserContributionValidator {

    private static final String USER_NOT_FOUND_IN_CONTRIBUTORS_EXCEPTION_MSG = "User was not found in contributions of group with id {0}";


    public static void validateUserGroupReadWritePermission(String userEmail,
                                                      GroupJPAEntity groupEntity,
                                                      String errorMessage) {
        UserJPAEntity groupAdmin = groupEntity.getAdmin();
        ContributionJPAEntity requesterContribution = groupEntity.getContributions().stream()
                .filter(contribution -> contribution.getContributor().getEmail().equals(userEmail))
                .findAny()
                .orElseThrow(supplyUserNotInContributorsOfGroupException(groupEntity.getId()));

        if (!groupAdmin.getEmail().equals(userEmail) || !PermissionType.READ_WRITE.equals(requesterContribution.getGroupPermissionType())) {
            throw new MalletForbiddenException(errorMessage);
        }
    }


    private static Supplier<MalletForbiddenException> supplyUserNotInContributorsOfGroupException(Long groupId) {
        return () -> {
            String message = MessageFormat.format(USER_NOT_FOUND_IN_CONTRIBUTORS_EXCEPTION_MSG, groupId);
            return new MalletForbiddenException(message);
        };
    }

    public static void validateAdminRole(String userEmail,
                                   UserJPAEntity groupAdmin,
                                   String errorMessage) {
        if (!groupAdmin.getEmail().equals(userEmail)) {
            throw new MalletForbiddenException(errorMessage);
        }
    }

}
