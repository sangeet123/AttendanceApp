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
			final StaffCreateRequestObject staffCreateRequestObject) {
		LocalDateTime utcNow = LocalDateTime.now(Clock.systemUTC());
		Staff staff = new StaffCreateRequestToStaffMapper.StaffResponseBuilder().id(staffCreateRequestObject.getId())
				.firstName(staffCreateRequestObject.getFirstName()).lastName(staffCreateRequestObject.getLastName())
				.email(staffCreateRequestObject.getEmail()).telephone(staffCreateRequestObject.getTelephone())
				.shortName(staffCreateRequestObject.getShortName()).comment(staffCreateRequestObject.getComment())
				.role(staffCreateRequestObject.getRole()).CreatedOn(utcNow).updatedOn(utcNow).build();
		staff.setSchool(createSchoolWithId(schoolId));
		return staff;
	}

	private static School createSchoolWithId(final long id) {
		School school = new School();
		school.setId(id);
		return school;
	}

}
