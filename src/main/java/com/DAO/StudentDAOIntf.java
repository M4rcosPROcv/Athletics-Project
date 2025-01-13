package com.DAO;
import java.sql.SQLException;
import java.util.List;

import com.models.Student;
// Student DAO interface
public interface StudentDAOIntf {
    Student getStudent(int studentID) throws SQLException;
    List<Student> getAllStudents() throws SQLException;
    int addStudent(Student student) throws SQLException;
    int updateStudent(Student student) throws SQLException;
    int deleteStudent(Student student) throws SQLException;
}
