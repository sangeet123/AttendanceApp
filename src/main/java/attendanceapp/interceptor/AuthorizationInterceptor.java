package attendanceapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
	@Override()
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		User user = (User) securityContext.getAuthentication().getPrincipal();
		return true;
	}
}
