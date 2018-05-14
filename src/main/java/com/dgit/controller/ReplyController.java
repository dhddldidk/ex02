package com.dgit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dgit.domain.ReplyVO;
import com.dgit.service.ReplyService;

@RequestMapping("/replies")
@RestController
public class ReplyController {

	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);

	@Autowired
	ReplyService service;

	// 주소 : /ex02/replies - post
	@RequestMapping(value = "", method = RequestMethod.POST) 

	// @RequestBody는 "{bno:12, replytext:덧글, replyer:user00}" 이렇게 String 으로 오는 데이터를
	// body안에 실려서 옴 그래서 @RequestBody를 반드시 써줘야 함
	
	// 단, 위에 json이 ReplyVO 안에 get, set 이 있어야 함
	public ResponseEntity<String> register(@RequestBody ReplyVO vo) { // 아니면 Map<String, Object>해도 됨
		ResponseEntity<String> entity = null;
		logger.info(vo.toString());

		try {
			service.addReply(vo);
			entity = new ResponseEntity<>("success", HttpStatus.OK); // 200
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);// 400
		}
		return entity;
	}
	
	
	//ex02/replies/all?bno=6이렇게 가야 하지만
	//ex02/replies/all/6 이렇게 url주소로 바로 보낼 수 있음
	@RequestMapping(value="/all/{bno}", method=RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") int bno){
		//보내는 변수에 이름이 있다는 거를 알려줌@PathVariable("bno") -> ex02/replies/all/6
		ResponseEntity<List<ReplyVO>> entity = null;
		logger.info("bno : "+bno);
		
		try{
			List<ReplyVO> list = service.listReply(bno);
			entity = new ResponseEntity<>(list, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);//error일 때는 객체를 못보내니까 BAD_REQUEST
		}
		return entity;
	}
	
	//수정 put, patch, url주소를 /{rno}일 경우에 
	@RequestMapping(value="/{rno}", method={RequestMethod.PUT, RequestMethod.PATCH})
	//method 방식을 2개로 할 때는 배열로 감싸서 2개를 받을 수도 있음
	public ResponseEntity<String> update(@PathVariable("rno") int rno, @RequestBody ReplyVO vo) {
		ResponseEntity<String> entity = null;
		logger.info("rno : "+ rno);
		logger.info(vo.toString());
		
		try {
			vo.setRno(rno);
			service.modifyReply(vo);
			entity = new ResponseEntity<>("success", HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>("fail", HttpStatus.OK);
		}
		return entity;
	}
	
	
	@RequestMapping(value="/{rno}", method=RequestMethod.DELETE)
	public ResponseEntity<String> remove(@PathVariable("rno") int rno){
		ResponseEntity<String> entity = null;
		logger.info("rno : "+rno);
		
		try {
			service.removeReply(rno);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}
