package attendanceapp.controller;

import java.util.List;
import attendanceapp.customstatus.Status;
import attendanceapp.model.requestobject.DeleteSubjectsRequestObject;
import attendanceapp.model.requestobject.SubjectCreateRequestObject;
import attendanceapp.model.requestobject.SubjectUpdateRequestObject;
import attendanceapp.model.responseobject.SubjectResponseObject;

public interface SubjectController {

	public SubjectResponseObject getSubject(final long schoolId, final long subjectId);

	public List<SubjectResponseObject> getSubjectList(final long schoolId);

	public Status delete(final long schoolId, final long subjectId);

	public Status delete(final long schoolId,
			final DeleteSubjectsRequestObject deleteSelectedSubjectRequestObject);

	public SubjectResponseObject create(final long schoolId,
			final SubjectCreateRequestObject subjectCreateRequestObject);

	public SubjectResponseObject update(final long schoolId,
			final SubjectUpdateRequestObject subjectUpdateRequestObject);

}
