package attendanceapp.services.util;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import attendanceapp.model.Subject;
import attendanceapp.model.requestobject.SubjectCreateRequestObject;
import attendanceapp.model.requestobject.SubjectUpdateRequestObject;
import attendanceapp.model.responseobject.SubjectResponseObject;
import attendanceapp.modeltoresponseobjectmapper.SubjectToSubjectResponseMapper;
import attendanceapp.requestobjecttomodelmapper.SubjectCreateRequestToSubjectMapper;
import attendanceapp.requestobjecttomodelmapper.SubjectUpdateRequestToSubjectMapper;

public final class SubjectServiceUtil {

	public static SubjectResponseObject createSubjectResponseObjectFromSubject(final Subject subject) {
		return new SubjectToSubjectResponseMapper.SubjectResponseBuilder().createdOn(subject.getCreatedOn())
				.updatedOn(subject.getUpdatedOn()).shortName(subject.getShortName()).id(subject.getId())
				.name(subject.getName()).build();
	}

	public static List<SubjectResponseObject> createSubjectResponseObjectListFromSubjectList(
			final List<Subject> subjects) {
		List<SubjectResponseObject> subjectResponseObjectLists = new ArrayList<SubjectResponseObject>();
		subjects.forEach(subject -> subjectResponseObjectLists.add(createSubjectResponseObjectFromSubject(subject)));
		return subjectResponseObjectLists;
	}

	public static Subject creaSubjectFromSubjectUpdateRequestObject(
			final SubjectUpdateRequestObject subjectRequestObject) {
		LocalDateTime utcNow = LocalDateTime.now(Clock.systemUTC());
		return new SubjectUpdateRequestToSubjectMapper.SubjectBuilder().id(subjectRequestObject.getId())
				.credit(subjectRequestObject.getCredit()).name(subjectRequestObject.getName())
				.shortName(subjectRequestObject.getShortName()).updatedOn(utcNow).build();
	}

	public static Subject creaSubjectFromSubjectCreateRequestObject(
			final SubjectCreateRequestObject subjectCreateRequestObject) {
		LocalDateTime utcNow = LocalDateTime.now(Clock.systemUTC());
		return new SubjectCreateRequestToSubjectMapper.SubjectBuilder().id(subjectCreateRequestObject.getId())
				.credit(subjectCreateRequestObject.getCredit()).name(subjectCreateRequestObject.getName())
				.shortName(subjectCreateRequestObject.getShortName()).updatedOn(utcNow).CreatedOn(utcNow).build();
	}

}
