package attendanceapp.services;

import java.util.List;

import attendanceapp.model.requestobject.DeleteSelectedSchoolRequestObject;
import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.model.requestobject.SchoolUpdateRequestObject;
import attendanceapp.model.responseobject.SchoolResponseObject;

public interface SchoolService {
	public SchoolResponseObject getSchool(final long id);

	public List<SchoolResponseObject> getSchoolList();

	public SchoolResponseObject update(final SchoolUpdateRequestObject schoolUpdateRequest);

	public void delete(final long id);

	public void create(final SchoolCreateRequestObject schoolCreateRequestObject);

	public void delete(final DeleteSelectedSchoolRequestObject deleteSelectedSchoolRequestObject);
}
