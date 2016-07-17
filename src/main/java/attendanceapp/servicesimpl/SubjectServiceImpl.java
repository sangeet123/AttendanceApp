package attendanceapp.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import attendanceapp.dao.SubjectDao;
import attendanceapp.model.Subject;
import attendanceapp.model.requestobject.DeleteSubjectsRequestObject;
import attendanceapp.model.requestobject.SubjectCreateRequestObject;
import attendanceapp.model.requestobject.SubjectUpdateRequestObject;
import attendanceapp.model.responseobject.SubjectResponseObject;
import attendanceapp.services.SubjectService;
import attendanceapp.services.util.SubjectServiceUtil;

@Service()
public class SubjectServiceImpl implements SubjectService {

	@Autowired()
	SubjectDao subjectDao;

	@Override()
	public SubjectResponseObject getSubject(final long schoolId, final long subjectId) {
		Subject subject = subjectDao.getSubject(schoolId, subjectId);
		return SubjectServiceUtil.createSubjectResponseObjectFromSubject(subject);
	}

	@Override()
	public List<SubjectResponseObject> getSubjectList(final long schoolId) {
		List<Subject> subjects = subjectDao.getSubjects(schoolId);
		return SubjectServiceUtil.createSubjectResponseObjectListFromSubjectList(subjects);
	}

	@Override()
	public SubjectResponseObject update(final long schoolId,
			final SubjectUpdateRequestObject subjectUpdateRequestObject) {
		return SubjectServiceUtil
				.createSubjectResponseObjectFromSubject(subjectDao.update(schoolId, subjectUpdateRequestObject));
	}

	@Override()
	public void delete(final long schoolId, final long subjectId) {
		subjectDao.delete(schoolId, subjectId);
	}

	@Override()
	public SubjectResponseObject create(final long schoolId,
			final SubjectCreateRequestObject subjectCreateRequestObject) {
		Subject subject = SubjectServiceUtil.createSubjectFromSubjectCreateRequestObject(schoolId,
				subjectCreateRequestObject);
		subjectDao.create(subject);
		return SubjectServiceUtil.createSubjectResponseObjectFromSubject(subject);
	}

	@Override()
	public void delete(long schoolId, DeleteSubjectsRequestObject deleteSelectedSchoolRequestObject) {
		subjectDao.delete(schoolId, deleteSelectedSchoolRequestObject.getCommaSeparatedIds());
	}

}
