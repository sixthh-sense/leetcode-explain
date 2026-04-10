package test.study.leetcodeexplain;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import test.study.leetcodeexplain.domain.Problem;
import test.study.leetcodeexplain.repository.ProblemRepository;

import java.util.Optional;

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
            }""",
            null,
            null);

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
            }""",
            null,
            null);

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
            }""",
            null,
            """
            [ 시간 복잡도 — O(n * k) ]

            n = 입력 배열 strs의 길이 (문자열 개수)
            k = 배열 내 문자열의 최대 길이

            1) 외부 for문: strs 배열을 한 번 순회 → O(n)
            2) 내부 for문: 각 문자열을 char 단위로 순회하며 int[26] 빈도 배열 구성 → O(k)
            3) Arrays.toString(identity): 고정 크기 26의 배열을 문자열로 변환 → O(26) = O(1)
            4) HashMap.computeIfAbsent + add: 평균 O(1) (해시 충돌 무시 시)

            → 전체: O(n) × O(k) = O(n * k)

            참고: 정렬 기반 접근법(Arrays.sort)을 key로 쓰면 O(n * k log k)이므로,
            int[26] 빈도 배열 방식이 더 효율적임.

            ──────────────────────────────────────

            [ 공간 복잡도 — O(n * k) ]

            1) HashMap<String, List<String>> mapper
               - 최악의 경우 n개의 서로 다른 key가 생성됨 (아나그램 그룹이 전부 다를 때)
               - key: Arrays.toString 결과 → 각 O(1) (고정 길이 26)
               - value: 모든 문자열의 참조를 저장 → 총 O(n)개, 문자열 자체 크기 합산 O(n * k)

            2) int[26] identity 배열
               - 매 반복마다 새로 생성되지만 이전 것은 GC 대상 → O(1)

            3) 반환값 ArrayList<>(mapper.values())
               - 입력 문자열 전체를 담으므로 → O(n * k)

            → 전체 추가 공간: O(n * k)""");

        addProblemIfMissing("Top K Frequent Elements",
            "Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.",
            """
            Example 1:
            Input: nums = [1,1,1,2,2,3], k = 2
            Output: [1,2]

            Example 2:
            Input: nums = [1], k = 1
            Output: [1]

            Constraints:
            - 1 <= nums.length <= 10^5
            - -10^4 <= nums[i] <= 10^4
            - k is in the range [1, the number of unique elements in the array].
            - It is guaranteed that the answer is unique.""",
            """
            class Solution {
                public int[] topKFrequent(int[] nums, int k) {

                }
            }""",
            """
            class Solution {
                public int[] topKFrequent(int[] nums, int k) {
                    /* HashMap 하나 만든 다음에, num을 key로 두고, count를 1씩 더하고, 최종적으론 value가 k인 key값들의 목록을 만들어서 보낸다? 23:28:24 */
                    /* value가 k가 아니라 가장 빈번한 k개 - 그런데 *공동* 몇위가 나와버리면 어떡하지? it is guaranteed that the answer is unique 조건이 있어서 망정이지 이게 없었으면 문제가 매우 구려질 뻔 11:46:53 */

                    Map<Integer, Integer> mapper = new HashMap<>();

                    for (int num : nums) {
                        mapper.put(num, mapper.getOrDefault(num, 0) + 1);
                    }

                    // 빈도별 버킷
                    List<Integer>[] bucket = new List[nums.length + 1];

                    for (int key : mapper.keySet()) {
                        int freq = mapper.get(key);
                        if (bucket[freq] == null) {
                            bucket[freq] = new ArrayList<>();
                        }
                        bucket[freq].add(key);
                    }

                    int[] result = new int[k];
                    int idx = 0;

                    // 뒤에서부터 (빈도 높은 순)
                    for (int i = bucket.length - 1; i >= 0 && idx < k; i--) {
                        if (bucket[i] != null) {
                            for (int num : bucket[i]) {
                                result[idx++] = num;
                                if (idx == k) break;
                            }
                        }
                    }

                    return result;
                }
            }""",
            null,
            """
            [ 평균 시간 복잡도 — O(n) ]
            - n = nums 배열의 길이
            - HashMap에 각 숫자의 빈도를 계산하는 데 O(n) 소요
            - 빈도별 버킷을 만드는 데 O(n) 소요
            - 버킷을 뒤에서부터 순회하며 k개의 요소를 찾는 데 최악의 경우 O(n) 소요

            [ Worst Case 시간 복잡도 — O(n) ]
            - 모든 요소의 빈도가 동일하더라도 버킷 순회는 n만큼 수행되므로 O(n) 유지

            ──────────────────────────────────────

            [ 평균 공간 복잡도 — O(n) ]
            - HashMap에 최대 n개의 고유 요소 저장: O(n)
            - 빈도별 버킷(List 배열)에 최대 n개의 요소 분산 저장: O(n)

            [ Worst Case 공간 복잡도 — O(n) ]
            - 모든 숫자가 고유하여 n개의 버킷에 1개씩 들어가는 경우에도 O(n) 유지""");
    }

    private void addProblemIfMissing(String title, String description, String testCases,
                                     String codeSnippet, String solution,
                                     String memoryRuntime, String complexity) {
        Optional<Problem> existing = problemRepository.findAll().stream()
                .filter(p -> p.getTitle().equals(title))
                .findFirst();

        if (existing.isEmpty()) {
            problemRepository.save(Problem.builder()
                    .title(title)
                    .description(description)
                    .testCases(testCases)
                    .codeSnippet(codeSnippet)
                    .solution(solution)
                    .memoryRuntime(memoryRuntime)
                    .complexity(complexity)
                    .build());
        } else {
            Problem problem = existing.get();
            boolean updated = false;
            if (problem.getComplexity() == null && complexity != null) {
                problem.setComplexity(complexity);
                updated = true;
            }
            if (problem.getMemoryRuntime() == null && memoryRuntime != null) {
                problem.setMemoryRuntime(memoryRuntime);
                updated = true;
            }
            if (problem.getTestCases() == null && testCases != null) {
                problem.setTestCases(testCases);
                updated = true;
            }
            if (problem.getCodeSnippet() == null && codeSnippet != null) {
                problem.setCodeSnippet(codeSnippet);
                updated = true;
            }
            if (updated) {
                problemRepository.save(problem);
            }
        }
    }
}
