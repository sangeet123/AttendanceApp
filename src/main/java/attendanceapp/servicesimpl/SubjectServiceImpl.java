package attendanceapp.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import attendanceapp.dao.SubjectDao;
import attendanceapp.exceptions.SubjectNotFoundException;
import attendanceapp.exceptions.UnknownException;
import attendanceapp.model.Subject;
import attendanceapp.model.requestobject.DeleteSelectedSubjectRequestObject;
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
	public SubjectResponseObject getSubject(long schoolId, long subjectId) {
		try {
			if (!subjectDao.isValidPair(schoolId, subjectId)) {
				throw new SubjectNotFoundException();
			}
			Subject subject = subjectDao.getSubject(schoolId, subjectId);
			return SubjectServiceUtil.createSubjectResponseObjectFromSubject(subject);
		} catch (Exception ex) {
			throw new UnknownException();
		}
	}

	@Override()
	public List<SubjectResponseObject> getSubjectList(long schoolId) {
		try {
			List<Subject> subjects = subjectDao.getSubjects(schoolId);
			return SubjectServiceUtil.createSubjectResponseObjectListFromSubjectList(subjects);
		} catch (Exception ex) {
			throw new UnknownException();
		}
	}

	@Override()
	public SubjectResponseObject update(long schoolId, SubjectUpdateRequestObject subjectUpdateRequestObject) {
		try {
			if (!subjectDao.isValidPair(schoolId, subjectUpdateRequestObject.getId())) {
				throw new SubjectNotFoundException();
			}
			Subject subject = SubjectServiceUtil.creaSubjectFromSubjectUpdateRequestObject(subjectUpdateRequestObject);
			subjectDao.update(schoolId, subject);
			return SubjectServiceUtil.createSubjectResponseObjectFromSubject(subject);
		} catch (Exception ex) {
			throw new UnknownException();
		}
	}

	@Override()
	public void delete(long schoolId, long subjectId) {
		try {
			if (!subjectDao.isValidPair(schoolId, subjectId)) {
				throw new SubjectNotFoundException();
			}
			subjectDao.delete(schoolId, subjectId);
		} catch (Exception ex) {
			throw new UnknownException();
		}
	}

	@Override()
	public SubjectResponseObject create(long schoolId, SubjectCreateRequestObject subjectCreateRequestObject) {
		try {
			Subject subject = SubjectServiceUtil.creaSubjectFromSubjectCreateRequestObject(subjectCreateRequestObject);
			subjectDao.update(schoolId, subject);
			return SubjectServiceUtil.createSubjectResponseObjectFromSubject(subject);
		} catch (Exception ex) {
			throw new UnknownException();
		}

	}

	@Override()
	public void delete(long schoolId, DeleteSelectedSubjectRequestObject deleteSelectedSchoolRequestObject) {
		try {
			subjectDao.delete(schoolId, deleteSelectedSchoolRequestObject.getCommaSeparatedIds());
		} catch (Exception ex) {
			throw new UnknownException();
		}
	}

}
