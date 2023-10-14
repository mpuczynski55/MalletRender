package com.agh.mallet.domain.vocabularyset.boundary;

import com.agh.api.SetInformationDTO;
import com.agh.mallet.domain.vocabularyset.control.service.VocabularySetService;
import com.agh.mallet.infrastructure.utils.NextChunkRebuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "set", produces = MediaType.APPLICATION_JSON_VALUE)
public class VocabularySetResource {

    private final VocabularySetService vocabularySetService;

    public VocabularySetResource(VocabularySetService vocabularySetService) {
        this.vocabularySetService = vocabularySetService;
    }

    @GetMapping
    public ResponseEntity<List<SetInformationDTO>> get(@RequestParam(name = "startPosition", defaultValue = "0") int startPosition,
                                                       @RequestParam(name = "limit", defaultValue = "10") int limit,
                                                       @RequestParam(name = "language") String primaryLanguage,
                                                       HttpServletRequest request) {
        String nextChunkUri = NextChunkRebuilder.rebuild(startPosition, limit, request);
        List<SetInformationDTO> sets = vocabularySetService.findByTermsLanguage(startPosition, limit, primaryLanguage, nextChunkUri);

        return new ResponseEntity<>(sets, HttpStatus.OK);
    }

}
