package kr.co.board.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.co.board.dao.ReplyDao;
import kr.co.board.vo.ReplyVo;

@Service
public class ReplyService {

	@Inject
	ReplyDao replyDao;

	public List<ReplyVo> replyListAll() throws Exception {
		return replyDao.replyListAll();
	}

	public int replyCount() throws Exception {
		return replyDao.replyCount();
	}

	public void replyInsert(Map<String, String> hashMap) throws Exception {
		replyDao.replyInsert(hashMap);
	}

	public void replyUpdate(ReplyVo vo) throws Exception {
		replyDao.replyUpdate(vo);
	}

	public void replyDelete(int id) throws Exception {
		replyDao.replyDelete(id);
	}
}