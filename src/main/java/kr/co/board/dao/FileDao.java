package kr.co.board.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.board.vo.BoardVo;
import kr.co.board.vo.FileVo;

@Repository
public class FileDao {

	@Inject
	SqlSession SqlSession;

	public List<BoardVo> FileListAll() throws Exception {
		return SqlSession.selectList("file.FileListAll");
	}

	public List<FileVo> fileView(int id) throws Exception {
		return SqlSession.selectList("file.fileView", id);
	}

	public void fileUpload(FileVo fileVo) {
		SqlSession.insert("file.fileUpload", fileVo);
	}

	public int fileCount(int id) throws Exception {
		return SqlSession.selectOne("file.fileCount", id);
	}
	public void fileDelete(String fileNmae) throws Exception {
		SqlSession.update("file.fileDelete", fileNmae);
	}
}
