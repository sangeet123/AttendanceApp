package attendanceapp.services.util;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import attendanceapp.model.Authority;
import attendanceapp.model.School;
import attendanceapp.model.User;
import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.model.requestobject.SchoolUpdateRequestObject;
import attendanceapp.model.responseobject.SchoolResponseObject;
import attendanceapp.modeltoresponseobjectmapper.SchoolToSchoolResponseMapper;
import attendanceapp.requestobjecttomodelmapper.SchoolRequestToAuthoritiesMapper;
import attendanceapp.requestobjecttomodelmapper.SchoolRequestToSchoolMapper;
import attendanceapp.requestobjecttomodelmapper.SchoolRequestToUserMapper;
import attendanceapp.requestobjecttomodelmapper.SchoolUpdateRequestToSchoolMapper;
import attendanceapp.util.Role;

public final class SchoolServiceUtils {

	private SchoolServiceUtils() throws InstantiationException {
		throw new InstantiationException();
	}

	public static School createSchoolFromSchoolResponseObject(final SchoolCreateRequestObject schoolRequestObject) {
		LocalDateTime createdTime = LocalDateTime.now(Clock.systemUTC());
		return new SchoolRequestToSchoolMapper.SchoolBuilder().id(schoolRequestObject.getId())
				.email(schoolRequestObject.getEmail()).name(schoolRequestObject.getName())
				.telephone(schoolRequestObject.getTelephone()).createdOn(createdTime).updatedOn(createdTime).build();
	}

	public static User createUserFromSchoolResponseObject(final SchoolCreateRequestObject schoolRequestObject) {
		return new SchoolRequestToUserMapper.UserBuilder().username(schoolRequestObject.getUsername())
				.password(schoolRequestObject.getPassword()).enabled(true).build();
	}

	public static Authority createAuthorityFromSchoolRequestObject(
			final SchoolCreateRequestObject schoolRequestObject) {
		String authority = Role.ROLE_SCHOOL_ADMIN;
		return new SchoolRequestToAuthoritiesMapper.AuthorityBuilder().username(schoolRequestObject.getUsername())
				.authority(authority).build();
	}

	public static SchoolResponseObject createSchoolResponseFromSchool(School school) {
		return new SchoolToSchoolResponseMapper.SchoolResponseBuilder().id(school.getId()).email(school.getEmail())
				.name(school.getName()).telephone(school.getTelephone()).createdOn(school.getCreatedOn())
				.updatedOn(school.getUpdatedOn()).build();
	}

	public static School createSchoolFromSchoolUpdateRequestObject(
			SchoolUpdateRequestObject schoolUpdateRequestObject) {
		LocalDateTime utcNow = LocalDateTime.now(Clock.systemUTC());
		return new SchoolUpdateRequestToSchoolMapper.SchoolBuilder().id(schoolUpdateRequestObject.getId())
				.email(schoolUpdateRequestObject.getEmail()).name(schoolUpdateRequestObject.getName())
				.telephone(schoolUpdateRequestObject.getTelephone()).updatedOn(utcNow).build();
	}

	public static List<SchoolResponseObject> createListOfSchoolResponseFromListOfSchool(List<School> schools) {
		List<SchoolResponseObject> schoolsResponseObject = new ArrayList<SchoolResponseObject>();
		schools.forEach(school -> schoolsResponseObject.add(createSchoolResponseFromSchool(school)));
		return schoolsResponseObject;
	}

}
