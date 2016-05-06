package attendanceapp.modeltoresponseobjectmapper;

import java.time.LocalDateTime;

import attendanceapp.model.responseobject.SubjectResponseObject;

public class SubjectToSubjectResponseMapper {

	public static class SubjectResponseBuilder {
		private long id;

		private String name;

		private int credit;

		private String shortName;

		private LocalDateTime createdOn;

		private LocalDateTime updatedOn;

		public SubjectResponseBuilder id(final long id) {
			this.id = id;
			return this;
		}

		public SubjectResponseBuilder name(final String name) {
			this.name = name;
			return this;
		}

		public SubjectResponseBuilder credit(final int credit) {
			this.credit = credit;
			return this;
		}

		public SubjectResponseBuilder shortName(final String shortName) {
			this.shortName = shortName;
			return this;
		}

		public SubjectResponseBuilder createdOn(final LocalDateTime createdOn) {
			this.createdOn = createdOn;
			return this;
		}

		public SubjectResponseBuilder updatedOn(final LocalDateTime updatedOn) {
			this.updatedOn = updatedOn;
			return this;
		}

		public SubjectResponseObject build() {
			SubjectResponseObject subjectResponseObject = new SubjectResponseObject();
			subjectResponseObject.setId(this.id);
			subjectResponseObject.setShortName(this.shortName);
			subjectResponseObject.setName(this.name);
			subjectResponseObject.setCredit(credit);
			subjectResponseObject.setCreatedOn(createdOn);
			subjectResponseObject.setUpdatedOn(updatedOn);
			return subjectResponseObject;
		}
	}

}
