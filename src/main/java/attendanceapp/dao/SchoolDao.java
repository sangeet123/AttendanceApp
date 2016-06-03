package attendanceapp.dao;

import java.util.List;

import attendanceapp.model.School;
import attendanceapp.model.requestobject.SchoolCreateRequestObject;

public interface SchoolDao {
	School getSchool(final long id);

	List<School> getSchoolList();

	void update(final School school);

	void delete(final long id);

	void delete(final String ids);

	void create(final SchoolCreateRequestObject schoolRequestObject);
}