package com.dgit.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SampleInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("pre handler.............................");
		
		HandlerMethod method = (HandlerMethod)handler;
		Method methodObj = method.getMethod();
		
		//실행되고 있는 메소드
		System.out.println("Bean : "+method.getBean());
		
		//실행 하려고 하는 메소드
		System.out.println("Method : "+methodObj);
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("post handler.............................");
		
		//result 키가 있는지 없는지 확인할 수 있음(Controller에서 넘어오는 )
		Object result = modelAndView.getModel().get("result");
		
		
		//doA command를 실행시켜서 넘어오는 키가 있으면 그걸 실어서 
		//jsp로 보내줌
		if(result !=null){
			request.getSession().setAttribute("sessionResult", result);
			response.sendRedirect("doA");
		}
	}

}
