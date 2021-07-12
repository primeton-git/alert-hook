package com.gaoya.mdm.webhook;

public class HelloBinaryTest {

    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(1));
        System.out.println(Integer.toBinaryString(2));
        System.out.println(Integer.toBinaryString(3));
        System.out.println(Integer.toBinaryString(4));
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));

        System.out.println();

        System.out.println(Integer.parseInt("1", 2)); // 二进制转换为十进制
        System.out.println(Integer.parseInt("10", 2)); // 二进制转换为十进制
        System.out.println(Integer.parseInt("11", 2)); // 二进制转换为十进制
        System.out.println(Integer.parseInt("100", 2)); // 二进制转换为十进制
        System.out.println(Integer.parseInt(Integer.toBinaryString(Integer.MAX_VALUE), 2)); // 二进制转换为十进制
        System.out.println(Integer.parseInt(Integer.toBinaryString(Integer.MAX_VALUE), 2) == Integer.MAX_VALUE); // 二进制转换为十进制
        System.out.println();

        System.out.println("\n");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 62; i++) {
            sb.append(1);
        }
        System.out.println(Long.parseLong(sb.toString(), 2));

    }

}
