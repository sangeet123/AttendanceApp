package attendanceapp.requestobjecttomodelmapper;

import java.time.LocalDateTime;

import attendanceapp.model.Subject;

public class SubjectUpdateRequestToSubjectMapper {

	public static class SubjectBuilder {
		private long id;

		private String name;

		private String shortName;

		private int credit;

		private LocalDateTime updatedOn;

		public SubjectBuilder id(long id) {
			this.id = id;
			return this;
		}

		public SubjectBuilder name(String name) {
			this.name = name;
			return this;
		}

		public SubjectBuilder shortName(String shortName) {
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

		public Subject build() {
			Subject subject = new Subject();
			subject.setId(this.id);
			subject.setShortName(this.shortName);
			subject.setName(this.name);
			subject.setCredit(this.credit);
			subject.setUpdatedOn(updatedOn);
			return subject;
		}
	}

}
