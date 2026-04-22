package test.study.leetcodeexplain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerProblem(@RequestParam String title,
                                  @RequestParam String description,
                                  @RequestParam(required = false) String input1,
                                  @RequestParam(required = false) String output1,
                                  @RequestParam(required = false) String input2,
                                  @RequestParam(required = false) String output2,
                                  @RequestParam(required = false) String input3,
                                  @RequestParam(required = false) String output3) {
        
        Problem problem = Problem.builder()
                .title(title)
                .description(description)
                .testCases(new java.util.ArrayList<>())
                .build();

        addTestCase(problem, 1, input1, output1);
        addTestCase(problem, 2, input2, output2);
        addTestCase(problem, 3, input3, output3);
        
        problemService.save(problem);
        return "redirect:/";
    }

    private void addTestCase(Problem problem, int seq, String input, String output) {
        if ((input != null && !input.isBlank()) || (output != null && !output.isBlank())) {
            test.study.leetcodeexplain.domain.TestCase tc = test.study.leetcodeexplain.domain.TestCase.builder()
                    .problem(problem)
                    .sequence(seq)
                    .input(input)
                    .output(output)
                    .build();
            problem.getTestCases().add(tc);
        }
    }
}
