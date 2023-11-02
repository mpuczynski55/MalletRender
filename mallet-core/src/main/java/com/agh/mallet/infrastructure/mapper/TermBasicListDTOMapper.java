package com.agh.mallet.infrastructure.mapper;

import com.agh.api.TermBasicDTO;
import com.agh.api.TermBasicListDTO;
import com.agh.mallet.domain.term.entity.TermJPAEntity;

import java.util.List;

public class TermBasicListDTOMapper {

    private TermBasicListDTOMapper() {}

    public static TermBasicListDTO from(List<TermJPAEntity> termEntities, String nextChunkUri){
        List<TermBasicDTO> terms = TermBasicDTOMapper.from(termEntities);

        return TermBasicListDTO.builder()
                .terms(terms)
                .nextChunkUri(nextChunkUri)
                .build();
    }

}
