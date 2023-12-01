package kr.co.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.board.dao.ReplyDao;
import kr.co.board.service.ReplyService;
import kr.co.board.vo.ReplyVo;

//@RequestMapping("/board/*")
@RestController
public class ReplyController {

	@Inject
	ReplyService replyService;
	ReplyDao replyDao;

	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	//댓글목록
	@RequestMapping(value = "/replyListAll", method = RequestMethod.GET)
	public ResponseEntity<List<ReplyVo>> replyListAll() throws Exception {
		return new ResponseEntity<List<ReplyVo>>(replyService.replyListAll(), HttpStatus.OK);
	}

	// 댓글수
	@RequestMapping(value = "/replyCount", method = RequestMethod.GET)
	public int  replyCount() throws Exception {
		int replyCount = replyService.replyCount();
		System.out.println(replyCount);
		return replyCount;
	}
	
	// 댓글작성
	@RequestMapping(value = "/replyInsert", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> replyInsert(@RequestBody Map<String, String> map) throws Exception {
		System.out.println(map);
		Map<String, String> hashmap = new HashMap<String, String>();
		try {
			hashmap.put("title", map.get("title"));
			hashmap.put("contents", map.get("contents"));
			hashmap.put("writer", map.get("writer"));
			hashmap.put("name", map.get("name"));
			hashmap.put("url", map.get("url"));
			replyService.replyInsert(hashmap);
			
			System.out.println( map.get("name"));
			System.out.println( map.get("url"));
			System.out.println("게시글 등록완료");
		} catch (Exception e) {
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	// 댓글 수정
	@RequestMapping(value = "/replyUpdate", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, String>> replyUpdate(@RequestBody Map<String, String> map) throws Exception {
		ReplyVo vo = new ReplyVo();
		System.out.println(map);
		try {
			vo.setId(Integer.parseInt(map.get("id")));
			vo.setContents(map.get("contents"));
			vo.setTitle(map.get("title"));
			vo.setUse_yn("Y");
			vo.setWriter(map.get("writer"));
			replyService.replyUpdate(vo);
			System.out.println("게시글 수정완료");
		} catch (Exception e) {
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	// 글삭제
	@RequestMapping(value = "/replyDelete", method = RequestMethod.DELETE)
	public String replyDelete(@RequestParam int id) throws Exception {
		System.out.println("게시글 삭제");
		replyService.replyDelete(id);
		return null;
	}
}
