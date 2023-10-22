package com.agh.mallet.infrastructure.mapper;

import com.agh.api.SetBasicInformationDTO;
import com.agh.api.SetBasicDTO;
import com.agh.mallet.domain.set.entity.SetJPAEntity;

import java.util.Collection;
import java.util.List;

public class SetBasicsDTOMapper {

    private SetBasicsDTOMapper() {}

    public static SetBasicDTO from(Collection<SetJPAEntity> userSetEntities, String nextChunkUri) {
        List<SetBasicInformationDTO> userSets = SetBasicInformationDTOMapper.from(userSetEntities);

        return SetBasicDTO.builder()
                .sets(userSets)
                .nextChunkUri(nextChunkUri)
                .build();
    }

}
