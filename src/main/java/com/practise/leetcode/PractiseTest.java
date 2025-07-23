package com.practise.leetcode;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class PractiseTest {

    @Test
    public void containsDuplicate() {
        int[] arr = {1, 2, 3, 1};
        final boolean ret = Practise.containsDuplicate(arr);
        assertTrue(ret);
    }

    @Test
    public void testIsAnagram() {
        String s = "anagram";
        String t = "nagaram";
        final boolean ret = Practise.isAnagram(s, t);
        assertTrue(ret);
    }

    @Test
    public void testTwoSum() {
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;

        int[] output = new int[]{0, 1};
        final int[] ret = Practise.twoSum(nums, target);
        Arrays.sort(ret);
        assertTrue(Arrays.equals(ret, output));
    }

    @Test
    public void testGroupAnagrams() {
        String[] input = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> output = List.of(List.of("bat"), List.of("nat", "tan"), List.of("ate", "eat", "tea"));
        final List<List<String>> lists = Practise.groupAnagrams(input);
        assertTrue(areListsEqualIgnoringOrder(lists, output));
    }

    @Test
    public void testTopKFrequentElements() {
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;
        int[] output = {1, 2};
        final int[] result = Practise.topKFrequent(nums, k);
        assertTrue(Arrays.equals(result, output));
    }

    @Test
    public void testProductExceptSelf() {
        int[] nums = {-1, 1, 0, -3, 3};
        int[] output = {0, 0, 9, 0, 0};
        final int[] result = Practise.productExceptSelf(nums);
        assertTrue(Arrays.equals(result, output));
    }

    @Test
    public void testIsValidSodoku() {
        char[][] board =
            {{'5', '3', '.', '.', '7', '.', '.', '.', '.'}
                , {'6', '.', '.', '1', '9', '5', '.', '.', '.'}
                , {'.', '9', '8', '.', '.', '.', '.', '6', '.'}
                , {'8', '.', '.', '.', '6', '.', '.', '.', '3'}
                , {'4', '.', '.', '8', '.', '3', '.', '.', '1'}
                , {'7', '.', '.', '.', '2', '.', '.', '.', '6'}
                , {'.', '6', '.', '.', '.', '.', '2', '8', '.'}
                , {'.', '.', '.', '4', '1', '9', '.', '.', '5'}
                , {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
        final boolean validSudoku = Practise.isValidSudoku(board);
        assertTrue(validSudoku);
    }

    @Test
    public void testLongestConsecutive() {
        int[] input = {100, 4, 200, 1, 3, 2};
        int output = 4;
        final int result = Practise.longestConsecutive(input);
        assertEquals(result, output);
    }

    @Test
    public void testIsPalindrome() {
        String input = "A man, a plan, a canal: Panama";
        final boolean palindrome = Practise.isPalindrome(input);
        assertTrue(palindrome);
    }

    @Test
    public void testTwoSumSorted() {
        int[] numbers = {2, 7, 11, 15};
        int target = 9;
        int[] output = {1, 2};
        final int[] result = Practise.twoSumSorted(numbers, target);
        assertTrue(Arrays.equals(result, output));
    }

    @Test
    public void testThreeSum() {
        int[] input = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> output = List.of(List.of(-1, -1, 2), List.of(-1, 0, 1));
        final List<List<Integer>> result = Practise.threeSum(input);
        assertTrue(areListsEqualIgnoringOrder(result, output));
    }

    @Test
    public void testMaxArea() {
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int output = 49;
        final int result = Practise.maxArea(height);
        assertEquals(output, result);
    }

    @Test
    public void testMaxProfit() {
        int[] prices = {7, 1, 5, 3, 6, 4};
        int profit = 5;
        final int result = Practise.maxProfit(prices);
        assertEquals(profit, result);
    }

    @Test
    public void testLongestSubString() {
        String s = "abcabcbb";
        int output = 3;
        final int result = Practise.lengthOfLongestSubstring(s);
        assertEquals(output, result);
    }

    @Test
    public void testCheckInclusion() {
        String s1 = "ab";
        String s2 = "eidbaooo";
        final boolean result = Practise.checkInclusion(s1, s2);
        assertTrue(result);
    }

    @Test
    public void testStringPermutations() {
        List<String> output = List.of("tmp",
            "tpm",
            "mtp",
            "mpt",
            "pmt",
            "ptm");
        String input = "tmp";
        final List<String> result = Practise.findAllPermutationInString(input);
        assertEquals(new HashSet<>(result), new HashSet<>(output));
    }

    @Test
    public void testIsValidParanthesis() {
        String input = "()";
        boolean output = true;
        final boolean result = Practise.isValidParanthesis(input);
        assertEquals(output, result);
    }

    @Test
    public void testMinStack() {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        assertEquals(-3, minStack.getMin()); // return -3
        minStack.pop();
        assertEquals(0, minStack.top());    // return 0
        assertEquals(-2, minStack.getMin()); // return -2
    }

    @Test
    public void testEvalExp() {
        String[] input = {"2", "1", "+", "3", "*"};
        int output = 9;
        int result = Practise.evalRPN(input);
        assertEquals(output, result);
    }

    @Test
    public void testGenerateParenthesis() {
        int n = 3;
        final List<String> result = Practise.generateParenthesis(n);
        List<String> output = List.of("((()))", "(()())", "(())()", "()(())", "()()()");
        assertEquals(new HashSet<>(result), new HashSet<>(output));
    }

    @Test
    public void testDailyTemperatures() {
        int[] temperatures = {30, 40, 50, 60};
        int[] output = {1, 1, 1, 0};
        final int[] result = Practise.dailyTemperatures(temperatures);
        assertTrue(Arrays.equals(output, result));
    }

    @Test
    public void searchTest() {
        int[] nums = {-1, 0, 3, 5, 9, 12};
        int target = 9;
        final int result = Practise.search(nums, target);
        int output = 4;
        assertEquals(output, result);
    }

    @Test
    public void testMatrixSearch() {
        int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        int target = 3;
        boolean output = true;
        final boolean result = Practise.searchMatrix(matrix, target);
        assertEquals(output, result);
    }

    @Test
    public void testMinSearch() {
        int[] nums = {5, 1, 2, 3, 4};
        int output = 1;
        final int result = Practise.findMin(nums);
        assertEquals(output, result);
    }

    @Test
    public void testSearchTest() {
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        int target = 0;
        int output = 4;
        int result = Practise.searchTarget(nums, target);
        assertEquals(output, result);
    }

    @Test
    public void testReverseLinkedList() {
        final Practise.ListNode output = createNode(new int[]{5, 4, 3, 2, 1});
        final Practise.ListNode result = Practise.reverseList(createNode(new int[]{1, 2, 3, 4, 5}));
        assertTrue(compareNode(result, output));
    }

    @Test
    public void testMergeNodeList() {
        final Practise.ListNode first = createNode(new int[]{1, 2, 4});
        final Practise.ListNode second = createNode(new int[]{1, 3, 4});
        final Practise.ListNode result = Practise.mergeTwoLists(first, second);
        final Practise.ListNode output = createNode(new int[]{1, 1, 2, 3, 4, 4});
        assertTrue(compareNode(output, result));
    }

    @Test
    public void testReverseNodeList() {
        final Practise.ListNode input = createNode(new int[]{1, 2, 3, 4});
        final Practise.ListNode output = createNode(new int[]{1, 4, 2, 3});
        Practise.reorderList(input);
        assertTrue(compareNode(output, input));
    }

    @Test
    public void testRemoveNodeFromLast() {
        final Practise.ListNode input = createNode(new int[]{1});
        final Practise.ListNode output = createNode(new int[]{});
        final Practise.ListNode result = Practise.removeNthFromEnd(input, 1);
        assertTrue(compareNode(output, result));
    }

    @Test
    public void testAddTwoNumbers() {
        final Practise.ListNode first = createNode(new int[]{2, 4, 3});
        final Practise.ListNode second = createNode(new int[]{5, 6, 4});
        final Practise.ListNode output = createNode(new int[]{7, 0, 8});
        final Practise.ListNode result = Practise.addTwoNumbers(first, second);
        assertTrue(compareNode(output, result));
    }

    @Test
    public void mergeKLists() {
        final Practise.ListNode first = createNode(new int[]{1, 4, 5});
        final Practise.ListNode second = createNode(new int[]{1, 3, 4});
        final Practise.ListNode third = createNode(new int[]{2, 6});

        final Practise.ListNode output = createNode(new int[]{1, 1, 2, 3, 4, 4, 5, 6});
        final Practise.ListNode result = Practise.mergeKLists(new Practise.ListNode[]{first, second, third});
        assertTrue(compareNode(output, result));
    }

    @Test
    public void testLRUCache() {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // cache is {1=1}
        lRUCache.put(2, 2); // cache is {1=1, 2=2}
        assertEquals(1, lRUCache.get(1));    // return 1
        lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
        assertEquals(-1, lRUCache.get(2));    // returns -1 (not found)
        lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
        assertEquals(-1, lRUCache.get(1));    // return -1 (not found)
        assertEquals(3, lRUCache.get(3));    // return 3
        assertEquals(4, lRUCache.get(4));    // return 4
    }

    @Test
    public void testInvertTree() {
        Integer[] input = {4, 2, 7, 1, 3, 6, 9};
        Integer[] output = {4, 7, 2, 9, 6, 3, 1};
        final Practise.TreeNode inputNode = createTreeNode(input);
        final Practise.TreeNode outputNode = createTreeNode(output);
        final Practise.TreeNode result = Practise.invertTree(inputNode);
        assertTrue(compareTreeNode(outputNode, result));

    }

    @Test
    public void testDiameterOfBinaryTree() {
        Integer[] root = {1, 2, 3, 4, 5};
        int output = 3;
        final Practise.TreeNode input = createTreeNode(root);
        final int result = Practise.diameterOfBinaryTree(input);
        assertEquals(output, result);
    }

    @Test
    public void testBalancedTree() {
        Integer[] root = {3, 9, 20, null, null, 15, 7};
        boolean output = true;
        final Practise.TreeNode inputNode = createTreeNode(root);
        final boolean result = Practise.isBalanced(inputNode);
        assertEquals(output, result);
    }

    @Test
    public void testIsSameTree() {
        Practise.TreeNode p = createTreeNode(new Integer[]{1, 2, 3});
        Practise.TreeNode q = createTreeNode(new Integer[]{1, 2, 3});
        boolean output = true;
        final boolean result = Practise.isSameTree(p, q);
        assertEquals(output, result);
    }

    @Test
    public void testIsSubTree() {
        Practise.TreeNode p = createTreeNode(new Integer[]{3, 4, 5, 1, 2});
        Practise.TreeNode q = createTreeNode(new Integer[]{4, 1, 2});
        boolean output = true;
        final boolean result = Practise.isSubtree(p, q);
        assertEquals(output, result);
    }

    @Test
    public void testLevelOrder() {
        Practise.TreeNode p = createTreeNode(new Integer[]{3, 9, 20, null, null, 15, 7});
        List<List<Integer>> output = List.of(List.of(3), List.of(9, 20), List.of(15, 7));
        final List<List<Integer>> result = Practise.levelOrder(p);
        assertTrue(areListsEqual(output, result));
    }

    @Test
    public void testRightSideView() {
        Practise.TreeNode p = createTreeNode(new Integer[]{1, 2, 3, null, 5, null, 4});
        List<Integer> output = List.of(1, 3, 4);
        final List<Integer> result = Practise.rightSideView(p);
        assertTrue(output.equals(result));
    }

    @Test
    public void testTrieNode() {
        Trie trie = new Trie();
        trie.insert("apple");
        assertTrue(trie.search("apple"));   // return True
        assertFalse(trie.search("app"));     // return False
        assertTrue(trie.startsWith("app")); // return True
        trie.insert("app");
        assertTrue(trie.search("app"));     // return True
    }

    @Test
    public void testKtLargest() {
        Practise.KthLargest kthLargest = new Practise.KthLargest(3, new int[]{4, 5, 8, 2});
        assertEquals(4, kthLargest.add(3));   // return 4
        assertEquals(5, kthLargest.add(5));   // return 5
        assertEquals(5, kthLargest.add(10));  // return 5
        assertEquals(8, kthLargest.add(9));   // return 8
        assertEquals(8, kthLargest.add(4));   // return 8
    }

    @Test
    public void testLastStoneWeight() {
        int[] input = {2, 7, 4, 1, 8, 1};
        int output = 1;
        assertEquals(output, Practise.lastStoneWeight(input));
    }

    @Test
    public void testFindKthLargest() {
        int[] nums = {3, 2, 1, 5, 6, 4};
        int k = 2;
        int output = 5;
        assertEquals(output, Practise.findKthLargest(nums, k));
    }

    @Test
    public void testSubsets() {
        int[] input = {1, 2, 3};
        List<List<Integer>> output = List.of(List.of(), List.of(1), List.of(2), List.of(1, 2), List.of(3), List.of(1, 3), List.of(2, 3), List.of(1, 2, 3));
        final List<List<Integer>> result = Practise.subsets(input);
        assertTrue(areListsEqualIgnoringOrder(output, result));
    }

    @Test
    public void testCombinationSum() {
        int[] input = {2, 3, 6, 7};
        int target = 7;
        List<List<Integer>> output = List.of(List.of(2, 2, 3), List.of(7));
        final List<List<Integer>> result = Practise.combinationSum(input, target);
        assertTrue(areListsEqualIgnoringOrder(output, result));
    }

    @Test
    public void testPermute() {
        int[] input = {0, 1};
        List<List<Integer>> output = List.of(List.of(0, 1), List.of(1, 0));
        final List<List<Integer>> result = Practise.permute(input);
        assertTrue(areListsEqualIgnoringOrder(output, result));
    }

    @Test
    public void testSubsetWithDup() {
        int[] input = {1, 2, 2};
        List<List<Integer>> output = List.of(List.of(), List.of(1), List.of(1, 2), List.of(1, 2, 2), List.of(2, 2));
        final List<List<Integer>> result = Practise.subsetsWithDup(input);
        assertTrue(areListsEqualIgnoringOrder(output, result));
    }

    @Test
    public void testSumCombination2() {
        int[] input = {10, 1, 2, 7, 6, 1, 5};
        int target = 8;
        List<List<Integer>> output = List.of(List.of(1, 1, 6), List.of(1, 2, 5), List.of(1, 7), List.of(2, 6));
        final List<List<Integer>> result = Practise.combinationSum2(input, target);
        assertTrue(areListsEqualIgnoringOrder(output, result));
    }

    @Test
    public void testWordSearch() {
        char[][] input = {{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
        String word = "ABCCED";
        boolean output = true;
        final boolean result = Practise.wordExist(input, word);
        assertEquals(output, result);
    }

    @Test
    public void testNumOfIslands() {
        char[][] input = {{'1', '1', '1', '1', '0'}, {'1', '1', '0', '1', '0'}, {'1', '1', '0', '0', '0'}, {'0', '0', '0', '0', '0'}};
        int output = 1;
        final int result = Practise.numIslands(input);
        assertEquals(output, result);
    }

    @Test
    public void testClimbStairs() {
        int n = 2;
        int output = 2;
        final int result = Practise.climbStairs(n);
        assertEquals(output, result);
    }

    @Test
    public void testMinStarsForClimb() {
        int[] cost = {10, 15, 20};
        int output = 15;
        final int result = Practise.minCostClimbingStairs(cost);
        assertEquals(output, result);
    }

    public static boolean areListsEqual(List<List<Integer>> list1, List<List<Integer>> list2) {
        // Check if the lists are of the same size
        if (list1.size() != list2.size()) {
            return false;
        }

        // Iterate through the nested lists and compare their elements
        for (int i = 0; i < list1.size(); i++) {
            List<Integer> subList1 = list1.get(i);
            List<Integer> subList2 = list2.get(i);

            // Check if the nested lists are of the same size
            if (subList1.size() != subList2.size()) {
                return false;
            }

            // Compare the elements of the nested lists
            for (int j = 0; j < subList1.size(); j++) {
                if (!Objects.equals(subList1.get(j), subList2.get(j))) {
                    return false;
                }
            }
        }

        return true;
    }

    private static Practise.TreeNode createTreeNode(Integer[] vals) {
        return createTreeNode(vals, 0);
    }

    private static Practise.TreeNode createTreeNode(Integer[] vals, int index) {
        Practise.TreeNode root = null;

        if (vals != null && index < vals.length) {
            if (vals[index] != null && vals[index] != -1) {  // Assuming -1 indicates a null node
                root = new Practise.TreeNode(vals[index]);
                root.left = createTreeNode(vals, 2 * index + 1);
                root.right = createTreeNode(vals, 2 * index + 2);
            }
        }
        return root;
    }

    private static boolean compareTreeNode(Practise.TreeNode left, Practise.TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }

        if (left.val != right.val) {
            return false;
        }
        return compareTreeNode(left.left, right.left) && compareTreeNode(left.right, right.right);
    }

    private static boolean compareNode(Practise.ListNode src, Practise.ListNode target) {
        if (src == null && target == null) {
            return true;
        }
        if (src == null || target == null) {
            return false;
        }
        if (src.val != target.val) {
            return false;
        }
        return compareNode(src.next, target.next);
    }

    private static Practise.ListNode createNode(int[] vals) {
        Practise.ListNode head = null;
        Practise.ListNode root = null;
        for (int i : vals) {
            if (head == null) {
                head = new Practise.ListNode();
                root = head;
            } else {
                head.next = new Practise.ListNode();
                head = head.next;
            }
            head.val = i;
        }
        return root;
    }

    public static <T> boolean areListsEqualIgnoringOrder(List<List<T>> list1, List<List<T>> list2) {
        Set<Set<T>> set1 = list1.stream()
            .map(HashSet::new)
            .collect(Collectors.toSet());

        Set<Set<T>> set2 = list2.stream()
            .map(HashSet::new)
            .collect(Collectors.toSet());

        return set1.equals(set2);
    }

}
