package intern.bluejay;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkedMoreThan14Hours {

	public ArrayList<String> workedMoreThanFourteenHrs(String excelFilePathString) throws Exception {
		FileInputStream inputStream = new FileInputStream(excelFilePathString);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		int rows = sheet.getLastRowNum();
		int cols = sheet.getRow(0).getLastCellNum();

		ArrayList<String> personIds = new ArrayList<>();// for storing the persons who has worked more than 14 hrs

		String id = null;
		for (int r = 2; r <= rows; r++) {
			XSSFRow row = sheet.getRow(r);

			for (int c = 0; c < cols; c++) {
				XSSFCell cell = row.getCell(c);
				if (c == 0) {
					id = cell.getStringCellValue(); // Get the person ID
				}
				// Get the shift time and check it is more than 14 hours
				if (c == 4 && !cell.getStringCellValue().isEmpty() && cell.getCellType() == CellType.STRING) {
					try {
						String cellValue = cell.getStringCellValue();
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
						Date dt = sdf.parse(cellValue);
						sdf = new SimpleDateFormat("HH");
						String hour = sdf.format(dt);
						if (Integer.parseInt(hour) >= 14) {
							personIds.add(id);
						}

					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(cell.getStringCellValue());
					}

				}
			}
		}
		workbook.close();
		return personIds;
	}

	public static void main(String[] args) throws Exception {
		String filePath = "C:\\Users\\jeevansb\\Downloads\\Assignment_Timecard.xlsx";
		WorkedMoreThan14Hours workedFourteenHours = new WorkedMoreThan14Hours();
		System.out.println("The Position ID of employee who worked more than 14 hours in a single shift is : "
				+ workedFourteenHours.workedMoreThanFourteenHrs(filePath));
	}
}
