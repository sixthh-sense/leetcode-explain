package test.study.leetcodeexplain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import test.study.leetcodeexplain.domain.Problem;
import test.study.leetcodeexplain.service.ProblemService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping("/")
    public String index(Model model) {
        List<Problem> problems = problemService.findAll();
        model.addAttribute("problems", problems);
        return "index";
    }

    @GetMapping("/problems/{id}")
    public String getProblem(@PathVariable Long id, Model model) {
        problemService.findOne(id).ifPresent(problem -> {
            model.addAttribute("problem", problem);
        });
        return "problem";
    }
}
