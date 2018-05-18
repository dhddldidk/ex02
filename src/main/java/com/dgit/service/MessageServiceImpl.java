package com.dgit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgit.domain.MessageVO;
import com.dgit.persistence.MessageDAO;
import com.dgit.persistence.PointDAO;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	MessageDAO messageDao;
	
	@Autowired
	PointDAO pointDao;
	
	@Transactional
	@Override
	public void addMessage(MessageVO vo) throws Exception {
		// 메시지를 남긴 사용자는 10포인트가 추가된다

		//메시지 추가
		messageDao.create(vo);
		//10포인트 올려주기
		pointDao.updatePoint(vo.getSender(), 10);//메시지 보낸사람에게 10포인트
	}

	@Transactional
	@Override
	public MessageVO readMessage(String uid, int mid) throws Exception {
		// 남겨진 메시지를 읽으면 5포인트가 추가된다.
		
		//메시지 열람시간으로 메시지를 열람한지 봄
		messageDao.updateState(mid);
		
		//5포인트
		pointDao.updatePoint(uid, 5);//읽은 사람에게 5포인트
		return messageDao.readMessage(mid);
	}

}
