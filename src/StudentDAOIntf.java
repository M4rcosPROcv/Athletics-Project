import java.sql.SQLException;
import java.util.List;

public interface StudentDAOIntf {
    Student getStudent(int studentID) throws SQLException;
    List<Student> getAllStudents() throws SQLException;
    int addStudent(Student student) throws SQLException;
    int updateStudent(Student student) throws SQLException;
    int deleteStudent(Student student) throws SQLException;
}
