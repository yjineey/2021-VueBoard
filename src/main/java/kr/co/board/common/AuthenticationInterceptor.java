package kr.co.board.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthenticationInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

	// Controller가 호출되기 전 수행
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 클라이언트에 부여한 세션을 가져온다.
		HttpSession session = request.getSession();

		if (session.getAttribute("LoginVo") == null) {
			response.sendRedirect("http://localhost:8081/");

			return false;
		}
		logger.info("preHandle =============================>  " + request.getRequestURI());

		return true;
	}

	// Controller가 정상처리 될때
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		logger.info("postHandle =============================>  " + request.getRequestURI());

	}

	// Controller 수행 후 view단 작업까지 완료 된 후 호출 (예외시에도 화면만 나온다면야...)
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		logger.info("afterCompletion =============================>  " + request.getRequestURI());

	}

}
