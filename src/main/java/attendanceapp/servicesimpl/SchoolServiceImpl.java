package attendanceapp.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import attendanceapp.dao.SchoolDao;
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
	public List<SchoolResponseObject> getSchoolList() {
		List<School> schools = schoolDao.getSchoolList();
		return SchoolServiceUtils.createListOfSchoolResponseFromListOfSchool(schools);
	}

	@Override()
	public SchoolResponseObject getSchool(final long id) {
		School school = schoolDao.getSchool(id);
		return SchoolServiceUtils.createSchoolResponseFromSchool(school);
	}

	@Override()
	public SchoolResponseObject update(final SchoolUpdateRequestObject schoolUpdateRequestObject) {
		School school = SchoolServiceUtils.createSchoolFromSchoolUpdateRequestObject(schoolUpdateRequestObject);
		schoolDao.update(school);
		return SchoolServiceUtils.createSchoolResponseFromSchool(school);
	}

	@Override()
	public void delete(final long id) {
		schoolDao.delete(id);
	}

	@Override()
	public void create(final SchoolCreateRequestObject schoolRequestObject) {
		schoolDao.create(schoolRequestObject);
	}

	@Override()
	public void delete(final DeleteSelectedSchoolRequestObject deleteSelectedSchoolRequestObject) {
		schoolDao.delete(deleteSelectedSchoolRequestObject.getCommaSeparatedIds());
	}
}
