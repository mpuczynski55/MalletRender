package com.agh.mallet.domain.set.control.service;

import com.agh.api.SetBasicDTO;
import com.agh.api.SetDetailDTO;
import com.agh.mallet.domain.set.control.repository.SetRepository;
import com.agh.mallet.domain.set.entity.SetJPAEntity;
import com.agh.mallet.domain.vocabulary.entity.Language;
import com.agh.mallet.domain.vocabulary.entity.TermJPAEntity;
import com.agh.mallet.infrastructure.exception.MalletNotFoundException;
import com.agh.mallet.infrastructure.mapper.SetBasicsDTOMapper;
import com.agh.mallet.infrastructure.mapper.SetInformationDTOMapper;
import com.agh.mallet.infrastructure.utils.NextChunkRebuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@Service
public class SetService {

    public static final String SET_NOT_FOUND_ERROR_MSG = "Set with id: {0} was not found";
    private final SetRepository setRepository;
    private final NextChunkRebuilder nextChunkRebuilder;

    public SetService(SetRepository setRepository, NextChunkRebuilder nextChunkRebuilder) {
        this.setRepository = setRepository;
        this.nextChunkRebuilder = nextChunkRebuilder;
    }

    public SetBasicDTO getBasics(Set<Long> ids) {
        List<SetJPAEntity> sets = setRepository.findAllById(ids);

        return SetBasicsDTOMapper.from(sets);
    }

    public SetDetailDTO get(long setId,
                            int startPosition,
                            int limit,
                            String primaryLanguage) {
        if (limit > 30) {
            limit = 30;
        }
        PageRequest pageRequest = PageRequest.of(startPosition, startPosition + limit);
        Language language = Language.from(primaryLanguage);

        List<TermJPAEntity> terms = setRepository.findAllTermsBySetIdAndLanguage(setId, language, pageRequest)
                .getContent();

        String nextChunkUri = nextChunkRebuilder.rebuild(terms, startPosition, limit);

        return SetInformationDTOMapper.from(setId, terms, nextChunkUri);
    }

    public SetBasicDTO getBasics(Set<Long> ids,
                                 int startPosition,
                                 int limit,
                                 String primaryLanguage) {
        if (!ids.isEmpty()) {
            return getBasics(ids);
        }

        if (limit > 10) {
            limit = 10;
        }

        PageRequest pageRequest = PageRequest.of(startPosition, startPosition + limit);
        Language language = Language.from(primaryLanguage);

        List<SetJPAEntity> sets = setRepository.findAllByTermsLanguage(language, pageRequest)
                .getContent();

        String nextChunkUri = nextChunkRebuilder.rebuild(sets, startPosition, limit);

        return SetBasicsDTOMapper.from(sets, nextChunkUri);
    }

    public SetJPAEntity getById(long id) {
        return setRepository.findById(id)
                .orElseThrow(supplySetNotFoundException(id));
    }

    private static Supplier<MalletNotFoundException> supplySetNotFoundException(long setId) {
        String message = MessageFormat.format(SET_NOT_FOUND_ERROR_MSG, setId);
        return () -> new MalletNotFoundException(message);
    }


}
