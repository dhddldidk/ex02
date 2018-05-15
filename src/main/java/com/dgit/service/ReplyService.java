package com.dgit.service;

import java.util.List;

import com.dgit.domain.Criteria;
import com.dgit.domain.ReplyVO;

public interface ReplyService {
	public void addReply(ReplyVO vo) throws Exception;
	public List<ReplyVO> listReply(int bno) throws Exception;
	public void modifyReply(ReplyVO vo) throws Exception;
	public void removeReply(int rno) throws Exception;
	
	//덧글 페이징
	public List<ReplyVO> listPageReply(int bno, Criteria cri) throws Exception;
	public int count(int bno) throws Exception;
}
