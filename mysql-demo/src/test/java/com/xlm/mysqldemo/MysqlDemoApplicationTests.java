package com.xlm.mysqldemo;

import com.xlm.mysqldemo.pojo.master.UserOperateLogDO;
import com.xlm.mysqldemo.pojo.slave.User;
import com.xlm.mysqldemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.annotation.Resource;
import javax.swing.*;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@SpringBootTest
class MysqlDemoApplicationTests {

    @Autowired
    private UserService userService;

    @Resource
    JtaTransactionManager transactionManager;

    @Autowired
    TransactionDefinition transactionDefinition;

    @Test
    //@Transactional(rollbackFor = Exception.class)
    void contextLoads() {
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        try {
            for (int i = 0; i < 10; i++) {
                User user = new User();
                user.setName("name"+i).setPassword("password"+i);
                userService.saveUser(user);
            }
            int a = 1/0;
            for (int i = 0; i < 10; i++) {
                UserOperateLogDO userOperateLogDO = new UserOperateLogDO();
                userOperateLogDO.setOperation("setOperation"+i).setUserId("useriD"+i);
                userService.saveUserLog(userOperateLogDO);
            }
            transactionManager.commit(transactionStatus);
        }catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(transactionStatus);
        }
    }

}

class Solution {
    public static int calPoints(String[] operations) {
        int[] ints = new int[operations.length];
        int index = 0;
        for (int i = 0; i < operations.length; i++) {
            switch (operations[i]) {
                case "C":
                    ints[--index] = 0;
                    break;
                case "D":
                    int curr = ints[--index];
                    ints[++index] = curr << 1;
                    index++;
                    break;
                case "+":
                    int tempIndex = index;
                    int current = ints[--tempIndex];
                    int preview = ints[--tempIndex];
                    ints[index++] = current + preview;
                    break;
                default:
                    ints[index++] = Integer.valueOf(operations[i]);
                    break;
            }
        }
        int reduce = Arrays.stream(ints).reduce(0, (a, b) -> a + b);
        return reduce;
    }


    public static boolean backspaceCompare(String s, String t) {
        return getResultString(s).equals(getResultString(t));
    }

