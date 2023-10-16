package intern.bluejay;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

public class ShiftTimeLessThan10Hours {
	
	/**
	 * This method will print the Employee Id and Employee Name who have less than 10 hours of time between shifts but greater than 1 hour
	 * @param path Excel Sheet path to read.
	 * @throws IOException 
	 */
	public void shiftLessThanTenHours(String path) throws IOException {

		/**
		 * To store the Employee details.
		 */
		ArrayList<EmployeeDetails> list = new ArrayList<EmployeeDetails>();

		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(path));
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		int lastRow = sheet.getLastRowNum();

		// for loop to traverse the excel sheet.
		for (int i = 2; i <= lastRow; i++) {

			XSSFRow row = sheet.getRow(i);
			XSSFRow row2 = sheet.getRow(i - 1);

			String name = null;
			String id1 = null;
			int date1 = 0;
			int time1 = 0;
			int min1 = 0;

			String id2 = null;
			int date2 = 0;
			int time2 = 0;
			int min2 = 0;

			XSSFCell cell = row.getCell(2);
			XSSFCell cell2 = row2.getCell(3);
			XSSFCell empidCell1 = row.getCell(0);
			XSSFCell empidCell2 = row2.getCell(0);
			XSSFCell empNameCell = row2.getCell(7);

			if (cell.getCellType() == NUMERIC && cell2.getCellType() == NUMERIC) {

				name = empNameCell.getStringCellValue(); // to get Employee Name.
				
				// to get Employee Id.
				id1 = empidCell1.getStringCellValue();
				id2 = empidCell2.getStringCellValue();
				
				//to get Date and time .
				Date javaDate = DateUtil.getJavaDate(cell.getNumericCellValue());
				String d = new SimpleDateFormat("dd").format(javaDate);
				String h = new SimpleDateFormat("HH").format(javaDate);
				String m = new SimpleDateFormat("mm").format(javaDate);

				Date javaDate2 = DateUtil.getJavaDate(cell2.getNumericCellValue());
				String d2 = new SimpleDateFormat("dd").format(javaDate2);
				String h2 = new SimpleDateFormat("HH").format(javaDate2);
				String m2 = new SimpleDateFormat("mm").format(javaDate2);

				min1 = Integer.parseInt(m);
				time1 = Integer.parseInt(h);
				date1 = Integer.parseInt(d);

				min2 = Integer.parseInt(m2);
				time2 = Integer.parseInt(h2);
				date2 = Integer.parseInt(d2);

				EmployeeDetails dt = new EmployeeDetails(name, id1, date1, time1, min1, id2, date2, time2, min2);
				list.add(dt);

			}
		}
		
		System.out.println("--------------------------------------------------");
		System.out.println("Employee Id\tTime Gap\tEmployee Name");
		System.out.println("--------------------------------------------------");
		for (EmployeeDetails dat : list) {

			if (dat.date1 == dat.date2 && dat.id1 == dat.id2) {
				int minutes = (dat.time1 * 60 + (dat.min1)) - (dat.time2 * 60 + (dat.min2));

				double res = minutes / 60;

				if (res > 1 && res < 10) {
					System.out.println(dat.id1 + "\t" + res + "\t" + dat.name);
				}

			}

		}

	}

	public static void main(String[] args) {
		String filePath = "C:\\Users\\jeevansb\\Downloads\\Assignment_Timecard.xlsx";
		ShiftTimeLessThan10Hours hrs = new ShiftTimeLessThan10Hours();
		try {
			hrs.shiftLessThanTenHours(filePath);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
