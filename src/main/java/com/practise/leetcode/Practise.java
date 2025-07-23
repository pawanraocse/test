package com.practise.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Practise {

    public static class ListNode {

        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static class TreeNode {

        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        final List<String> pawan = findAllPermutationInString("tmp");
        pawan.forEach(System.out::println);
    }

    public static boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (final int num : nums) {
            set.add(num);
        }

        return set.size() < nums.length;
    }

    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] arr = new int[26];
        for (int i = 0; i < s.length(); i++) {
            arr[s.charAt(i) - 'a']++;
            arr[t.charAt(i) - 'a']--;
        }
        for (final int i : arr) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }

    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> sumMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int required = target - num;
            if (sumMap.containsKey(required)) {
                int index = sumMap.get(required);
                return new int[]{i, index};
            } else {
                sumMap.put(num, i);
            }
        }
        return new int[0];
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (final String str : strs) {
            char[] chr = str.toCharArray();
            Arrays.sort(chr);
            String sortedStr = new String(chr);

            List<String> list = map.computeIfAbsent(sortedStr, k -> new ArrayList<>());
            list.add(str);
        }
        return new ArrayList<>(map.values());
    }

    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> numToCount = new HashMap<>();
        for (final int num : nums) {
            numToCount.put(num, numToCount.getOrDefault(num, 1) + 1);
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> numToCount.get(b) - numToCount.get(a));
        queue.addAll(numToCount.keySet());
        int[] result = new int[k];
        int i = 0;
        while (k > 0) {
            final Integer val = queue.poll();
            result[i++] = val;
            k--;
        }

        return result;
    }

    //nums = [1,2,3,4]
    public static int[] productExceptSelf(int[] nums) {
        int len = nums.length;
        int[] leftProduct = new int[len];
        int[] rightProduct = new int[len];
        leftProduct[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            leftProduct[i] = leftProduct[i - 1] * nums[i - 1];
        }
        //leftProduct = [1,1,2,6]
        rightProduct[len - 1] = 1;
        for (int i = len - 2; i >= 0; i--) {
            rightProduct[i] = rightProduct[i + 1] * nums[i + 1];
        }
        //rightProduct = [24,12,4,1]
        int[] result = new int[len];
        for (int i = 0; i < leftProduct.length; i++) {
            result[i] = leftProduct[i] * rightProduct[i];
        }
        return result;
    }

    public static boolean isValidSudoku(char[][] board) {
        Set<String> sudokuElems = new HashSet<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                char elem = board[i][j];
                if (elem != '.') {
                    String row = "Row" + (i) + elem;
                    String col = "Col" + (j) + elem;
                    String matrix = "Mat" + (i / 3) + (j / 3) + elem;
                    if (sudokuElems.contains(row) || sudokuElems.contains(col) || sudokuElems.contains(matrix)) {
                        return false;
                    }
                    sudokuElems.add(row);
                    sudokuElems.add(col);
                    sudokuElems.add(matrix);
                }
            }
        }
        return true;
    }

    public static int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (final int num : nums) {
            set.add(num);
        }
        int max = 0;
        for (final int num : nums) {
            if (!set.contains(num - 1)) {
                int count = 1;
                int next = num + 1;
                while (set.contains(next)) {
                    count++;
                    next++;
                }
                max = Math.max(max, count);
            }
        }
        return max;
    }

    public static boolean isPalindrome(String s) {
        String str = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        int left = 0;
        int right = str.length() - 1;
        while (left < right) {
            if (str.charAt(left++) != str.charAt(right--)) {
                return false;
            }
        }
        return true;
    }

    /*
    * Input: numbers = [2,7,11,15], target = 9
      Output: [1,2]
      Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We return [1, 2].
    * */
    public static int[] twoSumSorted(int[] numbers, int target) {
        int[] result = new int[2];
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                result[0] = left + 1;
                result[1] = right + 1;
                break;
            } else if (sum > target) {
                right--;
            } else {
                left++;
            }
        }
        return result;
    }

    /*
    * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]]
    * such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
    * Input: nums = [-1,0,1,2,-1,-4]
      Output: [[-1,-1,2],[-1,0,1]]
      Explanation:
        nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
        nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
        nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
        The distinct triplets are [-1,0,1] and [-1,-1,2].
        Notice that the order of the output and the order of the triplets does not matter.
    *
    *
    * */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (i != 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int firstNum = nums[i];
            int target = firstNum * -1;
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (target == sum) {
                    result.add(List.of(firstNum, nums[left], nums[right]));
                    left++;
                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;
                    }
                } else if (target > sum) {
                    left++;
                } else {
                    right--;
                }
            }

        }
        return result;
    }

    /*
     * You are given an integer array height of length n.
     * There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).
     * Input: height = [1,8,6,2,5,4,8,3,7]
     * Output: 49
     * Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7].
     * In this case, the max area of water (blue section) the container can contain is 49.
     * */
    public static int maxArea(int[] height) {
        int maxArea = 0;
        int left = 0;
        int right = height.length - 1;
        while (left < right) {
            int area = Math.min(height[left], height[right]) * (right - left);
            maxArea = Math.max(maxArea, area);
            if (height[left] <= height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }

    /*
    * You are given an array prices where prices[i] is the price of a given stock on the ith day.
      You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
      Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
    * Input: prices = [7,1,5,3,6,4]
      Output: 5
      Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
      Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
    * */
    public static int maxProfit(int[] prices) {
        int maxProfit = 0;
        int buyPrice = prices[0];
        for (int i = 1; i < prices.length; i++) {
            int currentPrice = prices[i];
            if (buyPrice >= currentPrice) {
                buyPrice = currentPrice;
            } else {
                int profit = currentPrice - buyPrice;
                maxProfit = Math.max(maxProfit, profit);
            }
        }
        return maxProfit;
    }

    /*
    * Input: s = "abcabcbb"
      Output: 3
      Explanation: The answer is "abc", with the length of 3.
    * */
    public static int lengthOfLongestSubstring(String s) {
        int left = 0;
        int right = 0;
        int maxLen = 0;
        Set<Character> set = new HashSet<>();
        while (right < s.length()) {
            char ch = s.charAt(right++);
            while (set.contains(ch)) {
                set.remove(s.charAt(left++));
            }
            set.add(ch);
            int len = right - left;
            maxLen = Math.max(maxLen, len);
        }
        return maxLen;
    }

    /*
    * Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.
      In other words, return true if one of s1's permutations is the substring of s2.
      Example 1:
      * Input: s1 = "ab", s2 = "eidbaooo"
        Output: true
        Explanation: s2 contains one permutation of s1 ("ba").
      Example 2:
        Input: s1 = "ab", s2 = "eidboaoo"
        Output: false
    *
    * */
    public static boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        int s1Len = s1.length();
        int[] arr1 = new int[26];
        int[] arr2 = new int[26];
        for (int i = 0; i < s1Len; i++) {
            arr1[s1.charAt(i) - 'a']++;
            arr2[s2.charAt(i) - 'a']++;
        }
        for (int i = 0; i < s2.length() - s1Len; i++) {
            if (Arrays.equals(arr1, arr2)) {
                return true;
            }

            arr2[s2.charAt(i) - 'a']--;
            arr2[s2.charAt(i + s1Len) - 'a']++;
        }
        return Arrays.equals(arr1, arr2);
    }

    public static List<String> findAllPermutationInString(String src) {
        List<String> listOfPerm = new ArrayList<>();
        stringPermutationHelper(listOfPerm, 0, src.toCharArray());
        return listOfPerm;
    }

    private static void stringPermutationHelper(List<String> list, int index, final char[] chars) {
        if (index == chars.length - 1) {
            list.add(new String(chars));
            return;
        }
        for (int i = index; i < chars.length; i++) {
            swapChars(chars, i, index);
            stringPermutationHelper(list, index + 1, chars);
            swapChars(chars, i, index);
        }
    }

    private static void swapChars(char[] chars, int i, int j) {
        char c = chars[i];
        chars[i] = chars[j];
        chars[j] = c;
    }

    /*
    * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
      An input string is valid if:
      Open brackets must be closed by the same type of brackets.
      Open brackets must be closed in the correct order.
      Every close bracket has a corresponding open bracket of the same type.
      Example 1:
        Input: s = "()"
        Output: true
    * */
    public static boolean isValidParanthesis(String s) {
        Stack<Character> stack = new Stack<>();
        for (final char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (!stack.isEmpty() && isMatchingBrackets(stack.peek(), c)) {
                stack.pop();
            } else {
                return false;
            }
        }
        return stack.isEmpty();
    }

    private static boolean isMatchingBrackets(char open, char close) {
        return (open == '(' && close == ')') ||
            (open == '{' && close == '}') ||
            (open == '[' && close == ']');
    }

    /*
     * The valid operators are '+', '-', '*', and '/'.
     * Example 1:
     * Input: tokens = ["2","1","+","3","*"]
     * Output: 9
     * Explanation: ((2 + 1) * 3) = 9
     * */
    public static int evalRPN(String[] tokens) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for (final String token : tokens) {
            try {
                int value = Integer.parseInt(token);
                stack.push(value);
            } catch (NumberFormatException e) {
                int num1 = stack.pop();
                int num2 = stack.pop();
                if (Objects.equals(token, "+")) {
                    stack.push(num1 + num2);
                } else if (Objects.equals(token, "-")) {
                    stack.push(num2 - num1);
                } else if (Objects.equals(token, "*")) {
                    stack.push(num1 * num2);
                } else if (Objects.equals(token, "/")) {
                    stack.push(num2 / num1);
                }
            }
        }
        return stack.pop();
    }

    /*
     * Example 1:
     * Input: n = 3
     * Output: ["((()))","(()())","(())()","()(())","()()()"]
     * */
    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        generateStringBrackets(n, 0, 0, result, new StringBuilder());
        return result;
    }

    private static void generateStringBrackets(int max, int left, int right, List<String> result, StringBuilder sb) {
        if (max * 2 == sb.length()) {
            result.add(sb.toString());
            return;
        }

        if (left < max) {
            sb.append("(");
            generateStringBrackets(max, left + 1, right, result, sb);
            sb.deleteCharAt(sb.length() - 1);
        }

        if (right < left) {
            sb.append(")");
            generateStringBrackets(max, left, right + 1, result, sb);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    /*
    * Given an array of integers temperatures represents the daily temperatures, return an array answer such that answer[i]
    * is the number of days you have to wait after the ith day to get a warmer temperature.
    *  If there is no future day for which this is possible, keep answer[i] == 0 instead.
    *Example 1:
    Input: temperatures = [73,74,75,71,69,72,76,73]
    Output: [1,1,4,2,1,1,0,0]
    *
    Example 2:
    Input: temperatures = [30,40,50,60]
    Output: [1,1,1,0]
    * */
    public static int[] dailyTemperatures(int[] temperatures) {
        int[] result = new int[temperatures.length];
        int hottestDay = 0;
        for (int i = temperatures.length - 1; i >= 0; i--) {
            int current = temperatures[i];
            hottestDay = Math.max(current, hottestDay);
            if (current < hottestDay) {
                int days = 1;
                while (current >= temperatures[i + days]) {
                    days += result[i + days];
                }
                result[i] = days;
            }
        }
        return result;
    }

    /*
     * Example 1:
     * Input: nums = [-1,0,3,5,9,12], target = 9
     * Output: 4
     * Explanation: 9 exists in nums and its index is 4
     * */
    public static int search(int[] nums, int target) {
        int index = -1;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int midVal = nums[mid];
            if (midVal == target) {
                index = mid;
                break;
            } else if (midVal >= target) {
                right--;
            } else {
                left++;
            }
        }
        return index;
    }

    /*
     * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
     * Output: true
     * */
    public static boolean searchMatrix(int[][] matrix, int target) {
        int row = matrix.length;
        int col = matrix[0].length;
        int l = 0;
        int r = (row * col) - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            int ele = matrix[mid / col][mid % col];
            if (ele == target) {
                return true;
            } else if (ele <= target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return false;
    }

    /*
     * Example 1:
     * Input: nums = [3,4,5,1,2]
     * Output: 1
     * Explanation: The original array was [1,2,3,4,5] rotated 3 times.
     * */
    public static int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = (right + left) / 2;

            // If mid element is greater than the right element, the minimum is in the right half.
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            }
            // If mid element is less than the right element, the minimum is in the left half.
            else if (nums[mid] < nums[right]) {
                right = mid;
            }
            // If mid element is equal to the right element, we can't decide which half to search.
            // So, we decrement the right pointer to reduce the search space.
            else {
                right--;
            }
        }

        // At the end of the loop, 'left' will be pointing to the minimum element.
        return nums[left];
    }

    /*
     * Example 1:
     * Input: nums = [4,5,6,7,0,1,2], target = 0
     * Output: 4
     * */
    public static int searchTarget(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            if (nums[l] <= nums[mid]) {
                if (target >= nums[l] && target < nums[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[r]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }

        return -1;
    }

    /*
     * Given the head of a singly linked list, reverse the list, and return the reversed list.
     * Input: head = [1,2,3,4,5]
     * Output: [5,4,3,2,1]
     * */
    protected static ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode prev = null;
        while (head.next != null) {
            ListNode node = head.next;
            head.next = prev;
            prev = head;
            head = node;
        }
        head.next = prev;
        return head;
    }

    /*
     * You are given the heads of two sorted linked lists list1 and list2.
     * Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists.
     * Return the head of the merged linked list.
     * Input: list1 = [1,2,4], list2 = [1,3,4]
     * Output: [1,1,2,3,4,4]
     * */
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null && list2 == null) {
            return null;
        }
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        if (list1.val <= list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
        }
    }

    /*
     * You are given the head of a singly linked-list. The list can be represented as:
     * L0 → L1 → … → Ln - 1 → Ln
     * Reorder the list to be on the following form:
     * L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
     * You may not modify the values in the list's nodes. Only nodes themselves may be changed
     * Input: head = [1,2,3,4]
     * Output: [1,4,2,3]
     * */
    public static void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }

        // Step 1: Find the middle of the linked list
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Step 2: Reverse the second half of the linked list
        ListNode prev = null, curr = slow, temp;
        while (curr != null) {
            temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }

        // Step 3: Merge the two halves of the linked list
        ListNode first = head, second = prev;
        while (second.next != null) {
            temp = first.next;
            first.next = second;
            first = temp;

            temp = second.next;
            second.next = first;
            second = temp;
        }
    }

    /*
     * Remove Nth Node From End of List
     * Input: head = [1,2,3,4,5], n = 2
     * Output: [1,2,3,5]
     */

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        final ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;
        ListNode slow = dummyNode;
        ListNode fast = dummyNode;
        while (n > 0) {
            fast = fast.next;
            n--;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummyNode.next;
    }

    /*
     * Input: l1 = [2,4,3], l2 = [5,6,4]
     * Output: [7,0,8]
     * Explanation: 342 + 465 = 807.
     * */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int parity = 0;
        ListNode dummy = new ListNode(-1);
        ListNode result = dummy;
        while (l1 != null || l2 != null) {
            int sum = parity;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }

            result.next = new ListNode(sum % 10);
            result = result.next;
            parity = sum / 10;
        }
        if (parity > 0) {
            result.next = new ListNode(parity);
        }

        return dummy.next;
    }

    /*
     * Input: lists = [[1,4,5],[1,3,4],[2,6]]
     * Output: [1,1,2,3,4,4,5,6]
     * Explanation: The linked-lists are:
     * [
     *  1->4->5,
     *  1->3->4,
     *  2->6
     * ]
     * merging them into one sorted list:
     * 1->1->2->3->4->4->5->6
     *
     * */
    public static ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (final ListNode list : lists) {
            ListNode node = list;
            while (node != null) {
                queue.add(node.val);
                node = node.next;
            }
        }
        ListNode head = new ListNode(-1);
        ListNode dummy = head;
        while (!queue.isEmpty()) {
            head.next = new ListNode(queue.poll());
            head = head.next;
        }
        return dummy.next;
    }

    /*
     * Given the root of a binary tree, invert the tree, and return its root.
     * Input: root = [4,2,7,1,3,6,9]
     * Output: [4,7,2,9,6,3,1]
     * */
    public static TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode tmp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(tmp);
        return root;
    }

    /*
     * Given the root of a binary tree, return its maximum depth.
     * A binary tree's maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
     * Input: root = [3,9,20,null,null,15,7]
     * Output: 3
     * */
    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left, right) + 1;
    }

    /*
     * Given the root of a binary tree, return the length of the diameter of the tree.
     * The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.
     * The length of a path between two nodes is represented by the number of edges between them.
     * Input: root = [1,2,3,4,5]
     * Output: 3
     * Explanation: 3 is the length of the path [4,2,1,3] or [5,2,1,3].
     * */
    private static int diameter = 0;

    public static int diameterOfBinaryTree(TreeNode root) {
        maxHeight(root);
        return diameter;
    }

    private static int maxHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int left = maxHeight(node.left);
        int right = maxHeight(node.right);
        diameter = Math.max((left + right), diameter);

        return 1 + Math.max(left, right);
    }

    /*
     * Given a binary tree, determine if it is height-balanced
     * Input: root = [3,9,20,null,null,15,7]
     * Output: true
     * */
    public static boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        // Check if the current node is balanced and its subtrees are balanced
        return Math.abs(leftHeight - rightHeight) <= 1 && isBalanced(root.left) && isBalanced(root.right);
    }

    private static int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /*
     * Given the roots of two binary trees p and q, write a function to check if they are the same or not.
     * Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.
     * Input: p = [1,2,3], q = [1,2,3]
     * Output: true
     * */
    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return (p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right));
    }

    /*
     * Input: root = [3,4,5,1,2], subRoot = [4,1,2]
     * Output: true
     * */
    public static boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null) {
            return false;
        }

        if (isSameTree(root, subRoot)) {
            return true;
        }

        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    /*
     * Given the root of a binary tree, return the level order traversal of its nodes' values. (i.e., from left to right, level by level).
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[3],[9,20],[15,7]]
     * */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> vals = new ArrayList<>();
            result.add(vals);
            for (int i = 0; i < size; i++) {
                final TreeNode node = queue.poll();
                vals.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return result;
    }

    /*
     * Given the root of a binary tree,
     *  imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.
     * Input: root = [1,2,3,null,5,null,4]
     * Output: [1,3,4]
     * */
    public static List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                final TreeNode node = queue.poll();
                if (i == size - 1) {
                    result.add(node.val);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return result;
    }

    public static class KthLargest {

        private PriorityQueue<Integer> queue;
        private int k;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            this.queue = new PriorityQueue<>();
            Arrays.stream(nums).forEach(num -> this.queue.add(num));
            while (queue.size() > k) {
                queue.poll();
            }
        }

        public int add(int val) {
            queue.add(val);
            if (queue.size() > k) {
                queue.poll();
            }
            return queue.peek();
        }
    }

    /*
     * You are given an array of integers stones where stones[i] is the weight of the ith stone.
     * We are playing a game with the stones. On each turn, we choose the heaviest two stones and smash them together.
     *  Suppose the heaviest two stones have weights x and y with x <= y. The result of this smash is:
     * If x == y, both stones are destroyed, and
     * If x != y, the stone of weight x is destroyed, and the stone of weight y has new weight y - x.
     * At the end of the game, there is at most one stone left.
     * Return the weight of the last remaining stone. If there are no stones left, return 0.
     *
     * Input: stones = [2,7,4,1,8,1]
     * Output: 1
     * */
    public static int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        Arrays.stream(stones).forEach(queue::add);
        while (queue.size() > 1) {
            final Integer num1 = queue.poll();
            final Integer num2 = queue.poll();
            if (!Objects.equals(num2, num1)) {
                queue.add(Math.abs(num1 - num2));
            }
        }
        return queue.isEmpty() ? 0 : queue.poll();
    }

    /*
     * Given an integer array nums and an integer k, return the kth largest element in the array.
     * Note that it is the kth largest element in the sorted order, not the kth distinct element.
     * Can you solve it without sorting?
     * Example 1:
     * Input: nums = [3,2,1,5,6,4], k = 2
     * Output: 5
     * Example 2:
     * Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
     * Output: 4
     * */
    public static int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    /*
     * Given an integer array nums of unique elements, return all possible subsets (the power set).
     * The solution set must not contain duplicate subsets. Return the solution in any order.
     * Example 1:
     * Input: nums = [1,2,3]
     * Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     * Example 2:
     * Input: nums = [0]
     * Output: [[],[0]]
     * */

    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        dfsToGetSubsets(nums, 0, result, new ArrayList<>());
        return result;
    }

    private static void dfsToGetSubsets(int[] nums, int index, final List<List<Integer>> result, final ArrayList<Integer> currentList) {
        result.add(new ArrayList<>(currentList));
        for (int i = index; i < nums.length; i++) {
            currentList.add(nums[i]);
            dfsToGetSubsets(nums, i + 1, result, currentList);
            currentList.remove(currentList.size() - 1);
        }
    }

    /*
     * Given an array of distinct integers candidates and a target integer target, return a list of all unique combinations of candidates
     * where the chosen numbers sum to target. You may return the combinations in any order.
     * The same number may be chosen from candidates an unlimited number of times. Two combinations are unique if the frequency
     * of at least one of the chosen numbers is different.
     * The test cases are generated such that the number of unique combinations that sum up to target is less than 150 combinations for the given input.
     * Example 1:
     * Input: candidates = [2,3,6,7], target = 7
     * Output: [[2,2,3],[7]]
     * Explanation:
     * 2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
     * 7 is a candidate, and 7 = 7.
     * These are the only two combinations.
     * */
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        dfsCombinationSum(result, candidates, target, 0, new ArrayList<>());
        return result;
    }

    private static void dfsCombinationSum(final List<List<Integer>> result, final int[] candidates, final int target,
                                          final int index, final ArrayList<Integer> currentList) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            result.add(new ArrayList<>(currentList));
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            int num = candidates[i];
            currentList.add(num);
            int rem = target - num;
            dfsCombinationSum(result, candidates, rem, i, currentList);
            currentList.remove(currentList.size() - 1);
        }
    }

    /*
     * Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.
     * Example 1:
     * Input: nums = [1,2,3]
     * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     * Example 2:

     * Input: nums = [0,1]
     * Output: [[0,1],[1,0]]
     * Example 3:

     * Input: nums = [1]
     * Output: [[1]]
     * */
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        dfsPermute(nums, result, new ArrayList<>(), new boolean[nums.length]);
        return result;
    }

    private static void dfsPermute(int[] nums, List<List<Integer>> result, List<Integer> currentList, boolean[] visited) {
        if (currentList.size() == nums.length) {
            result.add(new ArrayList<>(currentList));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            currentList.add(nums[i]);
            dfsPermute(nums, result, currentList, visited);
            visited[i] = false;
            currentList.remove(currentList.size() - 1);
        }
    }

    /*
     * Given an integer array nums that may contain duplicates, return all possible subsets(the power set).
     * The solution set must not contain duplicate subsets. Return the solution in any order.
     * Example 1:
     * Input: nums = [1,2,2]
     * Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]
     * */
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        dfsWithDup(nums, result, 0, new ArrayList<>());
        return result;
    }

    private static void dfsWithDup(final int[] nums, final List<List<Integer>> result, final int index, final ArrayList<Integer> currentList) {
        result.add(new ArrayList<>(currentList));
        for (int i = index; i < nums.length; i++) {
            if (i == index || nums[i - 1] != nums[i]) {
                int num = nums[i];
                currentList.add(num);
                dfsWithDup(nums, result, i + 1, currentList);
                currentList.remove(currentList.size() - 1);
            }
        }
    }

    /*
    * Given a collection of candidate numbers (candidates) and a target number (target),
    * find all unique combinations in candidates where the candidate numbers sum to target.
    * Each number in candidates may only be used once in the combination.
    * Note: The solution set must not contain duplicate combinations.
    * Example 1:
    * Input: candidates = [10,1,2,7,6,1,5], target = 8
    * Output:
    * [
        [1,1,6],
        [1,2,5],
        [1,7],
        [2,6]
      ]
    * */
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        dfsCombinationSum2(result, candidates, target, 0, new ArrayList<>());
        return result;
    }

    private static void dfsCombinationSum2(final List<List<Integer>> result, final int[] candidates, final int target, final int index, final ArrayList<Integer> current) {
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        if (target < 0) {
            return;
        }

        for (int i = index; i < candidates.length; i++) {
            if (i != index && candidates[i] == candidates[i - 1]) {
                continue;
            }
            if ((target - candidates[i]) < 0) {
                return;
            }

            current.add(candidates[i]);
            dfsCombinationSum2(result, candidates, target - candidates[i], i + 1, current);
            current.remove(current.size() - 1);
        }
    }

    /*
     * Given an m x n grid of characters board and a string word, return true if word exists in the grid.
     * The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring.
     * The same letter cell may not be used more than once.
     * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
     * Output: true
     * */
    public static boolean wordExist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if ((board[i][j] == word.charAt(0)) && (searchWord(board, 0, word, i, j))) {
                    return true;

                }
            }
        }
        return false;
    }

    private static boolean searchWord(final char[][] board, final int index, final String word, final int i, final int j) {
        if (index == word.length()) {
            return true;
        }
        if (i >= 0 && i <= board.length - 1 && j >= 0 && j <= board[0].length - 1 && board[i][j] == word.charAt(index)) {
            char temp = board[i][j];
            board[i][j] = ' ';
            final boolean match = searchWord(board, index + 1, word, i + 1, j) ||
                searchWord(board, index + 1, word, i - 1, j) ||
                searchWord(board, index + 1, word, i, j + 1) ||
                searchWord(board, index + 1, word, i, j - 1);
            board[i][j] = temp;
            return match;
        }
        return false;
    }

    /*
     * Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.
     * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
     * You may assume all four edges of the grid are all surrounded by water.
     * Example 1:
     * Input: grid = [
     * ["1","1","1","1","0"],
     * ["1","1","0","1","0"],
     * ["1","1","0","0","0"],
     * ["0","0","0","0","0"]
     * ]
     * Output: 1
     * */
    public static int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    count += islandDfs(grid, i, j);
                }
            }
        }
        return count;
    }

    private static int islandDfs(final char[][] grid, final int i, final int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == '0') {
            return 0;
        }

        grid[i][j] = '0';
        islandDfs(grid, i + 1, j);
        islandDfs(grid, i - 1, j);
        islandDfs(grid, i, j + 1);
        islandDfs(grid, i, j - 1);
        return 1;
    }

    public static Node cloneGraph(Node node) {
        return cloneGraphDfs(node, new HashMap<>());
    }

    private static Node cloneGraphDfs(final Node node, final HashMap<Integer, Node> visited) {
        if (node == null) {
            return null;
        }
        visited.put(node.val, new Node(node.val));
        for (final Node neighbor : node.neighbors) {
            if (!visited.containsKey(neighbor.val)) {
                cloneGraphDfs(neighbor, visited);
            }
            visited.get(node.val).neighbors.add(visited.get(neighbor.val));
        }
        return visited.get(node.val);
    }

    /*
     * You are climbing a staircase. It takes n steps to reach the top.
     * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
     * Input: n = 2
     * Output: 2
     * Explanation: There are two ways to climb to the top.
     * 1. 1 step + 1 step
     * 2. 2 steps
     * */
    public static int climbStairs(int n) {
        if (n == 0) {
            return 0;
        }
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 2; i < n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n - 1];
    }

    /*
     * You are given an integer array cost where cost[i] is the cost of ith step on a staircase.
     * Once you pay the cost, you can either climb one or two steps.
     * You can either start from the step with index 0, or the step with index 1.
     * Return the minimum cost to reach the top of the floor.
     *
     * Example 1:
     * Input: cost = [10,15,20]
     * Output: 15
     * Explanation: You will start at index 1.
     * - Pay 15 and climb two steps to reach the top.
     * The total cost is 15.
     * */
    public static int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        for (int i = 2; i < n; i++) {
            cost[i] += Math.min(cost[i - 1], cost[i - 2]);
        }
        return Math.min(cost[n - 1], cost[n - 2]);
    }

    public static class Node {

        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
}
