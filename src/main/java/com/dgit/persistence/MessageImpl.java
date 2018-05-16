package com.dgit.persistence;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.MessageVO;

@Repository
public class MessageImpl implements MessageDAO {

	@Autowired
	SqlSession session;
	
	private static final String namespace = "com.dgit.mapper.MessageMapper";
	
	@Override
	public void create(MessageVO vo) throws Exception {
		session.insert(namespace+".create", vo);

	}

	@Override
	public MessageVO readMessage(int mid) throws Exception {
		
		return session.selectOne(namespace+".readMessage", mid);
	}

	@Override
	public void updateState(int mid) throws Exception {
		session.update(namespace+".updateState", mid);

	}

}
