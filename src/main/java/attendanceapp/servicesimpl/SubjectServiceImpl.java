package attendanceapp.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import attendanceapp.dao.SubjectDao;
import attendanceapp.exceptions.SubjectNotFoundException;
import attendanceapp.exceptions.UnknownException;
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
		try {
			Subject subject = subjectDao.getSubject(schoolId, subjectId);
			return SubjectServiceUtil.createSubjectResponseObjectFromSubject(subject);
		} catch (SubjectNotFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new UnknownException();
		}
	}

	@Override()
	public List<SubjectResponseObject> getSubjectList(final long schoolId) {
		try {
			List<Subject> subjects = subjectDao.getSubjects(schoolId);
			return SubjectServiceUtil.createSubjectResponseObjectListFromSubjectList(subjects);
		} catch (Exception ex) {
			throw new UnknownException();
		}
	}

	@Override()
	public SubjectResponseObject update(final long schoolId,
			final SubjectUpdateRequestObject subjectUpdateRequestObject) {
		try {
			Subject subject = SubjectServiceUtil.creaSubjectFromSubjectUpdateRequestObject(subjectUpdateRequestObject);
			subjectDao.update(schoolId, subject);
			return SubjectServiceUtil.createSubjectResponseObjectFromSubject(subject);
		} catch (Exception ex) {
			throw new UnknownException();
		}
	}

	@Override()
	public void delete(final long schoolId, final long subjectId) {
		try {
			subjectDao.delete(schoolId, subjectId);
		} catch (SubjectNotFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new UnknownException();
		}
	}

	@Override()
	public SubjectResponseObject create(final long schoolId,
			final SubjectCreateRequestObject subjectCreateRequestObject) {
		try {
			Subject subject = SubjectServiceUtil.creaSubjectFromSubjectCreateRequestObject(subjectCreateRequestObject);
			subjectDao.update(schoolId, subject);
			return SubjectServiceUtil.createSubjectResponseObjectFromSubject(subject);
		} catch (Exception ex) {
			throw new UnknownException();
		}

	}

	@Override()
	public void delete(long schoolId, DeleteSubjectsRequestObject deleteSelectedSchoolRequestObject) {
		try {
			subjectDao.delete(schoolId, deleteSelectedSchoolRequestObject.getCommaSeparatedIds());
		} catch (Exception ex) {
			throw new UnknownException();
		}
	}

}
