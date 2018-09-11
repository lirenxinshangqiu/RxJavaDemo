package com.ls.rxjava;

/**
 * Created by ls on 18/6/15.
 */
public class StringDemo {

    public static void equalsTest(){
        String str1 = "abc";
        String str2 ="abc";//已经存放在了字符串缓冲池中，就不会再创建新的对象，此时str1和str2是同一个对象
        System.out.println(str1 == str2);
        System.out.println(str1.equals(str2));
        String str3 = new String("abc");//明确告知jvm要创建一个新的对象
        System.out.println(str1 == str3);
        System.out.println(str1.equals(str3));
    }
}
