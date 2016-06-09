package attendanceapp.requestobjecttomodelmapper;

import java.time.LocalDateTime;

import attendanceapp.model.Staff;

public class StaffCreateRequestToStaffMapper {

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

		public Staff build() {
			Staff staff = new Staff();
			staff.setId(this.id);
			staff.setComment(this.comment);
			staff.setCreatedOn(this.createdOn);
			staff.setUpdatedOn(this.updatedOn);
			staff.setEmail(this.email);
			staff.setTelephone(this.telephone);
			staff.setFirstName(this.firstName);
			staff.setLastName(this.lastName);
			staff.setShortName(this.shortName);
			staff.setRole(this.role);
			return staff;
		}
	}

}
