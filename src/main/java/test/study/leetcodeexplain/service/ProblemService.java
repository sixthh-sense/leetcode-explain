package test.study.leetcodeexplain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.study.leetcodeexplain.domain.Problem;
import test.study.leetcodeexplain.repository.ProblemRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    @Transactional
    public Long save(Problem problem) {
        problemRepository.save(problem);
        return problem.getId();
    }

    public List<Problem> findAll() {
        return problemRepository.findAll();
    }

    public Optional<Problem> findOne(Long id) {
        return problemRepository.findById(id);
    }
}
