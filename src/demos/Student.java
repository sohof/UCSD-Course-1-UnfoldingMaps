package demos;
import java.util.*;
/** A class to store information about a Student
 *  Used in module 4 of the UC San Diego MOOC Object Oriented Programming in Java
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * 
 */
public class Student extends Person
{
	public Student(String name)  {
		super(name);
	}

	public boolean isAsleep( int hr ) // override 
	{ 
		return 2 < hr && 8 > hr; 
	}
	
	public static void main(String[] args)
	{
		Random random = new Random();
		
		List<Integer> numsToSort = new ArrayList<>();
		
		for(int i = 0; i < 5; i++) {
			numsToSort.add(random.nextInt(100));
		}
		
		System.out.println(numsToSort);
				
//		Person[] people = new Person[3];
//		people[0] = new Person("ZAdam");
//		people[1] = new Person("KAdam");
//		people[2] = new Person("Adam");
//
//		Integer[] numbers = {6,4,3,2,1};
//		
//		for (Person p : people)
//			System.out.println(p);
//		
//		for (int i : numbers)
//			System.out.println(i);
//		
//		SellSort.mSort(people);
//		SellSort.mSort(numbers);
//		
//		for (Person p : people)
//			System.out.println(p);
//
//		for (int i : numbers)
//			System.out.println(i);
		
//		Person p;
//		p = new Student("Sally");
//		p.status(1);
	}
}
