package kr.co.board.service;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import kr.co.board.dao.LoginDao;
import kr.co.board.vo.LoginVo;

@Service
public class LoginService {

	@Inject
	LoginDao LoginDao;

	public void signUp(Map<String, String> hashMap) throws Exception {
		LoginDao.signUp(hashMap);
	}

	public boolean loginCheck(LoginVo loginvo) throws Exception {
		return LoginDao.loginCheck(loginvo);
	}

	public int loginCheckId(Map<String, String> map) throws Exception {
//		if(((String) map.get("key")).equals("id"))
		return LoginDao.loginCheckId(map.get("id"));
	}

	public void logout(HttpSession session) {
		LoginDao.logout(session);
	}

}
