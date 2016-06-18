package attendanceapp.requestobjecttomodelmapper;

import attendanceapp.model.Authority;

public class SchoolRequestToAuthoritiesMapper {

	private SchoolRequestToAuthoritiesMapper() throws InstantiationException {
		throw new InstantiationException();
	}

	public static class AuthorityBuilder {

		private String username;

		private String authority;

		public AuthorityBuilder username(String username) {
			this.username = username;
			return this;
		}

		public AuthorityBuilder authority(String authority) {
			this.authority = authority;
			return this;
		}

		public Authority build() {
			Authority authorityToReturn = new Authority();
			authorityToReturn.setAuthority(this.authority);
			authorityToReturn.setUsername(this.username);
			return authorityToReturn;
		}
	}

}
