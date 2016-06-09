package attendanceapp.modeltoresponseobjectmapper;

import java.time.LocalDateTime;

import attendanceapp.model.Staff;
import attendanceapp.model.responseobject.StaffResponseObject;
import attendanceapp.model.responseobject.SubjectResponseObject;
import attendanceapp.requestobjecttomodelmapper.StaffCreateRequestToStaffMapper.StaffResponseBuilder;

public class StaffToStaffResponseMapper {

	public static class StaffResponseBuilder {
		private long id;

		private String firstName;

		private String lastName;

		private String shortName;

		private String telephone;

		private String email;

		private String comment;

		private String role;

		private LocalDateTime createdOn;

		private LocalDateTime updatedOn;

		public StaffResponseBuilder id(final long id) {
			this.id = id;
			return this;
		}

		public StaffResponseBuilder firstName(final String firstName) {
			this.firstName = firstName;
			return this;
		}

		public StaffResponseBuilder lastName(final String lastName) {
			this.lastName = lastName;
			return this;
		}

		public StaffResponseBuilder shortName(final String shortName) {
			this.shortName = shortName;
			return this;
		}

		public StaffResponseBuilder telephone(final String telephone) {
			this.telephone = telephone;
			return this;
		}

		public StaffResponseBuilder email(final String email) {
			this.email = email;
			return this;
		}

		public StaffResponseBuilder role(final String role) {
			this.role = role;
			return this;
		}

		public StaffResponseBuilder comment(final String comment) {
			this.comment = comment;
			return this;
		}

		public StaffResponseBuilder updatedOn(final LocalDateTime updatedOn) {
			this.updatedOn = updatedOn;
			return this;
		}

		public StaffResponseBuilder CreatedOn(final LocalDateTime createdOn) {
			this.createdOn = createdOn;
			return this;
		}

		public StaffResponseObject build() {
			StaffResponseObject staffResponse = new StaffResponseObject();
			staffResponse.setId(this.id);
			staffResponse.setComment(this.comment);
			staffResponse.setCreatedOn(this.createdOn);
			staffResponse.setUpdatedOn(this.updatedOn);
			staffResponse.setEmail(this.email);
			staffResponse.setTelephone(this.telephone);
			staffResponse.setFirstName(this.firstName);
			staffResponse.setLastName(this.lastName);
			staffResponse.setShortName(this.shortName);
			staffResponse.setRole(this.role);
			return staffResponse;
		}

	}

}
