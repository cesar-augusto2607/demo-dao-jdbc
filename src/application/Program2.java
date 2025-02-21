package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		/*System.out.println("TEST INSERT: ");
		Department newDepartment = new Department(null, "music");
		departmentDao.insert(newDepartment);
		System.out.println("Insert! new id = " + newDepartment.getId());*/
		
		
		System.out.println("TEST FindAll: ");
		List<Department> list = departmentDao.findAll();
		for(Department department : list) {
			System.out.println(department);
		}
		
		System.out.println("TEST findById: ");
		Department department = departmentDao.findById(2);
		System.out.println(department);
		
		
		System.out.println("TEST Update: ");
		department = departmentDao.findById(6);
		department.setName("D3");
		departmentDao.update(department);
		System.out.println("Update completed! " + department.getName());
		
		
		System.out.println("TEST deleteById: ");
		System.out.print("Enter an id: ");
		int id = sc.nextInt();
		departmentDao.deletebyId(id);
		System.out.println("Department successfully deleted! ");
		
	}

}
