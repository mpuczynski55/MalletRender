package com.agh.mallet.infrastructure.mapper;

import com.agh.api.TermDTO;
import com.agh.mallet.domain.vocabulary.entity.TermJPAEntity;

import java.util.Collection;
import java.util.List;

public class TermDTOMapper {

    private TermDTOMapper() {}

    public static List<TermDTO> from(Collection<TermJPAEntity> entities){
        return entities.stream()
                .map(TermDTOMapper::from)
                .toList();
    }

    public static TermDTO from(TermJPAEntity entity){
        return TermDTO.builder()
                .id(entity.getId())
                .term(entity.getTerm())
                .definition(entity.getDefinition())
                .language(entity.getLanguage().name())
                .grammaticalCategory(entity.getGrammaticalCategory())
                .topic(entity.getTopic())
                .translations(from(entity.getTranslations()))
                .build();
    }
}
