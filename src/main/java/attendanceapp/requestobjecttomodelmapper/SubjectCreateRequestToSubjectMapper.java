package attendanceapp.requestobjecttomodelmapper;

import java.time.LocalDateTime;

import attendanceapp.model.Subject;

public class SubjectCreateRequestToSubjectMapper {

	public static class SubjectBuilder {
		private long id;

		private String name;

		private String shortName;

		private int credit;

		private LocalDateTime createdOn;

		private LocalDateTime updatedOn;

		public SubjectBuilder id(final long id) {
			this.id = id;
			return this;
		}

		public SubjectBuilder name(final String name) {
			this.name = name;
			return this;
		}

		public SubjectBuilder shortName(final String shortName) {
			this.shortName = shortName;
			return this;
		}

		public SubjectBuilder credit(final int credit) {
			this.credit = credit;
			return this;
		}

		public SubjectBuilder updatedOn(final LocalDateTime updatedOn) {
			this.updatedOn = updatedOn;
			return this;
		}

		public SubjectBuilder CreatedOn(final LocalDateTime createdOn) {
			this.createdOn = createdOn;
			return this;
		}

		public Subject build() {
			Subject subject = new Subject();
			subject.setId(this.id);
			subject.setShortName(this.shortName);
			subject.setName(this.name);
			subject.setCredit(this.credit);
			subject.setUpdatedOn(updatedOn);
			subject.setCreatedOn(createdOn);
			return subject;
		}
	}

}
