package test.study.leetcodeexplain;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import test.study.leetcodeexplain.domain.Problem;
import test.study.leetcodeexplain.repository.ProblemRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProblemRepository problemRepository;

    @Override
    public void run(String... args) throws Exception {
        addProblemIfMissing("Two Sum", 
            "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.",
            """
            public int[] twoSum(int[] nums, int target) {
                Map<Integer, Integer> map = new HashMap<>();
                for (int i = 0; i < nums.length; i++) {
                    int complement = target - nums[i];
                    if (map.containsKey(complement)) {
                        return new int[] { map.get(complement), i };
                    }
                    map.put(nums[i], i);
                }
                return null;
            }""");

        addProblemIfMissing("Palindrome Number",
            "Given an integer x, return true if x is a palindrome, and false otherwise.",
            """
            public boolean isPalindrome(int x) {
                if (x < 0) return false;
                int reversed = 0, remainder, original = x;
                while (x != 0) {
                    remainder = x % 10;
                    reversed = reversed * 10 + remainder;
                    x /= 10;
                }
                return original == reversed;
            }""");

        addProblemIfMissing("Group Anagrams",
            "Given an array of strings strs, group the anagrams together. You can return the answer in any order.",
            """
            class Solution {
                public List<List<String>> groupAnagrams(String[] strs) {
                    /* 문자열의 순서는 상관이 없고, 임의 문자열에 어떤 소알파벳이 몇 개씩 등장했는지가 주요 지표
                    HashMap 처럼 key, value 값을 동시에 표기할 수 있는 자료형이 필요함
                    제한조건에 *영소문자*가 있으니까 구체적인 key값까지 필요가 없고 1~26 식별 가능하기만 해도 괜찮음. value는 count가 가능한 숫자(정수)형이면 더 좋겠지
                    int[26] 혹은 HashMap 형태로 각 str 값을 파악한 다음 동일 객체값을 지녔을 경우에 한 그룹으로 묶게끔 진행하면 될 것 같음 - 저장된 객체 정보를 어떻게 조회할 것인가가 관건일까 */

                    Map<String, List<String>> mapper = new HashMap<>();

                    for (String str : strs) {
                        int[] identity = new int[26];
                        for (char chr : str.toCharArray()) {
                            identity[chr - 'a']++;
                        }
                        String key = Arrays.toString(identity);
                        mapper.computeIfAbsent(key, v -> new ArrayList<>()).add(str);
                    }

                    return new ArrayList<>(mapper.values());
                }
            }""");
    }

    private void addProblemIfMissing(String title, String description, String solution) {
        if (problemRepository.findAll().stream().noneMatch(p -> p.getTitle().equals(title))) {
            problemRepository.save(Problem.builder()
                    .title(title)
                    .description(description)
                    .solution(solution)
                    .build());
        }
    }
}
