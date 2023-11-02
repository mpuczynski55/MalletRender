package com.agh.mallet.domain.term.control.service;

import com.agh.api.TermBasicListDTO;
import com.agh.mallet.domain.term.control.repository.TermRepository;
import com.agh.mallet.domain.term.entity.TermJPAEntity;
import com.agh.mallet.infrastructure.mapper.TermBasicListDTOMapper;
import com.agh.mallet.infrastructure.utils.NextChunkRebuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermService {

    private final TermRepository termRepository;
    private final NextChunkRebuilder nextChunkRebuilder;

    public TermService(TermRepository termRepository, NextChunkRebuilder nextChunkRebuilder) {
        this.termRepository = termRepository;
        this.nextChunkRebuilder = nextChunkRebuilder;
    }

    public TermBasicListDTO getByTerm(String term,
                                      int startPosition,
                                      int limit) {
        PageRequest pageRequest = PageRequest.of(startPosition, startPosition + limit);

        List<TermJPAEntity> terms = termRepository.findAllByTerm(term, pageRequest)
                .getContent();

        String nextChunkUri = nextChunkRebuilder.rebuild(terms, startPosition, limit);

        return TermBasicListDTOMapper.from(terms, nextChunkUri);
    }

}
