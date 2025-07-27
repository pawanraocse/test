package com.practise.leetcode;

import com.practise.model.ListNode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    @Test
    void testTwoSumExample1() {
        Solution solution = new Solution();
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] result = solution.twoSum(nums, target);
        assertNotNull(result);
        assertEquals(2, result.length);
        assertTrue((result[0] == 0 && result[1] == 1) || (result[0] == 1 && result[1] == 0));
    }

    @Test
    void testAddTwoNumbersExample1() {
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        Solution solution = new Solution();
        ListNode result = solution.addTwoNumbers(l1, l2);
        int[] expected = {7, 0, 8};
        for (int val : expected) {
            assertNotNull(result);
            assertEquals(val, result.val);
            result = result.next;
        }
        assertNull(result);
    }

    @Test
    void testAddTwoNumbersExample2() {
        ListNode l1 = new ListNode(0);
        ListNode l2 = new ListNode(0);
        Solution solution = new Solution();
        ListNode result = solution.addTwoNumbers(l1, l2);
        assertNotNull(result);
        assertEquals(0, result.val);
        assertNull(result.next);
    }

    @Test
    void testAddTwoNumbersExample3() {
        ListNode l1 = new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9)))))));
        ListNode l2 = new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9))));
        Solution solution = new Solution();
        ListNode result = solution.addTwoNumbers(l1, l2);
        int[] expected = {8,9,9,9,0,0,0,1};
        for (int val : expected) {
            assertNotNull(result);
            assertEquals(val, result.val);
            result = result.next;
        }
        assertNull(result);
    }

    @Test
    void testLengthOfLongestSubstring_Example1() {
        Solution solution = new Solution();
        String s = "abba";
        int result = solution.lengthOfLongestSubstring(s);
        assertEquals(2, result); // "abb"
    }

    @Test
    void testLengthOfLongestSubstring_Example2() {
        Solution solution = new Solution();
        String s = "bbbbb";
        int result = solution.lengthOfLongestSubstring(s);
        assertEquals(1, result); // "b"
    }

    @Test
    void testLengthOfLongestSubstring_Example3() {
        Solution solution = new Solution();
        String s = "pwwkew";
        int result = solution.lengthOfLongestSubstring(s);
        assertEquals(3, result); // "wke"
    }

    @Test
    void testLengthOfLongestSubstring_EmptyString() {
        Solution solution = new Solution();
        String s = "";
        int result = solution.lengthOfLongestSubstring(s);
        assertEquals(0, result);
    }

    @Test
    void testLengthOfLongestSubstring_AllUnique() {
        Solution solution = new Solution();
        String s = "abcdef";
        int result = solution.lengthOfLongestSubstring(s);
        assertEquals(6, result);
    }
}
