package attendanceapp.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import attendanceapp.dao.StaffDao;
import attendanceapp.model.Staff;
import attendanceapp.model.requestobject.DeleteStaffsRequestObject;
import attendanceapp.model.requestobject.StaffCreateRequestObject;
import attendanceapp.model.requestobject.StaffUpdateRequestObject;
import attendanceapp.model.responseobject.StaffResponseObject;
import attendanceapp.services.StaffService;
import attendanceapp.services.util.StaffServiceUtil;

@Service()
public class StaffServiceImpl implements StaffService {

	@Autowired()
	private StaffDao staffDao;

	@Override()
	public Staff getStaffByUserNameAndPassword(String userName, String password) {
		return null;
	}

	@Override()
	public StaffResponseObject getStaff(long schoolId, long staffId) {
		Staff staff = staffDao.getStaff(schoolId, staffId);
		return StaffServiceUtil.createStaffResponseObjectFromStaff(staff);
	}

	@Override()
	public List<StaffResponseObject> getStaffList(long schoolId) {
		List<Staff> staffs = staffDao.getStaffs(schoolId);
		return StaffServiceUtil.createStaffResponseObjectListFromStaffList(staffs);
	}

	@Override()
	public StaffResponseObject update(long schoolId, StaffUpdateRequestObject staffUpdateRequest) {
		return StaffServiceUtil.createStaffResponseObjectFromStaff(staffDao.update(schoolId, staffUpdateRequest));
	}

	@Override()
	public void delete(final long schoolId, final long staffId) {
		staffDao.delete(schoolId, staffId);

	}

	@Override()
	public StaffResponseObject create(long schoolId, StaffCreateRequestObject staffCreateRequestObject) {
		Staff staff = StaffServiceUtil.createStaffFromStaffCreateRequestObject(schoolId, staffCreateRequestObject);
		staffDao.create(staff);
		return StaffServiceUtil.createStaffResponseObjectFromStaff(staff);
	}

	@Override()
	public void delete(long schoolId, DeleteStaffsRequestObject deleteStaffsRequestObject) {
		staffDao.delete(schoolId, deleteStaffsRequestObject.getCommaSeparatedIds());
	}

}
