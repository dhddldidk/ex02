package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgit.domain.Criteria;
import com.dgit.domain.ReplyVO;
import com.dgit.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDAO dao;
	
	@Override
	public void addReply(ReplyVO vo) throws Exception {
		dao.create(vo);
	}

	@Override
	public List<ReplyVO> listReply(int bno) throws Exception {
		
		return dao.list(bno);
	}

	@Override
	public void modifyReply(ReplyVO vo) throws Exception {
		dao.update(vo);

	}

	@Override
	public void removeReply(int rno) throws Exception {
		dao.delete(rno);

	}

	@Override
	public List<ReplyVO> listPageReply(int bno, Criteria cri) throws Exception {
		
		return dao.listPage(bno, cri);
	}

	@Override
	public int count(int bno) throws Exception {
		
		return dao.count(bno);
	}

}
