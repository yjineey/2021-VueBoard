package kr.co.board.dao;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.board.vo.LoginVo;

@Repository
public class LoginDao {

	@Inject
	SqlSession SqlSession;

	public void signUp(Map<String, String> hashMap) throws Exception {
		SqlSession.insert("login.signUp", hashMap);
	}

	public boolean loginCheck(LoginVo loginvo) throws Exception {
		return (SqlSession.selectList("login.loginCheck", loginvo.getId()) == null) ? false : true;
	}

	public int loginCheckId(String id) throws Exception {
		return SqlSession.selectOne("login.loginCheckId", id);
	}

	public void logout(HttpSession session) {
		session.invalidate();
	}

}
