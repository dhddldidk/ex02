package com.dgit.service;

import com.dgit.domain.MessageVO;

public interface MessageService {
	public void addMessage(MessageVO vo) throws Exception;
	
	public MessageVO readMessage(String uid, int mno) throws Exception;
}
