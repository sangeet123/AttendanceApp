package attendanceapp.services;

import java.util.List;

import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.model.requestobject.SchoolUpdateRequestObject;
import attendanceapp.model.responseobject.SchoolResponseObject;

public interface SchoolService {
	public SchoolResponseObject getSchool(final long id);

	public List<SchoolResponseObject> getSchoolList();

	public void update(final SchoolUpdateRequestObject schoolUpdateRequest);

	public void delete(final long id);

	public void create(final SchoolCreateRequestObject school);

	public void delete(final String ids);
}
