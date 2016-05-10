package attendanceapp.services;

import java.util.List;

import attendanceapp.model.requestobject.DeleteSubjectsRequestObject;
import attendanceapp.model.requestobject.SubjectCreateRequestObject;
import attendanceapp.model.requestobject.SubjectUpdateRequestObject;
import attendanceapp.model.responseobject.SubjectResponseObject;

public interface SubjectService {

	public SubjectResponseObject getSubject(final long schoolId, final long subjectId);

	public List<SubjectResponseObject> getSubjectList(final long schoolId);

	public SubjectResponseObject update(final long schoolId,
			final SubjectUpdateRequestObject subjectUpdateRequestObject);

	public void delete(final long schoolId, final long subjectId);

	public SubjectResponseObject create(final long schoolId,
			final SubjectCreateRequestObject subjectCreateRequestObject);

	public void delete(final long schoolId, final DeleteSubjectsRequestObject deleteSelectedSchoolRequestObject);
}
