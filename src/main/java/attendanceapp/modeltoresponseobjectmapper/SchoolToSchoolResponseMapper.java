package attendanceapp.modeltoresponseobjectmapper;

import java.time.LocalDateTime;

import attendanceapp.model.responseobject.SchoolResponseObject;

public class SchoolToSchoolResponseMapper {

	public static class SchoolResponseBuilder {
		private long id;

		private String name;

		private String telephone;

		private String email;

		private LocalDateTime createdOn;

		private LocalDateTime updatedOn;

		public SchoolResponseBuilder id(long id) {
			this.id = id;
			return this;
		}

		public SchoolResponseBuilder name(String name) {
			this.name = name;
			return this;
		}

		public SchoolResponseBuilder telephone(String telephone) {
			this.telephone = telephone;
			return this;
		}

		public SchoolResponseBuilder email(String email) {
			this.email = email;
			return this;
		}

		public SchoolResponseBuilder createdOn(LocalDateTime createdOn) {
			this.createdOn = createdOn;
			return this;
		}

		public SchoolResponseBuilder updatedOn(LocalDateTime updatedOn) {
			this.updatedOn = updatedOn;
			return this;
		}

		public SchoolResponseObject build() {
			SchoolResponseObject schoolResponse = new SchoolResponseObject();
			schoolResponse.setId(this.id);
			schoolResponse.setEmail(this.email);
			schoolResponse.setName(this.name);
			schoolResponse.setTelephone(this.telephone);
			schoolResponse.setCreatedOn(createdOn);
			schoolResponse.setUpdatedOn(updatedOn);
			return schoolResponse;
		}
	}

}
