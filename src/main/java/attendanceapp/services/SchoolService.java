package attendanceapp.services;

import java.util.List;

import attendanceapp.model.requestobject.SchoolRequestObject;
import attendanceapp.model.responseobject.SchoolResponseObject;

public interface SchoolService {
	public SchoolResponseObject getSchool(final long id);

	public List<SchoolResponseObject> getSchoolList();

	public void update(final SchoolRequestObject school);

	public void delete(final long id);

	public void create(final SchoolRequestObject school);

	public void delete(final String ids);
}
