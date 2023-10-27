package com.agh.mallet.infrastructure.mapper;

import com.agh.api.ContributionDTO;
import com.agh.api.GroupDTO;
import com.agh.mallet.domain.user.group.entity.GroupJPAEntity;

import java.util.List;

public class GroupDTOMapper {

    private GroupDTOMapper() {}

    public static GroupDTO from(GroupJPAEntity groupJPAEntity){
        List<ContributionDTO> contributions = ContributionDTOMapper.from(groupJPAEntity.getContributions());

        return GroupDTO.builder()
                .id(groupJPAEntity.getId())
                .name(groupJPAEntity.getName())
                .contributions(contributions)
                .build();
    }

}
