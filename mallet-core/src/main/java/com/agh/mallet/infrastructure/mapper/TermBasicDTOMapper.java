package com.agh.mallet.infrastructure.mapper;

import com.agh.api.TermBasicDTO;
import com.agh.mallet.domain.term.entity.TermJPAEntity;
import com.agh.mallet.infrastructure.utils.LanguageConverter;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class TermBasicDTOMapper {

    private TermBasicDTOMapper() {
    }

    public static List<TermBasicDTO> from(Collection<TermJPAEntity> termEntities) {
        return termEntities.stream()
                .map(TermBasicDTOMapper::from)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private static Optional<TermBasicDTO> from(TermJPAEntity termEntity) {
        return Optional.ofNullable(termEntity.getTranslation())
                .flatMap(TermBasicDTOMapper::from)
                .map(translation -> TermBasicDTOMapper.from(termEntity, translation));
    }

    private static TermBasicDTO from(TermJPAEntity termEntity, TermBasicDTO translation) {
        return TermBasicDTO.builder()
                .id(termEntity.getId())
                .term(termEntity.getTerm())
                .definition(termEntity.getDefinition())
                .language(LanguageConverter.from(termEntity.getLanguage()))
                .translation(translation)
                .build();
    }

}
