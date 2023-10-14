package com.agh.mallet.infrastructure.mapper;

import com.agh.api.SetInformationDTO;
import com.agh.api.TermDTO;
import com.agh.mallet.domain.vocabularyset.entity.VocabularySetJPAEntity;

import java.util.Collection;
import java.util.List;

public class SetInformationDTOMapper {

    private SetInformationDTOMapper() {}

    public static List<SetInformationDTO> from(Collection<VocabularySetJPAEntity> entities,
                                               String nextChunkUri) {
        return entities.stream()
                .map(entity -> from(entity, nextChunkUri))
                .toList();
    }

    public static SetInformationDTO from(VocabularySetJPAEntity entity,
                                         String nextChunkUri) {
        List<TermDTO> terms = TermDTOMapper.from(entity.getTerms());

        return SetInformationDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .terms(terms)
                .nextChunkUri(nextChunkUri)
                .build();
    }

}
