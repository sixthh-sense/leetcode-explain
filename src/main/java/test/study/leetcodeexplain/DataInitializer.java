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
            "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.\n\nYou may assume that each input would have exactly one solution, and you may not use the same element twice.\n\nYou can return the answer in any order.",
            """
            Example 1:
            Input: nums = [2,7,11,15], target = 9
            Output: [0,1]
            Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].

            Example 2:
            Input: nums = [3,2,4], target = 6
            Output: [1,2]

            Example 3:
            Input: nums = [3,3], target = 6
            Output: [0,1]""",
            """
            class Solution {
                public int[] twoSum(int[] nums, int target) {

                }
            }""",
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
            "Given an integer x, return true if x is a palindrome, and false otherwise.\n\nAn integer is a palindrome when it reads the same forward and backward.\n\nFor example, 121 is a palindrome while 123 is not.",
            """
            Example 1:
            Input: x = 121
            Output: true
            Explanation: 121 reads as 121 from left to right and from right to left.

            Example 2:
            Input: x = -121
            Output: false
            Explanation: From left to right, it reads -121. From right to left it becomes 121-. Therefore it is not a palindrome.

            Example 3:
            Input: x = 10
            Output: false
            Explanation: Reads 01 from right to left. Therefore it is not a palindrome.""",
            """
            class Solution {
                public boolean isPalindrome(int x) {

                }
            }""",
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
            "Given an array of strings strs, group the anagrams together. You can return the answer in any order.\n\nAn anagram is a word or phrase formed by rearranging the letters of a different word or phrase, using all the original letters exactly once.",
            """
            Example 1:
            Input: strs = ["eat","tea","tan","ate","nat","bat"]
            Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
            Explanation: There is no string in strs that can be rearranged to form "bat".
            The strings "nat" and "tan" are anagrams as they can be rearranged to form each other.
            The strings "ate", "eat", and "tea" are anagrams as they can be rearranged to form each other.

            Example 2:
            Input: strs = [""]
            Output: [[""]]

            Example 3:
            Input: strs = ["a"]
            Output: [["a"]]""",
            """
            class Solution {
                public List<List<String>> groupAnagrams(String[] strs) {

                }
            }""",
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

    private void addProblemIfMissing(String title, String description, String testCases, String codeSnippet, String solution) {
        if (problemRepository.findAll().stream().noneMatch(p -> p.getTitle().equals(title))) {
            problemRepository.save(Problem.builder()
                    .title(title)
                    .description(description)
                    .testCases(testCases)
                    .codeSnippet(codeSnippet)
                    .solution(solution)
                    .build());
        }
    }
}
