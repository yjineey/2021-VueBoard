package kr.co.board.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.board.vo.ReplyVo;

@Repository
public class ReplyDao {

	@Inject
	SqlSession SqlSession;

	public List<ReplyVo> replyListAll() throws Exception {
		return SqlSession.selectList("reply.replyListAll");
	}

	public int replyCount() throws Exception {
		return SqlSession.selectOne("reply.replyCount");
	}

	public void replyInsert(Map<String, String> hashMap) throws Exception {
		SqlSession.insert("reply.replyInsert", hashMap);
	}

	public void replyUpdate(ReplyVo vo) throws Exception {
		SqlSession.update("reply.replyUpdate", vo);
	}

	public void replyDelete(int id) throws Exception {
		SqlSession.update("reply.replyDelete", id);
	}

}
