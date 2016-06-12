package attendanceapp.services.util;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import attendanceapp.model.School;
import attendanceapp.model.Staff;
import attendanceapp.model.Subject;
import attendanceapp.model.requestobject.StaffCreateRequestObject;
import attendanceapp.model.requestobject.SubjectUpdateRequestObject;
import attendanceapp.model.responseobject.StaffResponseObject;
import attendanceapp.model.responseobject.SubjectResponseObject;
import attendanceapp.modeltoresponseobjectmapper.StaffToStaffResponseMapper;
import attendanceapp.requestobjecttomodelmapper.StaffCreateRequestToStaffMapper;
import attendanceapp.requestobjecttomodelmapper.SubjectUpdateRequestToSubjectMapper;

public final class StaffServiceUtil {

	private StaffServiceUtil() throws InstantiationException {
		throw new InstantiationException();
	}

	public static StaffResponseObject createStaffResponseObjectFromStaff(final Staff staff) {
		return new StaffToStaffResponseMapper.StaffResponseBuilder().id(staff.getId()).firstName(staff.getFirstName())
				.lastName(staff.getLastName()).email(staff.getEmail()).telephone(staff.getTelephone())
				.shortName(staff.getShortName()).comment(staff.getComment()).role(staff.getRole())
				.CreatedOn(staff.getCreatedOn()).updatedOn(staff.getUpdatedOn()).build();
	}

	public static List<SubjectResponseObject> createStaffResponseObjectListFromSubjectList(
			final List<Subject> subjects) {
		return null;
	}

	public static Subject creaSubjectFromSubjectUpdateRequestObject(final long schoolId,
			final SubjectUpdateRequestObject subjectRequestObject) {
		LocalDateTime utcNow = LocalDateTime.now(Clock.systemUTC());
		Subject subject = new SubjectUpdateRequestToSubjectMapper.SubjectBuilder().id(subjectRequestObject.getId())
				.credit(subjectRequestObject.getCredit()).name(subjectRequestObject.getName())
				.shortName(subjectRequestObject.getShortName()).updatedOn(utcNow).build();
		subject.setSchool(createSchoolWithId(schoolId));
		return subject;
	}

	public static Staff createStaffFromStaffCreateRequestObject(final long schoolId,
			final StaffCreateRequestObject request) {
		LocalDateTime utcNow = LocalDateTime.now(Clock.systemUTC());
		Staff staff = new StaffCreateRequestToStaffMapper.StaffResponseBuilder().id(request.getId())
				.firstName(request.getFirstName()).lastName(request.getLastName()).email(request.getEmail())
				.telephone(request.getTelephone()).shortName(request.getShortName()).comment(request.getComment())
				.role(request.getRole()).createdOn(utcNow).updatedOn(utcNow).enabled(true)
				.username(request.getUsername()).password(request.getPassword()).build();
		staff.setSchool(createSchoolWithId(schoolId));
		return staff;
	}

	private static School createSchoolWithId(final long id) {
		School school = new School();
		school.setId(id);
		return school;
	}

}
