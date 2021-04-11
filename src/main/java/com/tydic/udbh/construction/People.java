package com.tydic.udbh.construction;

public class People {
    private String name;
    private int age;

    private static  String a = father();

    public static String father()
    {
        System.out.println("父类静态变量");
        return "父类静态变量";
    }
    static {
        System.out.println("父类静态块");
    }
    {
        System.out.println("父类非静态块");
    }
    public People(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public People(){
        System.out.println("父类构造方法");
    }
    public void speak(){
        System.out.println("我是人");
    }


}
