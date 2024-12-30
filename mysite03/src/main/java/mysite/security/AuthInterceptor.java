package mysite.security;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//1. Handler 종류 확인
		if(!(handler instanceof HandlerMethod)) {
			// DefaultServletRequestHandler 타입인 경우
			// DefaultServletHandler가 처리하는 경우(정적자원, /assets/**, mapping이 안되어 있는 URL)
			return true; // 인증/권한 검사 건너뜀
		}
		
		//2. casting => HandlerMethod를 통해 현재 요청을 처리하는 컨트롤러 메서드에 접근 가능
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3. Handler Method에서 @Auth 가져오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		//4. Handler Method에서 @Auth가 없으면 클래스(타입)에서 가져오기
		if(auth == null) {
			// getBeanType() : 메서드가 속한 클래스 타입을 반환
			auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
		}
		
		//5. @Auth가 없으면...
		if(auth == null) {
			return true; // 인증/권한 검사 건너뜀
		}
		
		/* 인증/권한 검사 */
		String role = auth.role();
		
		//6. @Auth가 붙어 있기때문에 인증(Authentication) 여부 확인
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false; // 요청 처리 중단
		}
		
		// 권한(ROLE) 체크
		if(role.equals("ADMIN")) {
			if(!authUser.getRole().equals("ADMIN")) {
				response.sendRedirect(request.getContextPath());
				return false;
			}
		} else if(role.equals("USER")) {
			if(!authUser.getRole().equals("ADMIN") && !authUser.getRole().equals("USER")) {
				response.sendRedirect(request.getContextPath());
				return false;
			}			
		} else {
			return false;
		}
		
		
		//7. @Auth가 붙어 있고 인증도 된 경우
		return true;
	}

}
