package com.agh.mallet.infrastructure.mapper;

import com.agh.api.TermDetailDTO;
import com.agh.mallet.domain.term.entity.TermJPAEntity;
import com.agh.mallet.infrastructure.utils.LanguageConverter;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class TermDTOMapper {

    private TermDTOMapper() {
    }

    public static List<TermDetailDTO> from(Collection<TermJPAEntity> entities) {
        return entities.stream()
                .map(TermDTOMapper::from)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public static Optional<TermDetailDTO> from(TermJPAEntity entity) {
        return Optional.ofNullable(entity.getTranslation())
                .flatMap(TermDTOMapper::from)
                .map(translation -> from(entity, translation));

    }

    private static TermDetailDTO from(TermJPAEntity entity, TermDetailDTO translation) {
        return TermDetailDTO.builder()
                .id(entity.getId())
                .term(entity.getTerm())
                .definition(entity.getDefinition())
                .language(LanguageConverter.from(entity.getLanguage()))
                .translation(translation)
                .build();
    }
}

