package attendanceapp.requestobjecttomodelmapper;

import attendanceapp.model.Authority;

public class SchoolRequestToAuthoritiesMapper {

	public static class AuthorityBuilder {

		public String username;

		public String authority;

		public AuthorityBuilder username(String username) {
			this.username = username;
			return this;
		}

		public AuthorityBuilder authority(String authority) {
			this.authority = authority;
			return this;
		}

		public Authority build() {
			Authority authority = new Authority();
			authority.setAuthority(this.authority);
			authority.setUsername(this.username);
			return authority;
		}
	}

}
