package attendanceapp.requestobjecttomodelmapper;

import java.time.LocalDateTime;

import attendanceapp.model.Staff;

public class StaffUpdateRequestToStaffMapper {

	public static class StaffBuilder {
		private long id;

		private String firstName;

		private String lastName;

		private String shortName;

		private String telephone;

		private String email;

		private String comment;

		private String role;

		private LocalDateTime updatedOn;

		private boolean enabled;

		public StaffBuilder id(final long id) {
			this.id = id;
			return this;
		}

		public StaffBuilder firstName(final String firstName) {
			this.firstName = firstName;
			return this;
		}

		public StaffBuilder lastName(final String lastName) {
			this.lastName = lastName;
			return this;
		}

		public StaffBuilder shortName(final String shortName) {
			this.shortName = shortName;
			return this;
		}

		public StaffBuilder telephone(final String telephone) {
			this.telephone = telephone;
			return this;
		}

		public StaffBuilder email(final String email) {
			this.email = email;
			return this;
		}

		public StaffBuilder role(final String role) {
			this.role = role;
			return this;
		}

		public StaffBuilder comment(final String comment) {
			this.comment = comment;
			return this;
		}

		public StaffBuilder updatedOn(final LocalDateTime updatedOn) {
			this.updatedOn = updatedOn;
			return this;
		}

		public StaffBuilder enabled(final boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public Staff build() {
			Staff staff = new Staff();
			staff.setId(this.id);
			staff.setComment(this.comment);
			staff.setUpdatedOn(this.updatedOn);
			staff.setEmail(this.email);
			staff.setTelephone(this.telephone);
			staff.setFirstName(this.firstName);
			staff.setLastName(this.lastName);
			staff.setShortName(this.shortName);
			staff.setRole(this.role);
			staff.setEnabled(enabled);
			return staff;
		}
	}

}