    private static String getResultString(String str) {
        Stack<String> stack = new Stack<>();
        String[] split = str.split("");
        for (String string : split) {
            if ("#".equals(string) && !stack.isEmpty()) {
                stack.pop();
            } else {
                if (!"#".equals(string)) {
                    stack.push(string);
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : stack) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    public static String removeOuterParentheses(String s) {
        Stack<Character> stack = new Stack<>();
        int sign = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(s.charAt(i));
            }else if (s.charAt(i) == ')') {
                stack.pop();
            }

            if (stack.isEmpty()) {
                builder.append(s,sign + 1,i);
                sign = i + 1;
            }
        }
        return builder.toString();
    }

    public static String removeDuplicates(String s) {
        Stack<Character> sourceStack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (!sourceStack.isEmpty() && sourceStack.peek() == s.charAt(i)) {
                sourceStack.pop();
            } else {
                sourceStack.push(s.charAt(i));
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Character character : sourceStack) {
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }


    public static void main(String[] args) {
        //String[] operations = {"5","2","C","D","+"};
        //int i = calPoints(operations);
        //System.out.println("i = " + i);
        //String s = "y#fo##f", t = "y#f#o##f";
        //System.out.println("backspaceCompare(s,t) = " + backspaceCompare(s, t));

        //String string = "((()())(()))";
        //String removed = removeOuterParentheses(string);
        //String string = "abbaca";
        //String removed = removeDuplicates(string);
        //System.out.println(removed);
        //int[] ints = {10,1,1,6};
        //int[] ints1 = finalPrices(ints);
        //System.out.println(ints1);

        //String str = "abBAcC";
        //String s = makeGood(str);
        //System.out.println("s = " + s);

        //String[] strings = {"./","../","./"};
        //int operations = minOperations(strings);
        //System.out.println("operations = " + operations);


        //String string = "ABFCACDB";
        //int i = minLength(string);
        //System.out.println("i = " + i);
        //int[] array = {1,3,5,7};
        //int lengthOfLCIS = findLengthOfLCIS(array);
        //System.out.println("lengthOfLCIS = " + lengthOfLCIS);

        int[] array = {1,2,2,4};
        int[] errorNums = findErrorNums(array);
        System.out.println("errorNums = " + errorNums);

    }

    public static int[] finalPrices(int[] prices) {
        Stack<Integer> stack = new Stack<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < prices.length; i++) {
            while (!stack.isEmpty() && prices[i] <= stack.peek()) {
                Integer peek = stack.peek();
                map.put(i-1,peek-prices[i]);
                stack.pop();
            }
            stack.push(prices[i]);
        }
        for (int i = 0; i < prices.length; i++) {
            if (!map.containsKey(i)) {
                map.put(i,prices[i]);
            }
        }
        int[] ints = new int[prices.length];
        for (int i = 0; i < prices.length; i++) {
            ints[i] = map.getOrDefault(i, -1);
        }
        return ints;
    }

    public static String makeGood(String s) {
        if (s == null) {
            return null;
        }

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (!stack.isEmpty() && (Character.toUpperCase(stack.peek()) == Character.toUpperCase(s.charAt(i)) && stack.peek() != s.charAt(i)))  {
                stack.pop();
            }else {
                stack.push(s.charAt(i));
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Character character : stack) {
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }

    public static int minOperations(String[] logs) {
        if (logs.length == 0) {
            return 0;
        }
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < logs.length; i++) {
            if (!stack.isEmpty() && logs[i].equals("../")) {
                stack.pop();
            } else if (logs[i].equals("./")) {

            } else if (!logs[i].equals("../")) {
                stack.push(logs[i]);
            }
        }
        return stack.size();
    }

    public int countStudents(int[] students, int[] sandwiches) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int student : students) {
            map.put(student,map.getOrDefault(student,0) + 1);
        }
        int studentCount = students.length;
        for (int sandwich : sandwiches) {
            if (!map.containsKey(sandwich)) {
                return studentCount;
            }
            int num = map.get(sandwich);
            if (num == 0) {
                return studentCount;
            }else {
                map.put(sandwich,--num);
                studentCount--;
            }
        }
        return studentCount;
    }


    public static int minLength(String s) {
        if (s == null) {
            return 0;
        }

        Stack<String> stack = new Stack<>();
        String[] split = s.split("");
        for (String value : split) {
            if (!stack.isEmpty() && (value.equals("B") || value.equals("D"))) {
                if (stack.peek().equals("A") && value.equals("B")) {
                    stack.pop();
                      continue;
                }
                if (stack.peek().equals("C") && value.equals("D")) {
                    stack.pop();
                    continue;
                }
            }
            stack.push(value);
        }
        return stack.size();
    }

    public static int findLengthOfLCIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int max = 0;
        int counter = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i-1]) {
                counter++;
            }else {
                max = Math.max(counter,max);
                counter = 1;
            }
        }
        return Math.max(counter,max);
    }

    public static int[] findErrorNums(int[] nums) {
        int[] arr = new int[nums.length +1];
        for (int num : nums) {
            arr[num]++;
        }
        int a = 0, b = 0;
        for (int i = 1; i <= nums.length; i++) {
            if (arr[i] == 2) {
                a = i;
            }
            if (arr[i] == 0) {
                b = i;
            }
        }
        return new int[]{a,b};
    }

    public double findMaxAverage(int[] nums, int k) {
        if (nums.length < k) {
            return 0;
        }
        int start = 0;
        int end = k - 1;

        double maxSum = 0;
        double tempSum = 0;
        for (int i = start; i <= end; i++) {
            tempSum += nums[i];
        }
        maxSum = Math.max(tempSum, maxSum);

        for (int i = end + 1; i < nums.length; i++) {
            tempSum = tempSum - nums[start];
            start++;
            tempSum = tempSum + nums[i];
            maxSum = Math.max(tempSum, maxSum);
        }
        return maxSum / k;
    }

}
