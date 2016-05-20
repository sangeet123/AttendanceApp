package attendanceapp.unittest.testdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import attendanceapp.model.responseobject.SubjectResponseObject;
import attendanceapp.unitest.common.util.AttendanceAppUnitTestUtil;
import attendanceapp.unittest.utils.SubjectControllerUnitTestUtil;

public class SubjectControllerUnitTestData {

	public static final String TESTSUBJECTDATAFILEPATH = "test-subject-data";

	public static List<SubjectResponseObject> subjectsResponseObject = null;

	private static void readsubjectFromFile() throws IOException {
		subjectsResponseObject = new ArrayList<SubjectResponseObject>();
		File file = null;
		BufferedReader buffer = null;
		try {
			file = new File(AttendanceAppUnitTestUtil.resourceToString(TESTSUBJECTDATAFILEPATH));
			buffer = new BufferedReader(new FileReader(file));
			String line;
			while ((line = buffer.readLine()) != null) {
				line = line.trim();
				SubjectResponseObject subjectResponseObject = extractsubjectFromLine(line);
				subjectsResponseObject.add(subjectResponseObject);
			}
		} finally {
			if (buffer != null) {
				buffer.close();
			}
		}
	}

	private static String getValueFromKeyVal(String keyVal) {
		int index = keyVal.indexOf(SubjectControllerUnitTestUtil.DELEIMITER);
		String val = keyVal.substring(index + 1).trim();
		return val;
	}

	public static List<SubjectResponseObject> getTestSubjectList() throws IOException {
		if (subjectsResponseObject == null) {
			readsubjectFromFile();
		}
		return subjectsResponseObject;
	}

	public static SubjectResponseObject extractsubjectFromLine(String line) {
		String[] subjectParams = line.split(" ");
		SubjectResponseObject subjectResponseObject = new SubjectResponseObject();
		for (String param : subjectParams) {

			if (param.contains(SubjectControllerUnitTestUtil.ID)) {
				subjectResponseObject.setId(Long.parseLong(getValueFromKeyVal(param)));
			} else if (param.contains(SubjectControllerUnitTestUtil.NAME)) {
				subjectResponseObject.setName(getValueFromKeyVal(param));
			} else if (param.contains(SubjectControllerUnitTestUtil.SHORTNAME)) {
				subjectResponseObject.setShortName(getValueFromKeyVal(param));
			} else if (param.contains(SubjectControllerUnitTestUtil.CREDIT)) {
				subjectResponseObject.setCredit(Integer.parseInt(getValueFromKeyVal(param)));
			}
		}

		return subjectResponseObject;
	}

	public static List<SubjectResponseObject> getSubject(long id) throws IOException {
		if (subjectsResponseObject == null) {
			readsubjectFromFile();
		}
		List<SubjectResponseObject> subject = subjectsResponseObject.stream().filter(s -> s.getId() == id)
				.collect(Collectors.toList());
		return subject;

	}

}
