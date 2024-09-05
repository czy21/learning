package com.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.math.BigDecimal;
import java.util.*;


public class StringTest {

    /*
     * String 对象布局 及值的内存占用情况
     * .class底层使用unicode编码 英文字符占用1b 汉字占用2b
     */
    @Test
    public void test1() {
        String str1 = "y";
        System.out.println(StringUtils.join("ClassLayout info: ", ClassLayout.parseInstance(str1).toPrintable()));
        System.out.println(StringUtils.join("GraphLayout info: ", GraphLayout.parseInstance(str1).toPrintable()));
    }

    /*
     * String 定义的两种方式
     */
    @Test
    public void test2() {
        String str1 = "ni"; //存入常量池中
        String str2 = new String("ni"); //存入堆中
        System.out.println(str1 == str2);
    }


    public boolean contains(String str, String search) {
        for (int i = 0; i < str.length(); i++) {
            StringBuilder ret = new StringBuilder();
            for (int j = i; j < str.length(); j++) {
                ret.append(str.charAt(j));
                if (ret.length() == search.length() && ret.toString().equals(search)) {
                    return true;
                }
            }

        }
        return false;
    }

    // 题一
    @Test
    public void f1() {
        boolean a = contains("abcde", "bc");
        System.out.println(a);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Node {
        private String value;
        private List<Node> children = new ArrayList<>();

        public Node(String value) {
            this.value = value;
        }
    }

    public void deleteNode(Node node, String value) {
        Iterator<Node> iterator = node.getChildren().iterator();
        while (iterator.hasNext()) {
            Node n = iterator.next();
            if (!n.getValue().equals(value)) {
                if (n.getChildren().isEmpty()) {
                    iterator.remove();
                } else {
                    deleteNode(n, value);
                }
            }
        }
    }

    // 题二
    @Test
    public void f2() {
        Node root = new Node();
        Node c = new Node("c");
        List<Node> bList = new ArrayList<>();
        bList.add(c);
        Node b = new Node("b", bList);
        Node a = new Node("a");
        List<Node> gList = new ArrayList<>();
        gList.add(new Node("b"));
        Node g = new Node("g", gList);
        Node b1 = new Node("b");
        List<Node> dList = new ArrayList<>();
        dList.add(g);
        dList.add(b1);
        Node d = new Node("d", dList);
        root.children.add(a);
        root.children.add(b);
        root.children.add(d);
        deleteNode(root, "b");
        System.out.println("a");
    }

    static List<String> list = Arrays.asList("+", "-", "*", "/", "(", ")");

    private static String calculate(String expresion) {
        Stack<String> stack = new Stack<>();
        // 去括号
        for (int i = 0; i < expresion.length(); i++) {
            String character = String.valueOf(expresion.charAt(i));
            if (character.equals(" ")) {
                continue;
            }
            if (character.equals(")")) {
                // 如果遇到右括号，把栈中的数据一个一个取出来放入另外一个栈中，直到遇见左括号
                Stack<String> sb = new Stack<>();
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    sb.push(stack.pop());
                }
                stack.pop();// 删除栈中的左括号
                stack.push(calculate(sb));// 将括号里面的内容计算之后重新放入栈中

            } else {
                // 没有遇到右括号
                if (stack.isEmpty()) {
                    stack.push(character);
                    continue;
                }
                if (list.contains(character)) {
                    stack.push(character); // 如果是运算符或者左括号，直接入栈
                } else {
                    // 如果是数字或者小数点，需要和之前入栈的数字拼接
                    if (list.contains(stack.peek())) {
                        stack.push(character); // 如果上一个入栈的不是数字或者小数点，直接入栈
                    } else {
                        // 如果上一个入栈的是数字或者小数点，先出栈，再拼接，最后入栈
                        StringBuilder sb = new StringBuilder();
                        sb.append(stack.pop());
                        sb.append(character);
                        stack.push(sb.toString());
                    }
                }
            }
        }
        // 计算最后的结果
        if (stack.size() == 1) {// 计算完括号里面的，如果栈中只剩下一个元素
            return stack.pop();
        } else {
            // 计算完括号里面的，栈中还有多个元素，继续计算
            // 但是此时栈中的元素是正序排列的，需要变成倒序再计算
            Stack<String> last = new Stack<String>();
            while (!stack.isEmpty()) {
                last.push(stack.pop());
            }
            return calculate(last);
        }
    }

    private static String calculate(Stack<String> exp) {
        ArrayList<String> arrayList = new ArrayList<>();
        // 先算乘除法
        while (!exp.isEmpty()) {
            if ("*".equals(exp.peek()) || "/".equals(exp.peek())) {
                String fuhao = exp.pop();
                String number = exp.pop();
                String last = arrayList.remove(arrayList.size() - 1);
                if (fuhao.equals("*")) {
                    arrayList.add(new BigDecimal(last).multiply(new BigDecimal(number)).toString());
                } else {
                    arrayList.add(new BigDecimal(last).divide(new BigDecimal(number), 12, BigDecimal.ROUND_HALF_EVEN).toString());
                }
            } else {
                arrayList.add(exp.pop());
            }
        }
        // 再算加减法
        BigDecimal b = new BigDecimal(arrayList.remove(0));
        Iterator<String> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (next.equals("+")) {
                b = b.add(new BigDecimal(iterator.next()));
            } else if (next.equals("-")) {
                b = b.subtract(new BigDecimal(iterator.next()));
            }
        }
        return b.toString();
    }

    // 题三，有参考
    @Test
    public void f3() {
        String ret = calculate("(1+2)*1.2-5/2");
        System.out.println(ret);
    }
}
