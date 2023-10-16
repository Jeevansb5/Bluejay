package intern.bluejay;


//This class is like helper class to create object ArrayList to store the data.
public class EmployeeDetails {
	String name;
	String id1;
	int date1;
	int time1;
	int min1;
	String id2;
	int date2;
	int time2;
	int min2;

	public EmployeeDetails(String name, String id1, int date1, int time1, int min1, String id2, int date2, int time2,
			int min2) {
		this.name = name;
		this.id1 = id1;
		this.date1 = date1;
		this.time1 = time1;
		this.min1 = min1;
		this.id2 = id2;
		this.date2 = date2;
		this.time2 = time2;
		this.min2 = min2;
	}

	@Override
	public String toString() {

		return "EmployeeDetails[name=" + name + ",id1=" + id1 + ",date1=" + date1 + ",time1=" + time1 + ",min1=" + min1
				+ ",id2=" + id2 + ",date2=" + date2 + ",time2=" + time2 + ",min2=" + min2 + "]";
	}

}
