package com.tydic.udbh.construction;

public class Student extends People {

    private String stuNo;
    static {
        System.out.println("子类静态块");
    }
    private static  String b = child();

    public static String child()
    {
        System.out.println("子类静态变量");
        return "子类静态变量";
    }

    public Student(String name, int age, String stuNo) {
        super(name, age);
        this.stuNo = stuNo;
    }
    public Student(String stuNo) {
        this.stuNo = stuNo;
    }
    public Student(){
        System.out.println("子类构造方法");
    }

    {
        System.out.println("子类非静态块");
    }
    @Override
    public void  speak(){
        System.out.println("我是学生");
    }


    public static void main(String[] args) {
        Student s1 = new Student();
        System.out.println("================================");
        Student s2 = new Student();
    }
}
