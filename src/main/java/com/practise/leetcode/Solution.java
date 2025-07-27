package com.practise.leetcode;

import com.practise.model.ListNode;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public static void main(String[] args) {

    }


    /**
     * Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
     * <p>
     * You may assume that each input would have exactly one solution, and you may not use the same element twice.
     * <p>
     * You can return the answer in any order.
     * Example 1:
     * <p>
     * Input: nums = [2,7,11,15], target = 9
     * Output: [0,1]
     * Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
     * Example 2:
     * <p>
     * Input: nums = [3,2,4], target = 6
     * Output: [1,2]
     * Example 3:
     * <p>
     * Input: nums = [3,3], target = 6
     * Output: [0,1]
     **/

    /**
     * Optimized Two Sum solution using HashMap.
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     * <p>
     * Memory usage: Depends on input size 'n', map stores up to 'n' entries.
     * For int[] of size 10^5, approx space used by HashMap = ~1.6MB
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }


    /**
     * <a href="https://leetcode.com/problems/add-two-numbers/">...</a>
     * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order,
     * and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.
     * <p>
     * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
     * <p>
     * <p>
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: l1 = [2,4,3], l2 = [5,6,4]
     * Output: [7,0,8]
     * Explanation: 342 + 465 = 807.
     * Example 2:
     * <p>
     * Input: l1 = [0], l2 = [0]
     * Output: [0]
     * Example 3:
     * <p>
     * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
     * Output: [8,9,9,9,0,0,0,1]
     * <p>
     * <p>
     * Constraints:
     * <p>
     * The number of nodes in each linked list is in the range [1, 100].
     * 0 <= Node.val <= 9
     * It is guaranteed that the list represents a number that does not have leading zeros.
     **/

    /**
     * 🧠 Time & Space Complexity
     * Time Complexity: O(max(m, n)) — where m and n are lengths of the lists.
     * <p>
     * Space Complexity: O(max(m, n)) — for the output list (not counting input space).
     * <p>
     * Memory Use: Linear with respect to list size; minimal overhead (~4–8 bytes per node reference).
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode currentList = dummyHead;
        int carry = 0;
        while (l1 != null || l2 != null || carry > 0) {
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;
            int sum = x + y + carry;
            carry = sum / 10;
            currentList.next = new ListNode(sum % 10);
            currentList = currentList.next;

            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;

        }
        return dummyHead.next;
    }

    /**
     * Given a string s, find the length of the longest substring without duplicate characters.
     * <p>
     * <p>
     * <p>
     * Example 1:
     * <p>
     * Input: s = "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3.
     * Example 2:
     * <p>
     * Input: s = "bbbbb"
     * Output: 1
     * Explanation: The answer is "b", with the length of 1.
     * Example 3:
     * <p>
     * Input: s = "pwwkew"
     * Output: 3
     * Explanation: The answer is "wke", with the length of 3.
     * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
     * <p>
     * <p>
     * Constraints:
     * <p>
     * 0 <= s.length <= 5 * 104
     * s consists of English letters, digits, symbols and spaces.
     */

    /**
     * 🧠 Time and Space Complexity
     * Time Complexity: O(n) — each character is visited at most twice.
     * <p>
     * Space Complexity: O(k) — where k is the size of the charset (≤ 128 ASCII characters).
     * <p>
     * Memory Usage: ~2–4 KB for input size 50,000.
     */
    public int lengthOfLongestSubstring(String s) {
       /* HashMap<Character, Integer> lastMap = new HashMap<>();
        int maxLength = 0;
        int start = 0;
        for (int end = 0; end < s.length(); end++) {
            char c = s.charAt(end);
            if (lastMap.containsKey(c) && lastMap.get(c) >= start) {
                start = lastMap.get(c) + 1;
            }
            lastMap.put(c, end);
            maxLength = Math.max(maxLength, end - start + 1);
        }
        return maxLength;*/
        int[] lastSeen = new int[128]; // ASCII range
        int maxLen = 0;
        int start = 0;

        for (int end = 0; end < s.length(); end++) {
            char c = s.charAt(end);
            // lastSeen[c] stores index + 1 to avoid default 0 conflict
            start = Math.max(start, lastSeen[c]);
            maxLen = Math.max(maxLen, end - start + 1);
            lastSeen[c] = end + 1;
        }

        return maxLen;
    }
}
