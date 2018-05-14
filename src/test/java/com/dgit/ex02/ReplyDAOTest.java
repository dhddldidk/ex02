package com.dgit.ex02;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.ReplyVO;
import com.dgit.persistence.ReplyDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class ReplyDAOTest {

	@Autowired
	ReplyDAO dao;
	
	//@Test
	public void test1Create() throws Exception {
		ReplyVO vo = new ReplyVO();
		vo.setBno(8);
		vo.setReplytext("처음 다는 덧글입니다.");
		vo.setReplyer("사과씨");
		
		dao.create(vo);
	}
	
	//@Test
	public void test2List() throws Exception {
		List<ReplyVO> list = dao.list(8);
		
		for(ReplyVO vo : list){
		System.out.println("검색된 list : "+list);
		}
		
	}
	
	//@Test
	public void test3Update() throws Exception {
		ReplyVO vo = new ReplyVO();
		vo.setRno(1);
		vo.setReplytext("수정한 덧글입니다.");
		dao.update(vo);
	}
	
	@Test
	public void test4Delete() throws Exception {
		dao.delete(2);
	}
}
