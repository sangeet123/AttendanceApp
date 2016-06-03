package attendanceapp.services;

import java.util.List;

import attendanceapp.model.requestobject.DeleteSchoolsRequestObject;
import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.model.requestobject.SchoolUpdateRequestObject;
import attendanceapp.model.responseobject.SchoolResponseObject;

public interface SchoolService {
	public SchoolResponseObject getSchool(final long id);

	List<SchoolResponseObject> getSchoolList();

	SchoolResponseObject update(final SchoolUpdateRequestObject schoolUpdateRequest);

	void delete(final long id);

	void create(final SchoolCreateRequestObject schoolCreateRequestObject);

	void delete(final DeleteSchoolsRequestObject deleteSelectedSchoolRequestObject);
}
