package attendanceapp.servicesimpl;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import attendanceapp.dao.SchoolDao;
import attendanceapp.exceptions.DuplicateSchoolNameException;
import attendanceapp.exceptions.DuplicateUserNameException;
import attendanceapp.exceptions.SchoolNotFoundException;
import attendanceapp.exceptions.UnknownException;
import attendanceapp.model.School;
import attendanceapp.model.requestobject.DeleteSelectedSchoolRequestObject;
import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.model.requestobject.SchoolUpdateRequestObject;
import attendanceapp.model.responseobject.SchoolResponseObject;
import attendanceapp.services.SchoolService;
import attendanceapp.services.util.SchoolServiceUtils;

@Service()
public class SchoolServiceImpl implements SchoolService {

	@Autowired()
	SchoolDao schoolDao;

	@Override()
	public SchoolResponseObject getSchool(final long id) {
		try {
			School school = schoolDao.getSchool(id);
			return SchoolServiceUtils.createSchoolResponseFromSchool(school);
		} catch (ObjectNotFoundException | NullPointerException ex) {
			throw new SchoolNotFoundException();
		} catch (Exception ex) {
			throw new UnknownException();
		}
	}

	@Override()
	public List<SchoolResponseObject> getSchoolList() {
		try {
			List<School> schools = schoolDao.getSchoolList();
			return SchoolServiceUtils.createListOfSchoolResponseFromListOfSchool(schools);
		} catch (Exception ex) {
			throw new UnknownException();
		}
	}

	@Override()
	public SchoolResponseObject update(final SchoolUpdateRequestObject schoolUpdateRequestObject) {
		try {
			School school = SchoolServiceUtils.createSchoolFromSchoolUpdateRequestObject(schoolUpdateRequestObject);
			schoolDao.update(school);
			return SchoolServiceUtils.createSchoolResponseFromSchool(school);
		} catch (ObjectNotFoundException | NullPointerException ex) {
			throw new SchoolNotFoundException();
		} catch (ConstraintViolationException ex) {
			throw new DuplicateSchoolNameException();
		} catch (Exception ex) {
			throw new UnknownException();
		}
	}

	@Override()
	public void delete(final long id) {
		try {
			schoolDao.delete(id);
		} catch (ObjectNotFoundException | NullPointerException ex) {
			throw new SchoolNotFoundException();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new UnknownException();
		}
	}

	@Override()
	public void create(final SchoolCreateRequestObject schoolRequestObject) {
		try {
			schoolDao.create(schoolRequestObject);
		} catch (DuplicateSchoolNameException | DuplicateUserNameException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new UnknownException(ex.getMessage());
		}
	}

	@Override()
	public void delete(final DeleteSelectedSchoolRequestObject deleteSelectedSchoolRequestObject) {
		try {
			schoolDao.delete(deleteSelectedSchoolRequestObject.getCommaSeparatedIds());
		} catch (Exception ex) {
			throw new UnknownException();
		}
	}
}
