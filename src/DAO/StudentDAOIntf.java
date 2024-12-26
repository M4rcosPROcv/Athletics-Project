package DAO;
import java.sql.SQLException;
import java.util.List;

import Student.Student;
// Student DAO interface
public interface StudentDAOIntf {
    Student getStudent(int studentID) throws SQLException;
    List<Student> getAllStudents() throws SQLException;
    int addStudent(Student student) throws SQLException;
    int updateStudent(Student student) throws SQLException;
    int deleteStudent(Student student) throws SQLException;
}
