package com.agh.mallet.infrastructure.mapper;

import com.agh.api.UserDTO;
import com.agh.api.SetBasicInformationDTO;
import com.agh.mallet.domain.set.entity.SetJPAEntity;

import java.util.Collection;
import java.util.List;

public class SetBasicInformationDTOMapper {

    private SetBasicInformationDTOMapper() {}

    public static List<SetBasicInformationDTO> from(Collection<SetJPAEntity> sets) {
        return sets.stream()
                .map(SetBasicInformationDTOMapper::from)
                .toList();
    }

    public static SetBasicInformationDTO from(SetJPAEntity set) {
        UserDTO creator = UserDTOMapper.from(set.getCreator());

        return SetBasicInformationDTO.builder()
                .id(set.getId())
                .name(set.getName())
                .creator(creator)
                .numberOfTerms(set.getTerms().size())
                .description(set.getDescription())
                .build();
    }

}
