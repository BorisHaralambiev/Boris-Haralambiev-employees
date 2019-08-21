import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.temporal.ChronoUnit;

public class Employee {
	
	private int EmpID;
	private int ProjectID;
	private LocalDate DateFrom;
	private LocalDate DateTo;
	 
	public Employee() {}

	public int getEmpID() {
		return this.EmpID;
	}
	
	public int getProjectID() {
		return this.ProjectID;
	}
	
	public LocalDate getDateFrom() {
		return this.DateFrom;
	}
	
	public LocalDate getDateTo() {
		return this.DateTo;
	}
	
	@Override
	public String toString() {
	        return ("Emp ID:" + this.getEmpID()+
	                	" Project ID: " + this.getProjectID() +
	                	" Date From: " + this.getDateFrom() +
	                	" Date To : " + this.getDateTo());
	}
	
	public static void main(String[] args) {
		
		ArrayList<Employee> employees = new ArrayList<>();
		try
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			Scanner inFile = new Scanner(new File("LongestPeriod.txt"));
			
			int i = 0;
			
			while (inFile.hasNext()) {
				Employee newEmployee = new Employee();
				newEmployee.EmpID = inFile.nextInt();
				newEmployee.ProjectID = inFile.nextInt();
				
				String dateFrom = inFile.next();
				if(dateFrom .equals("NULL")) {
					newEmployee.DateFrom = LocalDate.now();
				}
				
				else {
				newEmployee.DateFrom = LocalDate.parse(dateFrom,formatter);
				}
				
				String dateTo = inFile.next();
				if(dateTo.equals("NULL") ){
					newEmployee.DateTo = LocalDate.now();
				}
				
				else{
				newEmployee.DateTo = LocalDate.parse(dateTo,formatter);
				}	
				
				employees.add(newEmployee);
				++i;
			}
			inFile.close();
		}
		catch(FileNotFoundException exception) {
			System.out.println("File not found");
		}
		catch (ArrayIndexOutOfBoundsException AIOOBexception){		
			System.out.println("Array Index is out of bounds");
		}      
		catch (IllegalArgumentException IAexception) {
			System.out.println(IAexception);
			Thread.dumpStack();
		}
		
		//currentMax will contain the number of days a pair worked together the longest
		//employeeFirst contains the ID of the First member of the pair
		//employeeSecond contains the ID of the Second member of the pair 
		
		long currentMax = 0;
		int firstEmployee = 0;
		int secondEmployee = 0;
		LocalDate maxStart,minEnd;
		
		for (int i = 0; i < employees.size(); i++) {
		    for (int k = i + 1; k < employees.size(); k++) {
		        if ((employees.get(i) != employees.get(k)) && (employees.get(i).ProjectID == employees.get(k).ProjectID)) {
		        	
		        	//Check for start and end for dates of two date ranges
		        	//Check if they are behind or after for overlap
		        	
		        	if(employees.get(i).getDateFrom().isAfter(employees.get(k).getDateFrom())) {
		        		maxStart = employees.get(i).getDateFrom();
		        	}
		        	else {
		        		maxStart = employees.get(k).getDateFrom();
		        	}
		        	
		        	if(employees.get(i).getDateTo().isBefore(employees.get(k).getDateTo())) {
		        		minEnd = employees.get(i).getDateTo();
		        	}
		        	else {
		        		minEnd = employees.get(k).getDateTo();
		        	}
					
		        	//Calculate days overlap
		        	
		        	long days = ChronoUnit.DAYS.between(maxStart,minEnd);
		        	
		        	//Checking if the current days count is bigger than currentMax
		        	//If true change the currentMax to this current days count
		        	//If true store also the current Employee IDs for the objects respectively for i and k
		        	
		        	if(days>0 && days>currentMax) {
		        		currentMax = days;
		        		firstEmployee = employees.get(i).getEmpID();
		        		secondEmployee = employees.get(k).getEmpID();
		        	}
		            
		        }
		    }
		}
		
		System.out.println("Pair of employees that worked together the most");
		System.out.println("ID of the first employee: " + firstEmployee);
		System.out.println("ID of the Second employee: " + secondEmployee);
		System.out.println("Total number of days: " + currentMax);
	}
}
