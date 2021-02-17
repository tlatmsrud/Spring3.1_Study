package spring.co.kr.test;

public class 제네릭 {
	public static void main(String[] args) {
		
	    Person<StudentInfo> sp = new Person<StudentInfo>(new StudentInfo(2));
	    System.out.println(sp.info.grade);
	}
}
class StudentInfo{
    public int grade;
    StudentInfo(int grade){ this.grade = grade; }
}
/*
 * class StudentPerson{ public StudentInfo info; StudentPerson(StudentInfo
 * info){ this.info = info; } }
 */

class EmployeeInfo{
    public int rank;
    EmployeeInfo(int rank){ this.rank = rank; }
}

/*
 * class EmployeePerson{ public EmployeeInfo info; EmployeePerson(EmployeeInfo
 * info){ this.info = info; } }
 */
class Person<T>{

	public T info;

	public Person(T info){
		this.info = info;
	}
}

