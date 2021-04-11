package com.tydic.udbh.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented                             //说明该注解将被包含在javadoc中
public @interface SysLog {
	
	 public String description() ; //Spring组件名称。如果用户未提供名称，则默认为“”。
	//当要命名bean时，主要使用它。例如，动物类有2个实例，因此我无法直接对其进行自动装配，因此我需要命名它们，然后使用命名的bean自动装配。
	   //public String name();
	  // public String value();//原文出自【易百教程】，商业转载请联系作者获得授权，非商业请保留原文链接：https://www.yiibai.com/javareflect/javareflect_method_getannotation.html


}
