package com.dgit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dgit.domain.SampleVO;

@RequestMapping("/sample")
@RestController 
public class SampleController {
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public String sayHello(){ //ex02/sample/hello 주소
		return "hello";
	}
	
	@RequestMapping("/sendVO")
	public SampleVO sendVO(){
		SampleVO vo = new SampleVO();
		vo.setFirstName("매미");
		vo.setLastName("양");
		vo.setMno(123);
		return vo;
	}
	
	@RequestMapping("/sendList")
	public List<SampleVO> sendList() {
		List<SampleVO> list1 = new ArrayList<>();
		
		for(int i = 0; i<=10; i++){
			SampleVO vo = new SampleVO();
			vo.setFirstName("매미"+i);
			vo.setLastName("양");
			vo.setMno(i);
			list1.add(vo);
		}
		return list1;
	}
	
	@RequestMapping("/sendMap")
	public Map<Integer, SampleVO> sendMap() {
		Map<Integer, SampleVO> map = new HashMap<>();
		
		for(int i = 0; i<=10; i++){
			SampleVO vo = new SampleVO();
			vo.setFirstName("나무"+i);
			vo.setLastName("양");
			vo.setMno(i);
			map.put(i, vo);
		}
		return map;
	}
	
	//에러보내기
	@RequestMapping("/sendErrorAuth")
	public ResponseEntity<Void> sendListAuth() {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//400 error
	}
	
	@RequestMapping("/sendFail")
	public ResponseEntity<String> sendFail(){
		return new ResponseEntity<String>("failed", HttpStatus.BAD_REQUEST);
	}
	
	//객체보내기
	@RequestMapping("/sendErrorList")
	public ResponseEntity<List<SampleVO>> sendErrorList(){
		List<SampleVO> list1 = new ArrayList<>();
		
		for(int i = 0; i<=10; i++){
			SampleVO vo = new SampleVO();
			vo.setFirstName("매미"+i);
			vo.setLastName("양");
			vo.setMno(i);
			list1.add(vo);
		}
		return new ResponseEntity<>(list1, HttpStatus.NOT_FOUND);//404에러
	}
}
