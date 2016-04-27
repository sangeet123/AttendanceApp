package attendanceapp.requestobjecttomodelmapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import attendanceapp.model.User;

public class SchoolRequestToUserMapper {

	public static class UserBuilder {

		public String username;

		public String password;

		public boolean enabled;

		public UserBuilder username(String username) {
			this.username = username;
			return this;
		}

		public UserBuilder password(String password) {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);
			this.password = hashedPassword;
			return this;
		}

		public UserBuilder enabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public User build() {
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setEnabled(enabled);
			return user;
		}

	}

}
