package com.agh.mallet.domain.vocabularyset.control.service;

import com.agh.api.SetInformationDTO;
import com.agh.mallet.domain.vocabulary.entity.Language;
import com.agh.mallet.domain.vocabularyset.control.repository.VocabularySetRepository;
import com.agh.mallet.domain.vocabularyset.control.repository.VocabularySetSpecification;
import com.agh.mallet.domain.vocabularyset.entity.VocabularySetJPAEntity;
import com.agh.mallet.infrastructure.mapper.SetInformationDTOMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VocabularySetService {

    private final VocabularySetRepository vocabularySetRepository;

    public VocabularySetService(VocabularySetRepository vocabularySetRepository) {
        this.vocabularySetRepository = vocabularySetRepository;
    }

    public List<SetInformationDTO> findByTermsLanguage(int startPosition,
                                                       int limit,
                                                       String primaryLanguage,
                                                       String nextChunkUri) {
        if (limit > 25) {
            limit = 20;
        }

        PageRequest pageRequest = PageRequest.of(startPosition, startPosition + limit);

        Language language = Language.from(primaryLanguage);
        List<VocabularySetJPAEntity> sets = vocabularySetRepository.findAll(VocabularySetSpecification.termsLanguageIs(language), pageRequest)
                .getContent();


       return SetInformationDTOMapper.from(sets, nextChunkUri);
    }

}
