package com.dgit.persistence;

import java.util.List;

import com.dgit.domain.Criteria;
import com.dgit.domain.ReplyVO;

public interface ReplyDAO {
	public List<ReplyVO> list(int bno) throws Exception;
	
	public void create(ReplyVO vo) throws Exception;
	
	public void update(ReplyVO vo) throws Exception;
	
	public void delete(int rno) throws Exception;
	
	
	//답글 페이징
	public List<ReplyVO> listPage(int bno, Criteria cri) throws Exception;//게시물 번호, 몇개 보여줄껀지
	
	public int count(int bno) throws Exception;
}
