package kr.co.board.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.board.dao.BoardDao;
import kr.co.board.service.BoardService;
import kr.co.board.service.FileService;
import kr.co.board.vo.BoardVo;
import kr.co.board.vo.FileVo;
import net.sf.json.JSONObject;

//@RequestMapping("/board/*")
@RestController
public class BoardController {

	@Inject
	BoardService boardService;

	@Inject
	FileService fileService;

	@Inject
	BoardDao boardDao;

	@Autowired
	ResourceLoader resourceLoader;

	// ModelAndView 사용x redirect 사용x : 같은 프로젝트 내에 웹 페이지가 존재 할 때만 사용 가능
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	// 게시글목록
	@RequestMapping(value = "/boardListAll", method = RequestMethod.GET)
	public ResponseEntity<List<BoardVo>> boardListAll() throws Exception {
		return new ResponseEntity<List<BoardVo>>(boardService.boardListAll(), HttpStatus.OK);
	}

	// 페이징처리
	@RequestMapping(value = "/perPageCount", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> perPageCount(@RequestParam int perPageNum, int pageStart, BoardVo vo)
			throws Exception {
		System.out.println(":::::::::::게시판 목록출력::::::::::::::::::");
		vo.setPerPageNum(perPageNum);
		vo.setPageStart(pageStart);
//		vo.setTotal(boardService.boardCount());
//		List<Map<String, Object>> pageList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardList", boardService.perPageCount(vo));
		map.put("boardCount", boardService.boardCount());
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	// 글 작성
	@RequestMapping(value = "/boardInsert", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ResponseEntity<List<BoardVo>> boardInsert(BoardVo vo, FileVo fileVo) throws Exception {
		System.out.println(":::::::::::게시글 등록::::::::::::::::::");
		try {
			vo.setTitle(vo.getTitle());
			vo.setContents(vo.getContents());
			vo.setWriter(vo.getWriter());

			boardService.boardInsert(vo);
			fileService.fileUpload(vo, fileVo);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// 상세보기
	@RequestMapping(value = "/boardView/{id}", method = RequestMethod.GET)
	public JSONObject boardListOne(@PathVariable int id) throws Exception {
		System.out.println(":::::::::::게시글 상세보기::::::::::::::::::");

		JSONObject json = new JSONObject();

		List<FileVo> multiFile = new ArrayList<FileVo>();
		multiFile = fileService.fileView(id);

		json.put("boardView", boardService.boardView(id));
		json.put("fileView", multiFile);

		// json.toString();
		return json;
	}

	// 글 수정
	@RequestMapping(value = "/boardUpdate2", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ResponseEntity<List<BoardVo>> boardUpdate2(BoardVo vo, FileVo fileVo) throws Exception {
		System.out.println(":::::::::::게시글 수정::::::::::::::::::");
		System.out.println(vo);
		System.out.println(fileVo);

		try {
			vo.getId();
			vo.setTitle(vo.getTitle());
			vo.setWriter(vo.getWriter());
			vo.setContents(vo.getContents());

			boardService.boardUpdate(vo);
			fileService.fileUpload(vo, fileVo);

			System.out.println("게시글 수정완료");
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 글삭제
	@RequestMapping(value = "/boardDelete2", method = RequestMethod.DELETE)
	public String boardDelete2(@RequestParam int id) throws Exception {
		System.out.println("게시글 삭제");
		boardService.boardDelete(id);
		return null;
	}

	// 파일삭제
	@RequestMapping(value = "/fileDelete", method = RequestMethod.POST)
	public String fileDelete(@RequestParam String fileName) throws Exception {
		System.out.println(":::::::::::파일 삭제::::::::::::::::::");
		System.out.println(fileName);

		fileService.fileDelete(fileName);
		return null;
	}

	// 검색
	@RequestMapping(value = "/boardSearch", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> boardSearch(@RequestParam String searchVal, @RequestParam int perPageNum,
			@RequestParam int pageStart, BoardVo vo) throws Exception {

		System.out.println(":::::::::::게시글 검색::::::::::::::::::");

		System.out.println(perPageNum);
		System.out.println(pageStart);
		Map<String, Object> hashmap = new HashMap<String, Object>();

		hashmap.put("title", searchVal);
		hashmap.put("writer", searchVal);
		hashmap.put("perPageNum", perPageNum);
		hashmap.put("pageStart", pageStart);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchList", boardService.boardSearch(hashmap));
		map.put("searchCount", boardService.searchCount(searchVal));

		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	/** 게시판 - 첨부파일 다운로드 : orgFileName(파일명), fileName(파일명+암호화), filePath(파일경로) */
	@RequestMapping(value = "/fileDownload/{orgFileName}")
	public ResponseEntity<Resource> fileDownload(@PathVariable String orgFileName,
			@RequestParam("fileName") String fileName) {
		System.out.println(":::::::::::파일 다운로드::::::::::::::::::");

		try {
			System.out.println("\"classpath:uploadPath/\" + fileName >>>" + "classpath:uploadPath/" + fileName);
			// classpath에 위치한 file경로 : resourceLoader를 사용하여 프로젝트의 resouces를 읽어와 다운로드가 가능하다.
			Resource resource = resourceLoader.getResource("classpath:uploadPath/" + fileName);

			// 파일의 길이를 헤더에 지정해 주기 위해 file로 저장, 파일이 없는 경우 fileNotFoundException error가 난다.
			File file = resource.getFile();

			// 파일의 mediaType을 알아내기 위한 api
			Tika tika = new Tika();
			String mediaType = tika.detect(file);

			//resource는  classpath에 위치한 file 경로
			System.out.println("resource.getFile() >> " + resource);
			//전체경로 및 파일 길이
			System.out.println("file.getPath() >> " + file.getPath());
			System.out.println("file.length() >> " + file.length());
			// resource에 저장된 파일명
			System.out.println("resource.getFilename() ::: " + resource.getFilename());

			// ResponseEntity를 사용하기 위해서는 응답상태코드, 응답헤더, 응답본문을 설정해 주어야 함
			// ok 200 상태코드 설정
			return ResponseEntity.ok()
					// 파일이 다운로드 되어 저장될 이름
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + resource.getFilename() + "\"")
					// 파일의 크기
					.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))
					// 파일타입_ 바이너리 데이터로 받아오기
					.header(HttpHeaders.CONTENT_TYPE, mediaType)
					// 응답본문
					.body(resource);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 게시글목록 (다른방법)
//	@RequestMapping(value = "/boardListAll", method = RequestMethod.GET)
//	public ResponseEntity <List<Map<String, Object>>> boardListAll() throws Exception {
//		List<Map<String, Object>> list = boardService.boardListAll();
//		return new ResponseEntity<>(list, HttpStatus.OK);
//	}
//	@RequestMapping(value = "/boardListAll2", method = RequestMethod.GET)
//	public ResponseEntity<List<BoardVo>> boardListAll() throws Exception {
//		List<BoardVo> result = boardService.boardListAll();
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}

	// 게시글 수
//	@RequestMapping(value = "/boardCount", method = RequestMethod.GET)
//	public ResponseEntity<Integer> boardCount() throws Exception {
//		return new ResponseEntity<Integer>(boardService.boardCount(), HttpStatus.OK);
//	}

	// 파일 수
//	@RequestMapping(value = "/fileCount", method = RequestMethod.GET)
//	public ResponseEntity<Integer> fileCount(int id) throws Exception {
//		System.out.println(fileService.fileCount(id));
//		return new ResponseEntity<Integer>(fileService.fileCount(id), HttpStatus.OK);
//	}
}