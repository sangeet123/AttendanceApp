package attendanceapp.requestobjecttomodelmapper;

import attendanceapp.model.School;

public class SchoolRequestToSchoolMapper {

	public static class SchoolBuilder {
		private long id;

		private String name;

		private String telephone;

		private String email;

		public SchoolBuilder id(long id) {
			this.id = id;
			return this;
		}

		public SchoolBuilder name(String name) {
			this.name = name;
			return this;
		}

		public SchoolBuilder telephone(String telephone) {
			this.telephone = telephone;
			return this;
		}

		public SchoolBuilder email(String email) {
			this.email = email;
			return this;
		}

		public School build() {
			School school = new School();
			school.setId(this.id);
			school.setEmail(this.email);
			school.setName(this.name);
			school.setTelephone(this.telephone);
			return school;
		}
	}

}
