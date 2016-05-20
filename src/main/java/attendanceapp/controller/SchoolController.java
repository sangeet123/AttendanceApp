package attendanceapp.controller;

import java.util.List;

import attendanceapp.customstatus.Status;
import attendanceapp.model.requestobject.DeleteSchoolsRequestObject;
import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.model.requestobject.SchoolUpdateRequestObject;
import attendanceapp.model.responseobject.SchoolResponseObject;

public interface SchoolController {

	public SchoolResponseObject getSchool(final long id);

	public List<SchoolResponseObject> getSchoolList();

	public Status delete(final long id);

	public Status delete(final DeleteSchoolsRequestObject deleteSelectedSchoolRequestObject);

	public Status create(final SchoolCreateRequestObject schoolRequestObject);

	public SchoolResponseObject update(final SchoolUpdateRequestObject schoolRequestObject);
}
