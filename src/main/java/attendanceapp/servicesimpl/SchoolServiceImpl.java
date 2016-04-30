package attendanceapp.servicesimpl;

import java.util.ArrayList;
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
import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.model.requestobject.SchoolUpdateRequestObject;
import attendanceapp.model.responseobject.SchoolResponseObject;
import attendanceapp.modeltoresponseobjectmapper.SchoolToSchoolResponseMapper;
import attendanceapp.requestobjecttomodelmapper.SchoolUpdateRequestToSchoolMapper;
import attendanceapp.services.SchoolService;

@Service()
public class SchoolServiceImpl implements SchoolService {

	@Autowired()
	SchoolDao schoolDao;

	private SchoolResponseObject createSchoolResponseFromSchool(School school) {
		return new SchoolToSchoolResponseMapper.SchoolResponseBuilder().id(school.getId()).email(school.getEmail())
				.name(school.getName()).telephone(school.getTelephone()).build();
	}

	private School createSchoolFromSchoolUpdateRequestObject(SchoolUpdateRequestObject schoolUpdateRequestObject) {
		return new SchoolUpdateRequestToSchoolMapper.SchoolBuilder().id(schoolUpdateRequestObject.getId())
				.email(schoolUpdateRequestObject.getEmail()).name(schoolUpdateRequestObject.getName())
				.telephone(schoolUpdateRequestObject.getTelephone()).build();
	}

	private List<SchoolResponseObject> createListOfSchoolResponseFromListOfSchool(List<School> schools) {
		List<SchoolResponseObject> schoolsResponseObject = new ArrayList<SchoolResponseObject>();
		schools.forEach(school -> {
			schoolsResponseObject.add(createSchoolResponseFromSchool(school));
		});
		return schoolsResponseObject;
	}

	@Override()
	public SchoolResponseObject getSchool(final long id) {
		try {
			School school = schoolDao.getSchool(id);
			return createSchoolResponseFromSchool(school);
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
			return createListOfSchoolResponseFromListOfSchool(schools);
		} catch (Exception ex) {
			throw new UnknownException();
		}
	}

	@Override()
	public void update(final SchoolUpdateRequestObject schoolUpdateRequestObject) {
		try {
			School school = createSchoolFromSchoolUpdateRequestObject(schoolUpdateRequestObject);
			schoolDao.update(school);
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
	public void delete(final String ids) {
		try {
			schoolDao.delete(ids);
		} catch (Exception ex) {
			throw new UnknownException();
		}
	}
}
