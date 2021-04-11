package com.tydic.udbh.stream;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamTest1 {
    public static void main(String[] args) {
        List<TestStreamModel> list = Lists.newArrayList();
        TestStreamModel testStreamModel = new  TestStreamModel();
        testStreamModel.setId(2);/*主键*/
        testStreamModel.setName("张三");/*姓名*/
        testStreamModel.setClasses(1);/*班级*/
        testStreamModel.setGrade(1);/*年级*/
        testStreamModel.setScore(80);/*成绩*/
        list.add(testStreamModel);

       TestStreamModel testStreamModel1 = new TestStreamModel();
        testStreamModel1.setId(1);
        testStreamModel1.setName("李四");
        testStreamModel1.setClasses(1);
        testStreamModel1.setGrade(1);
        testStreamModel1.setScore(60);
        list.add(testStreamModel1);

        TestStreamModel testStreamModel2 = new TestStreamModel();
        testStreamModel2.setId(3);
        testStreamModel2.setName("王二麻子");
        testStreamModel2.setClasses(2);
        testStreamModel2.setGrade(1);
        testStreamModel2.setScore(90);
        list.add(testStreamModel2);

        TestStreamModel testStreamModel3 = new TestStreamModel();
        testStreamModel3.setId(4);
        testStreamModel3.setName("王五");
        testStreamModel3.setClasses(2);
        testStreamModel3.setGrade(1);
        testStreamModel3.setScore(59.5);
        list.add(testStreamModel3);

        TestStreamModel testStreamModel4 = new TestStreamModel();
        testStreamModel4.setId(8);
        testStreamModel4.setName("小明");
        testStreamModel4.setClasses(1);
        testStreamModel4.setGrade(2);
        testStreamModel4.setScore(79.5);
        list.add(testStreamModel4);

        TestStreamModel testStreamModel5 = new TestStreamModel();
        testStreamModel5.setId(5);
        testStreamModel5.setName("小红");
        testStreamModel5.setClasses(2);
        testStreamModel5.setGrade(2);
        testStreamModel5.setScore(99);
        list.add(testStreamModel5);

        TestStreamModel testStreamModel6 = new TestStreamModel();
        testStreamModel6.setId(7);
        testStreamModel6.setName("小黑");
        testStreamModel6.setClasses(2);
        testStreamModel6.setGrade(2);
        testStreamModel6.setScore(45);
        list.add(testStreamModel6);

        TestStreamModel testStreamModel7 = new TestStreamModel();
        testStreamModel7.setId(6);
        testStreamModel7.setName("小白");
        testStreamModel7.setClasses(1);
        testStreamModel7.setGrade(2);
        testStreamModel7.setScore(88.8);
        list.add(testStreamModel7);

        TestStreamModel testStreamModel8 = new TestStreamModel();
        testStreamModel8.setId(6);
        testStreamModel8.setName("小白");
        testStreamModel8.setClasses(1);
        testStreamModel8.setGrade(2);
        testStreamModel8.setScore(88.8);
        list.add(testStreamModel8);

        /*分组， 按照字段中某个属性将list分组*/
        Map<Integer, List<TestStreamModel>> map = list.stream().collect(Collectors.groupingBy(t -> t.getGrade()));
        System.out.println("按年级分组"+map);
        /*然后再对map处理，这样就方便取出自己要的数据*/
        for(Map.Entry<Integer, List<TestStreamModel>> entry : map.entrySet()){
            System.out.println("key:"+entry.getKey());
            System.out.println("value:"+entry.getValue());
        }

//     list.sort(Comparator.comparing(TestStreamModel ::getGrade,Comparator.nullsFirst(String)) ));



    }
}
