package attendanceapp.daoimpl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import attendanceapp.constants.Constant;
import attendanceapp.dao.StaffDao;
import attendanceapp.dao.validator.StaffDaoValidator;
import attendanceapp.exceptions.NotFoundException;
import attendanceapp.exceptions.UnknownException;
import attendanceapp.model.Staff;
import attendanceapp.model.requestobject.StaffUpdateRequestObject;
import attendanceapp.services.util.StaffServiceUtil;

@Repository()
public class StaffDaoImpl implements StaffDao {

	private static final String DELETE_STAFF_BY_ID = "delete attendanceapp.model.Staff where id= :staffId and school.id= :schoolId";
	private static final String DELETE_STAFFS_BY_IDS = "delete attendanceapp.model.Staff where id in (:staffIds) and school.id= :schoolId";
	private final Logger logger = LoggerFactory.getLogger(StaffDaoImpl.class);

	@Autowired()
	private SessionFactory sessionFactory;

	@Autowired()
	private StaffDaoValidator validator;

	Session session = null;
	Transaction transaction = null;

	private void closeSession() {
		if (session != null) {
			session.close();
		}
	}

	private Staff findStaff(final long schoolId, final long staffId) {
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Staff.class);
			criteria.add(Restrictions.eq("school.id", schoolId)).add(Restrictions.eq("id", staffId));
			@SuppressWarnings("rawtypes")
			List staffs = criteria.list();
			if (staffs.isEmpty()) {
				throw new NotFoundException(Constant.RESOURSE_DOES_NOT_EXIST);
			}
			return (Staff) staffs.get(0);
		} finally {
			closeSession();
		}
	}

	@Override()
	public Staff getStaff(long schoolId, long staffId) {
		try {
			return findStaff(schoolId, staffId);
		} catch (NotFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override()
	public List<Staff> getStaffs(long schoolId) {
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Staff.class);
			criteria.add(Restrictions.eq("school.id", schoolId));
			return criteria.list();
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	@Override()
	public Staff update(long schoolId, final StaffUpdateRequestObject request) {
		validator.validateStaffUpdateRequestObject(schoolId, request);
		Staff originalStaff = this.getStaff(schoolId, request.getId());
		StaffServiceUtil.copyAttributes(originalStaff, request);
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(originalStaff);
			transaction.commit();
			return originalStaff;
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	@Override()
	public void delete(long schoolId, long staffId) {
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(DELETE_STAFF_BY_ID).setParameter("staffId", staffId)
					.setParameter("schoolId", schoolId);
			if (query.executeUpdate() == 0) {
				throw new NotFoundException(Constant.RESOURSE_DOES_NOT_EXIST);
			}
		} catch (NotFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	@Override()
	public void delete(long schoolId, String ids) {
		try {
			List<Long> numbers = Stream.of(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());
			session = sessionFactory.openSession();
			Query query = session.createQuery(DELETE_STAFFS_BY_IDS).setParameter("schoolId", schoolId)
					.setParameterList("staffIds", numbers);
			query.executeUpdate();
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	@Override()
	public void create(Staff staff) {
		validator.validateStaff(staff);
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(staff);
			transaction.commit();
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}

	}

}
