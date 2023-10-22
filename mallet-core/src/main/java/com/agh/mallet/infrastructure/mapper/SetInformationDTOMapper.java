package com.agh.mallet.infrastructure.mapper;

import com.agh.api.SetInformationDTO;
import com.agh.api.TermDTO;
import com.agh.mallet.domain.vocabulary.entity.TermJPAEntity;
import com.agh.mallet.domain.set.entity.SetJPAEntity;

import java.util.Collection;
import java.util.List;

public class SetInformationDTOMapper {

    private SetInformationDTOMapper() {}

    public static SetInformationDTO from(long setId,
                                         List<TermJPAEntity> termJPAEntities,
                                         String nextChunkUri) {
        List<TermDTO> terms = TermDTOMapper.from(termJPAEntities);

        return SetInformationDTO.builder()
                .id(setId)
                .terms(terms)
                .nextChunkUri(nextChunkUri)
                .build();
    }

    public static List<SetInformationDTO> from(Collection<SetJPAEntity> sets,
                                               String nextChunkUri) {
        return sets.stream()
                .map(entity -> from(entity, nextChunkUri))
                .toList();
    }

    public static SetInformationDTO from(SetJPAEntity set,
                                         String nextChunkUri) {
        List<TermDTO> terms = TermDTOMapper.from(set.getTerms());

        return SetInformationDTO.builder()
                .id(set.getId())
                .name(set.getName())
                .description(set.getDescription())
                .terms(terms)
                .nextChunkUri(nextChunkUri)
                .build();
    }

}
