package attendanceapp.interceptor;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	@Autowired()
	private SessionFactory sessionFactory;
	private Session session = null;

	final String school = "school";
	final String forwardSlash = "/";
	final String checkIfUserIsAllowedToAccessSchoolResource = "from attendanceapp.model.User where username= :username and school.id= :schoolId";

	@Override()
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		final SecurityContext securityContext = SecurityContextHolder.getContext();
		final String pathInfo = request.getPathInfo();
		String[] params = pathInfo.split(forwardSlash);
		int indexOfSchoolId = Arrays.asList(params).indexOf(school);
		session = sessionFactory.openSession();
		try {
			long schoolId = Long.parseLong(params[indexOfSchoolId + 1]);
			final User user = (User) securityContext.getAuthentication().getPrincipal();
			final String userName = user.getUsername();
			Query query = session.createQuery(checkIfUserIsAllowedToAccessSchoolResource)
					.setParameter("username", userName).setParameter("schoolId", schoolId);
			if (query.uniqueResult() == null) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return false;
			}
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return false;
		} finally {
			closeSession();
		}

		return true;
	}

	private void closeSession() {
		if (session != null) {
			session.close();
		}
	}
}
