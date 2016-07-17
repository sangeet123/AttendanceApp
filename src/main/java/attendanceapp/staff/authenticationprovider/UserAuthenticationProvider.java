package attendanceapp.staff.authenticationprovider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import attendanceapp.model.Staff;
import attendanceapp.services.StaffService;

public class UserAuthenticationProvider implements AuthenticationProvider {

	@Autowired()
	private StaffService staffService;

	@Override()
	public Authentication authenticate(Authentication authentication) {
		Staff staff = staffService.getStaffByUserNameAndPassword(authentication.getName(),
				authentication.getCredentials().toString());

		if (staff != null) {
			Collection<GrantedAuthority> authorities = new ArrayList<>(buildRolesFromUser(staff));
			return new UsernamePasswordAuthenticationToken(staff.getUsername(), staff.getPassword(), authorities);
		} else {
			throw new BadCredentialsException("");
		}

	}

	private Collection<GrantedAuthority> buildRolesFromUser(Staff staff) {
		Collection<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(staff.getRole()));
		return authorities;
	}

	@Override()
	public boolean supports(@SuppressWarnings("rawtypes") Class authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
