package attendanceapp.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import attendanceapp.dao.StaffDao;
import attendanceapp.model.Staff;
import attendanceapp.model.requestobject.DeleteStaffsRequestObject;
import attendanceapp.model.requestobject.StaffCreateRequestObject;
import attendanceapp.model.requestobject.StaffUpdateRequestObject;
import attendanceapp.model.responseobject.StaffResponseObject;
import attendanceapp.services.StaffService;
import attendanceapp.services.util.StaffServiceUtil;

public class StaffServiceImpl implements StaffService {

	@Autowired()
	private StaffDao staffDao;

	@Override()
	public Staff getStaffByUserNameAndPassword(String userName, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override()
	public StaffResponseObject getStaff(long schoolId, long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override()
	public List<StaffResponseObject> getStaffList(long schoolId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override()
	public StaffResponseObject update(long schoolId, StaffUpdateRequestObject staffUpdateRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override()
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

	@Override()
	public StaffResponseObject create(long schoolId, StaffCreateRequestObject staffCreateRequestObject) {
		Staff staff = StaffServiceUtil.createStaffFromStaffCreateRequestObject(schoolId, staffCreateRequestObject);
		staffDao.create(staff);
		return StaffServiceUtil.createStaffResponseObjectFromStaff(staff);
	}

	@Override()
	public void delete(long schoolId, DeleteStaffsRequestObject deleteStaffsRequestObject) {
		// TODO Auto-generated method stub

	}

}
