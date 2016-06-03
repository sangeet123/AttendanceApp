package attendanceapp.controller;

import java.util.List;

import attendanceapp.customstatus.Status;
import attendanceapp.model.requestobject.DeleteSchoolsRequestObject;
import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.model.requestobject.SchoolUpdateRequestObject;
import attendanceapp.model.responseobject.SchoolResponseObject;

public interface SchoolController {

	SchoolResponseObject getSchool(final long id);

	List<SchoolResponseObject> getSchoolList();

	Status delete(final long id);

	Status delete(final DeleteSchoolsRequestObject deleteSelectedSchoolRequestObject);

	Status create(final SchoolCreateRequestObject schoolRequestObject);

	SchoolResponseObject update(final SchoolUpdateRequestObject schoolRequestObject);
}
