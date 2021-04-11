package com.tydic.udbh.construction;

public class Son {
    private  String name;

      static {
          System.out.println("静态代码块1");
      }
      static  {
          System.out.println("静态代码块2");
      }
    public Son(){
        System.out.println("无参构造方法");
    }
    public Son(String name){
          System.out.println("有参构造方法");
    }

    public static void main(String[] args) {
        Son son = new Son();
        System.out.println("============================");
    }

}
