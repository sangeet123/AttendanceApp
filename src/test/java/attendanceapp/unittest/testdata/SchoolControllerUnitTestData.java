package attendanceapp.unittest.testdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import attendanceapp.model.responseobject.SchoolResponseObject;
import attendanceapp.unittest.common.util.AttendanceAppUnitTestUtil;
import attendanceapp.unittest.utils.SchoolControllerUnitTestUtil;

public class SchoolControllerUnitTestData {

	public static final String TESTSCHOOLDATAFILEPATH = "test-school-data";

	public static List<SchoolResponseObject> schoolsResponseObject = null;

	private static void readSchoolFromFile() throws IOException {
		schoolsResponseObject = new ArrayList<SchoolResponseObject>();
		File file = null;
		BufferedReader buffer = null;
		try {
			file = new File(AttendanceAppUnitTestUtil.resourceToString(TESTSCHOOLDATAFILEPATH));
			buffer = new BufferedReader(new FileReader(file));
			String line;
			while ((line = buffer.readLine()) != null) {
				line = line.trim();
				SchoolResponseObject schoolResponseObject = extractSchoolFromLine(line);
				schoolsResponseObject.add(schoolResponseObject);
			}
		} finally {
			if (buffer != null) {
				buffer.close();
			}
		}
	}

	private static String getValueFromKeyVal(String keyVal) {
		int index = keyVal.indexOf(SchoolControllerUnitTestUtil.DELEIMITER);
		String val = keyVal.substring(index + 1).trim();
		return val;
	}

	public static List<SchoolResponseObject> getTestSchoolList() throws IOException {
		if (schoolsResponseObject == null) {
			readSchoolFromFile();
		}
		return schoolsResponseObject;
	}

	public static SchoolResponseObject extractSchoolFromLine(String line) {
		String[] schoolParams = line.split(" ");
		SchoolResponseObject schoolResponseObject = new SchoolResponseObject();
		for (String param : schoolParams) {

			if (param.contains(SchoolControllerUnitTestUtil.ID)) {
				schoolResponseObject.setId(Long.parseLong(getValueFromKeyVal(param)));
			} else if (param.contains(SchoolControllerUnitTestUtil.NAME)) {
				schoolResponseObject.setName(getValueFromKeyVal(param));
			} else if (param.contains(SchoolControllerUnitTestUtil.EMAIL)) {
				schoolResponseObject.setEmail(getValueFromKeyVal(param));
			} else if (param.contains(SchoolControllerUnitTestUtil.TELEPHONE)) {
				schoolResponseObject.setTelephone(getValueFromKeyVal(param));
			}
		}

		return schoolResponseObject;
	}

	public static List<SchoolResponseObject> getSchool(long id) throws IOException {
		if (schoolsResponseObject == null) {
			readSchoolFromFile();
		}
		List<SchoolResponseObject> school = schoolsResponseObject.stream().filter(s -> s.getId() == id)
				.collect(Collectors.toList());
		return school;

	}

}
