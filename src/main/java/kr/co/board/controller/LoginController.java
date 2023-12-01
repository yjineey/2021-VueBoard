package kr.co.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.board.dao.LoginDao;
import kr.co.board.service.LoginService;
import kr.co.board.vo.LoginVo;

@RestController
public class LoginController {

	@Autowired
	LoginService loginService;
	LoginDao loginDao;

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	// 회원가입
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public void signUp(@RequestBody Map<String, String> map) throws Exception {
		Map<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("name", map.get("name"));
		hashmap.put("id", map.get("id"));
		hashmap.put("password", map.get("password"));
		hashmap.put("agree", map.get("agree"));
		hashmap.put("sex", map.get("sex"));
		hashmap.put("job", map.get("job"));
		hashmap.put("grade", map.get("grade"));
		loginService.signUp(hashmap);
		System.out.println("회원가입완료");
	}

	// 로그인
	@RequestMapping(value = "/loginCheck", method = RequestMethod.GET)
	public ResponseEntity<Map<Object, Object>> loginCheck(HttpServletRequest request, HttpServletResponse response,
			LoginVo loginvo) throws Exception {

		HttpSession session = request.getSession();
		Map<Object, Object> hashmap = new HashMap<Object, Object>();
		boolean loginResult = loginService.loginCheck(loginvo);

		// 로그인 아이디 패스워드 값이 true이면
		if (loginResult) {

			session.setAttribute("id", loginvo.getId());
			session.setAttribute("password", loginvo.getPassword());

			System.out.println("로그인 : 세션 : 아이디 : " + session.getId());
			System.out.println("로그인 : 세션 : 패스워드 : " + session.getAttribute("password"));

			hashmap.put("id", loginvo.getId());
			hashmap.put("password", loginvo.getPassword());
//			hashmap.put("token", request.getSession());
//			hashmap.put("token", request);

		} else {
			System.out.println("=========== 로그인 실패 ===========");
			return null;
		}
		return new ResponseEntity<>(hashmap, HttpStatus.OK);
	}

	// 로그인 아이디 체크
	@RequestMapping(value = "/loginCheckId", method = RequestMethod.POST)
	public boolean loginCheckId(@RequestBody Map<String, String> map) throws Exception {
		System.out.println("회원가입 : 아이디 확인 : " + map);
		return (loginService.loginCheckId(map) == 0);
	}

	// 로그아웃
	@RequestMapping(value = "/logOut", method = RequestMethod.GET)
	public void logout(HttpSession session) {
		loginService.logout(session);
	}

}
