package kr.co.board.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.board.vo.BoardVo;

@Repository
public class BoardDao {

	@Inject
	SqlSession SqlSession;

	public List<BoardVo> boardListAll() throws Exception {
		return SqlSession.selectList("board.boardListAll");
	}

	public List<BoardVo> pageList(@Param("limit") int limit) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limit", limit);
		return SqlSession.selectList("board.pageList", map);
	}

	public List<BoardVo> perPageCount(BoardVo vo) throws Exception {
		return SqlSession.selectList("board.perPageCount", vo);
	}

	public int boardCount() throws Exception {
		return SqlSession.selectOne("board.boardCount");
	}
	public int searchCount(String searchVal) throws Exception {
		return SqlSession.selectOne("board.searchCount",searchVal);
	}

	public void boardInsert(BoardVo vo) throws Exception {
		SqlSession.insert("board.boardInsert", vo);
	}

	public BoardVo boardView(int id) throws Exception {
		return SqlSession.selectOne("board.boardView", id);
	}

	public void boardUpdate(BoardVo vo) throws Exception {
		SqlSession.update("board.boardUpdate", vo);
	}

	public void boardDelete(int id) throws Exception {
		SqlSession.update("board.boardDelete", id);
	}

	public void boardDeleteAll(ArrayList<BoardVo> array) throws Exception {
		SqlSession.update("board.boardDelete", array);
	}

	public List<BoardVo> boardSearch(Map<String, Object> map) throws Exception {
		return SqlSession.selectList("board.boardSearch", map);
	}

}
