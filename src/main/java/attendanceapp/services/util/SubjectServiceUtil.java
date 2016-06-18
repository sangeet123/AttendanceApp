package attendanceapp.services.util;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import attendanceapp.model.School;
import attendanceapp.model.Subject;
import attendanceapp.model.requestobject.SubjectCreateRequestObject;
import attendanceapp.model.requestobject.SubjectUpdateRequestObject;
import attendanceapp.model.responseobject.SubjectResponseObject;
import attendanceapp.modeltoresponseobjectmapper.SubjectToSubjectResponseMapper;
import attendanceapp.requestobjecttomodelmapper.SubjectCreateRequestToSubjectMapper;

public final class SubjectServiceUtil {

	private SubjectServiceUtil() throws InstantiationException {
		throw new InstantiationException();
	}

	public static SubjectResponseObject createSubjectResponseObjectFromSubject(final Subject subject) {
		return new SubjectToSubjectResponseMapper.SubjectResponseBuilder().createdOn(subject.getCreatedOn())
				.updatedOn(subject.getUpdatedOn()).shortName(subject.getShortName()).id(subject.getId())
				.credit(subject.getCredit()).name(subject.getName()).build();
	}

	public static List<SubjectResponseObject> createSubjectResponseObjectListFromSubjectList(
			final List<Subject> subjects) {
		List<SubjectResponseObject> subjectResponseObjectLists = new ArrayList<>();
		subjects.forEach(subject -> subjectResponseObjectLists.add(createSubjectResponseObjectFromSubject(subject)));
		return subjectResponseObjectLists;
	}

	public static Subject createSubjectFromSubjectCreateRequestObject(final long schoolId,
			final SubjectCreateRequestObject subjectCreateRequestObject) {
		LocalDateTime utcNow = LocalDateTime.now(Clock.systemUTC());
		Subject subject = new SubjectCreateRequestToSubjectMapper.SubjectBuilder()
				.id(subjectCreateRequestObject.getId()).credit(subjectCreateRequestObject.getCredit())
				.name(subjectCreateRequestObject.getName()).shortName(subjectCreateRequestObject.getShortName())
				.updatedOn(utcNow).CreatedOn(utcNow).build();
		subject.setSchool(createSchoolWithId(schoolId));
		return subject;
	}

	private static School createSchoolWithId(final long id) {
		School school = new School();
		school.setId(id);
		return school;
	}

	public static void copyAttributes(final Subject originalSubject, final SubjectUpdateRequestObject request) {
		originalSubject.setCredit(request.getCredit());
		originalSubject.setName(request.getName());
		originalSubject.setShortName(request.getShortName());
		originalSubject.setUpdatedOn(LocalDateTime.now(Clock.systemUTC()));
	}

}
