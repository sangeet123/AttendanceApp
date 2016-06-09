package attendanceapp.controller;

import java.util.List;
import attendanceapp.customstatus.Status;
import attendanceapp.model.requestobject.DeleteSubjectsRequestObject;
import attendanceapp.model.requestobject.SubjectCreateRequestObject;
import attendanceapp.model.requestobject.SubjectUpdateRequestObject;
import attendanceapp.model.responseobject.SubjectResponseObject;

public interface SubjectController {

	SubjectResponseObject getSubject(final long schoolId, final long subjectId);

	List<SubjectResponseObject> getSubjectList(final long schoolId);

	Status delete(final long schoolId, final long subjectId);

	Status delete(final long schoolId, final DeleteSubjectsRequestObject deleteSelectedSubjectRequestObject);

	SubjectResponseObject create(final long schoolId, final SubjectCreateRequestObject subjectCreateRequestObject);

	SubjectResponseObject update(final long schoolId, final SubjectUpdateRequestObject subjectUpdateRequestObject);

}
