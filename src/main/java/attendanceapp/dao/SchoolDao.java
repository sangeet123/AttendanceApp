package attendanceapp.dao;

import java.util.List;

import attendanceapp.model.School;
import attendanceapp.model.requestobject.SchoolRequestObject;

public interface SchoolDao {
	public School getSchool(final long id);

	public List<School> getSchoolList();

	public void update(final School school);

	public void delete(final long id);

	public void delete(final String ids);

	void create(final SchoolRequestObject schoolRequestObject);
}