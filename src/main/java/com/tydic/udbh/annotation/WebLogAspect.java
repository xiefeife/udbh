package com.tydic.udbh.annotation;


import com.alibaba.fastjson.JSON;
import com.tydic.udbh.entity.Operation;
import com.tydic.udbh.entity.User;
import com.tydic.udbh.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class WebLogAspect {
	

	
	DateUtil dateUtil=new DateUtil();
	
	//本地日志信息对象
	private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
	
	  @Pointcut("@annotation(com.tydic.udbh.annotation.SysLog)")
	//@Pointcut("execution(public * com.tomlive.controller..*(..))") // 切入点描述 这个是controller包的切入点
	public void controllerLog() {
	}// 签名，可以理解成这个切入点的一个名称


	@Before("controllerLog()") // 在切入点的方法run之前要干的
	public void logBeforeController(JoinPoint joinPoint) throws Throwable {

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();// 这个RequestContextHolder是Springmvc提供来获得请求的东西
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		System.out.println(request.getLocalAddr());
		System.out.println(request.getSession());
		System.out.println();
		// 打印请求的路径
		logger.info("################URL : " + request.getRequestURL().toString());
		// 打印请求的方法
		logger.info("################HTTP_METHOD : " + request.getMethod());
		// 打印请求的真实地址
		logger.info("################IP : " + request.getRemoteAddr());

		logger.info("################THE ARGS OF THE CONTROLLER : " + Arrays.toString(joinPoint.getArgs()));

		// 下面这个getSignature().getDeclaringTypeName()是获取包+类名的
		// 然后后面的joinPoint.getSignature.getName()获取了方法名
		logger.info("################CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
				+ joinPoint.getSignature().getName());
		// logger.info("################TARGET: " +
		// joinPoint.getTarget());//返回的是需要加强的目标类的对象
		// logger.info("################THIS: " +
		// joinPoint.getThis());//返回的是经过加强后的代理类的对象

		logger.info("################IP : " + getControllerMethodDescription(joinPoint));
		Operation op=new Operation();
		User user=(User)request.getSession().getAttribute("user_session");

		log.info("登录的用户{}"+user);
		String  description= getControllerMethodDescription(joinPoint);//获取接口上注解的内容
		op.setOperationIp(request.getRemoteAddr());//设置请求的ip
		log.info("客户端请求的ip是{}",request.getRemoteAddr());
		op.setOperationTime(dateUtil.createDate());//获取当前操作的时间
		op.setParam(request.getMethod());//获取请求方法的方式
		op.setOperationContent(description);//获取方法上面注解的描述  
	}

	/**
	  * 获取注解中对方法的描述信息 用于Controller层注解
	 * @param joinPoint 切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		System.out.println("targetName得到的是完整的类路径" + targetName);
		String methodName = joinPoint.getSignature().getName();
		System.out.println("methodName得到的是操作的方法名" + methodName);
		Object[] arguments = joinPoint.getArgs();
		System.out.println("arguments得到的是一个数组" + JSON.toJSONString(arguments));
		Class targetClass = Class.forName(targetName);
		
		// 得到操作类里面所有的方法
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName) && null!=method.getAnnotation(SysLog.class)) {
				description = method.getAnnotation(SysLog.class).description();
				break;
			}
		}
		return description;
	}

	
}
