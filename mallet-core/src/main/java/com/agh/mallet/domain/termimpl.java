package com.agh.mallet.domain;

import com.agh.mallet.domain.set.control.repository.VocabularySetRepository;
import com.agh.mallet.domain.set.entity.VocabularySetJPAEntity;
import com.agh.mallet.domain.user.control.repo.UserRepository;
import com.agh.mallet.domain.user.control.repo.UserVocabularyRepository;
import com.agh.mallet.domain.user.entity.PermissionType;
import com.agh.mallet.domain.user.entity.UserJPAEntity;
import com.agh.mallet.domain.user.entity.UserVocabularySetJPAEntity;
import com.agh.mallet.domain.vocabulary.control.repository.TermRepository;
import com.agh.mallet.domain.vocabulary.entity.Language;
import com.agh.mallet.domain.vocabulary.entity.TermJPAEntity;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Service
public class termimpl {

    private final TermRepository TermRepository;

    private final UserRepository UserRepository;
    private final UserVocabularyRepository UserVocabularyRepository;
    private final VocabularySetRepository VocabularySetRepository;

    public termimpl(TermRepository TermRepository, UserRepository UserRepository, UserVocabularyRepository UserVocabularyRepository, VocabularySetRepository VocabularySetRepository) {
        this.TermRepository = TermRepository;
        this.UserRepository = UserRepository;
        this.UserVocabularyRepository = UserVocabularyRepository;
        this.VocabularySetRepository = VocabularySetRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        TermJPAEntity butt = new TermJPAEntity("BUTT", Language.EN, "GRAM1", "TOPIC1");
        TermJPAEntity dupa = new TermJPAEntity("DUPA", Language.PL, "WULGARYZM", "TEMAT1", Collections.singleton(butt));
        butt.setTranslationsOfTerm(Collections.singleton(dupa));
        TermRepository.save(butt);
        TermRepository.save(dupa);

        Optional<TermJPAEntity> byId = TermRepository.findById(1L);


        UserJPAEntity userJPAEntity = new UserJPAEntity("lukasz", "chuj", LocalDate.now(), "chuj", Collections.emptySet());
        UserRepository.save(userJPAEntity);
        VocabularySetJPAEntity vocabularySetJPAEntity = new VocabularySetJPAEntity("set1", "dupa", Collections.singleton(dupa));
        VocabularySetRepository.save(vocabularySetJPAEntity);
        UserVocabularySetJPAEntity userVocabularySetJPAEntity = new UserVocabularySetJPAEntity(PermissionType.READ_WRITE, userJPAEntity, vocabularySetJPAEntity);
        UserVocabularyRepository.save(userVocabularySetJPAEntity);
        userJPAEntity.setVocabularySets(Collections.singleton(userVocabularySetJPAEntity));
        UserRepository.save(userJPAEntity);
    }
}
