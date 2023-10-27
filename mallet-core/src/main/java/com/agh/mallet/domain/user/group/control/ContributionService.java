package com.agh.mallet.domain.user.group.control;

import com.agh.mallet.domain.user.group.entity.ContributionJPAEntity;
import com.agh.mallet.infrastructure.exception.MalletNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

@Service
public class ContributionService {

    public static final String CONTRIBUTION_NOT_FOUND_ERROR_MSG = "Contribution with id: {0} was not found";
    private final ContributionRepository contributionRepository;

    public ContributionService(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }

    public ContributionJPAEntity getById(Long id) {
        return contributionRepository.findById(id)
                .orElseThrow(supplyContributionNotFoundException(id));
    }

    public List<ContributionJPAEntity> getByIds(Collection<Long> ids) {
        return contributionRepository.findAllById(ids);
    }


    private Supplier<MalletNotFoundException> supplyContributionNotFoundException(Long id) {
        return () -> {
            String message = MessageFormat.format(CONTRIBUTION_NOT_FOUND_ERROR_MSG, id);
            return new MalletNotFoundException(message);
        };
    }

}
