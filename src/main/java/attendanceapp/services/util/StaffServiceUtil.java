package attendanceapp.services.util;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import attendanceapp.model.School;
import attendanceapp.model.Staff;
import attendanceapp.model.requestobject.StaffCreateRequestObject;
import attendanceapp.model.requestobject.StaffUpdateRequestObject;
import attendanceapp.model.responseobject.StaffResponseObject;
import attendanceapp.modeltoresponseobjectmapper.StaffToStaffResponseMapper;
import attendanceapp.requestobjecttomodelmapper.StaffCreateRequestToStaffMapper;

public final class StaffServiceUtil {

	private StaffServiceUtil() throws InstantiationException {
		throw new InstantiationException();
	}

	private static School createSchoolWithId(final long id) {
		School school = new School();
		school.setId(id);
		return school;
	}

	public static StaffResponseObject createStaffResponseObjectFromStaff(final Staff staff) {
		return new StaffToStaffResponseMapper.StaffResponseBuilder().id(staff.getId()).firstName(staff.getFirstName())
				.lastName(staff.getLastName()).email(staff.getEmail()).telephone(staff.getTelephone())
				.shortName(staff.getShortName()).comment(staff.getComment()).role(staff.getRole())
				.CreatedOn(staff.getCreatedOn().toString()).updatedOn(staff.getUpdatedOn().toString()).build();
	}

	public static List<StaffResponseObject> createStaffResponseObjectListFromStaffList(final List<Staff> staffs) {
		List<StaffResponseObject> responseObjects = new ArrayList<>();
		staffs.forEach(staff -> responseObjects.add(createStaffResponseObjectFromStaff(staff)));
		return responseObjects;
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

	public static void copyAttributes(final Staff original, final StaffUpdateRequestObject updateObject) {
		original.setComment(updateObject.getComment());
		original.setFirstName(updateObject.getFirstName());
		original.setLastName(updateObject.getLastName());
		original.setComment(updateObject.getComment());
		original.setEmail(updateObject.getEmail());
		original.setRole(updateObject.getShortName());
		original.setUpdatedOn(LocalDateTime.now(Clock.systemUTC()));
		original.setRole(updateObject.getRole());
		original.setShortName(updateObject.getShortName());
		original.setTelephone(updateObject.getTelephone());
	}
}
