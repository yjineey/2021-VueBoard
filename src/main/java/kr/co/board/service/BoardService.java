package kr.co.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.co.board.dao.BoardDao;
import kr.co.board.vo.BoardVo;

@Service
public class BoardService {

	@Inject
	BoardDao boardDao;

	public List<BoardVo> boardListAll() throws Exception {
		return boardDao.boardListAll();
	}


	public List<BoardVo> pageList(int limit) throws Exception {
		return boardDao.pageList(limit);
	}

	public List<BoardVo> perPageCount(BoardVo vo) throws Exception {
		return boardDao.perPageCount(vo);
	}

	public int boardCount() throws Exception {
		return boardDao.boardCount();
	}
	public int searchCount(String searchVal) throws Exception {
		return boardDao.searchCount(searchVal);
	}

	public void boardInsert(BoardVo vo) throws Exception {
		boardDao.boardInsert(vo);
	}

	// 리턴 할 Type
	public BoardVo boardView(int id) throws Exception {
		return boardDao.boardView(id);
	}

	public void boardUpdate(BoardVo vo) throws Exception {
		boardDao.boardUpdate(vo);
	}

	public void boardDelete(int id) throws Exception {
		boardDao.boardDelete(id);
	}

	public void boardDeleteAll(ArrayList<BoardVo> array) throws Exception {
		boardDao.boardDeleteAll(array);
	}

	public List<BoardVo> boardSearch(Map<String, Object> map) throws Exception {
		return boardDao.boardSearch(map);
	}

}