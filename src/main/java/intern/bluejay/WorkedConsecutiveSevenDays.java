package intern.bluejay;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

public class WorkedConsecutiveSevenDays {
	
	/**
	 * This method will print the Employee Id and Employee Name who worked more than 7 consecutive days 
	 * @param filePath Excel sheet file path
	 */
	public void consecutiveSevenDays(String filePath) throws IOException {
		
		FileInputStream inputStream = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

		HashMap<String, TreeSet<Integer>> workDays = new HashMap<>();
		HashMap<String, String> allEmpIdEmpName = new HashMap<>();

		XSSFSheet sheet = workbook.getSheetAt(0);
		int lastRow = sheet.getLastRowNum();
		int lastColumn = sheet.getRow(0).getLastCellNum();
		
		System.out.println("Employee who worked consecutively 7 Days are given below");
		System.out.println("------------------------------------");
		System.out.println("EmpId\t\tEmpName");
		System.out.println("------------------------------------");
		// here we iterate over the each row and store the Employee name. And number of days employee worked.
		for (int i = 1; i <= lastRow; i++) {
			XSSFRow row = sheet.getRow(i);
			String empId = "";
			String empName = "";
			int date = 0;

			for (int c = 0; c <= lastColumn; c++) {
				XSSFCell cell = row.getCell(c);

				// get employee Id
				if (c == 0) {
					empId = cell.getStringCellValue();
				}
				// get Employee Name and store in allEmpIdEmpName HashMap
				if (c == 7) {
					empName = cell.getStringCellValue();
					allEmpIdEmpName.put(empId, empName);
				}
				// Get the value of 2nd column, which has time in data
				if (c == 2 && cell.getCellType() == NUMERIC) {
					Date javaDate = DateUtil.getJavaDate(cell.getNumericCellValue());
					String d = new SimpleDateFormat("dd").format(javaDate); // extract date from timestamp

					date = Integer.parseInt(d);
					if (workDays.get(empId) == null) {
						TreeSet t = new TreeSet<>();
						t.add(date);
						workDays.put(empId, t);
					} else {
						TreeSet t = workDays.get(empId);
						t.add(date);
					}
				}
			}
		}
		Set<String> keys = workDays.keySet();
		Iterator<String> keyItr = keys.iterator();
		// Calculate who has worked continuously for more than 7 days
		while (keyItr.hasNext()) {
			String empId = keyItr.next();
			ArrayList<Integer> daysList = new ArrayList<>(workDays.get(empId));
			int n = 0;
			if (daysList.size() >= 7) {
				for (int i = 0; i < daysList.size() - 1; i++) {
					if (daysList.get(i + 1) - daysList.get(i) == 1) {
						n++;
					} else {
						n = 0;
					}
				}
			}
			if (n >= 7) {
				String empName = allEmpIdEmpName.get(empId);
				System.out.println(empId+"\t"+empName);
			}
		}
		
		workbook.close();
	}

	public static void main(String[] args) {
		String filePath = "C:\\Users\\jeevansb\\Downloads\\Assignment_Timecard.xlsx";
		WorkedConsecutiveSevenDays workedContinuouslyForSevenDays = new WorkedConsecutiveSevenDays();
		try {
			workedContinuouslyForSevenDays.consecutiveSevenDays(filePath);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
