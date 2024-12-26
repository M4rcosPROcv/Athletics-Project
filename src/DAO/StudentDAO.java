package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Student.Student;

public class StudentDAO implements StudentDAOIntf {
    
    public StudentDAO(){}

    // Returns the student by its student ID
    @Override
    public Student getStudent(int studentID) throws SQLException {

        Student student = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            connection = Database.getConnection();

            String sql = "SELECT * FROM students WHERE studentID = ?";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, studentID);

            rs = ps.executeQuery();
            if(rs.next()){
                int ostudentID = rs.getInt("studentID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String dateOfBirth = rs.getString("dateOfBirth");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phoneNumber");
                String major = rs.getString("major");
                String registrationYear = rs.getString("registrationYear");
                boolean graduatedAtMassasoit = rs.getBoolean("graduatedAtMassasoit");
                boolean transfer = rs.getBoolean("transfer");
                String graduationOrTransferYear = rs.getString("graduationOrTransferYear");

                student = new Student(ostudentID, firstName, lastName, dateOfBirth, address, 
                phoneNumber, major, registrationYear, graduatedAtMassasoit, transfer, graduationOrTransferYear);
            }
            return student;
        }
        finally{
            Database.closeConnections(rs, ps, connection);
        }
    }

    // Returns a list of all students
    @Override
    public List<Student> getAllStudents() throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            connection = Database.getConnection();
            String sql = "SELECT * FROM students ORDER BY studentID";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            return resultSetToStudents(rs);
        }
        finally{
            Database.closeConnections(rs, ps, connection);
        }
    }

    // Adds a student to the database
    @Override
    public int addStudent(Student student) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        
        try{
            connection = Database.getConnection();
            String sql = "INSERT INTO students (studentID, firstName, lastName, dateOfBirth, address," +
            " phoneNumber, major, registrationYear, graduatedAtMassasoit, transfer, graduationOrTransferYear)" + 
            " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, student.getStudentID());
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getLastName());
            ps.setString(4, student.getDateOfBirth());
            ps.setString(5, student.getAddress());
            ps.setString(6, student.getPhoneNumber());
            ps.setString(7, student.getMajor());
            ps.setString(8, student.getRegistrationYear());
            ps.setBoolean(9, student.isGraduatedAtMassasoit());
            ps.setBoolean(10, student.isTransfer());
            ps.setString(11, student.getGraduationOrTransferYear());
            int rs = ps.executeUpdate();

            return rs;
        }
        finally {
            Database.closeConnections(null, ps, connection);
        }
    }

    // Updates a student in the database
    @Override
    public int updateStudent(Student student) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;

        try{
            connection = Database.getConnection();
            String sql = "UPDATE students SET firstName =?, lastName =?, dateOfBirth =?, address =?, " +
            "phoneNumber =?, major =?, registrationYear =?, graduatedAtMassasoit =?, transfer =?, " +
            "graduationOrTransferYear =? WHERE studentID =?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getDateOfBirth());
            ps.setString(4, student.getAddress());
            ps.setString(5, student.getPhoneNumber());
            ps.setString(6, student.getMajor());
            ps.setString(7, student.getRegistrationYear());
            ps.setBoolean(8, student.isGraduatedAtMassasoit());
            ps.setBoolean(9, student.isTransfer());
            ps.setString(10, student.getGraduationOrTransferYear());
            ps.setInt(11, student.getStudentID());

            int rs = ps.executeUpdate();
            return rs;
        }
        finally{
            Database.closeConnections(null, ps, connection);
        }
    }

    // Deletes a student from the database
    @Override
    public int deleteStudent(Student student) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;

        try{
            connection = Database.getConnection();
            String sql = "DELETE FROM students WHERE studentID =?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, student.getStudentID());

            int rs = ps.executeUpdate();
            return rs;
        }
        finally{
            Database.closeConnections(null, ps, connection);
        }
    }

    /**
     * Receives a ResultSet as param and returns a List with all the objects in the result set
     * @param rs the ResultSet
     * @return a List containing all the objects in the result set
     * @throws SQLException
     */
    private List<Student> resultSetToStudents(ResultSet rs) throws SQLException {
        ArrayList<Student> students = new ArrayList<Student>();

        while(rs.next()){
            Student student = new Student();
            student.setStudentID(rs.getInt("studentID"));
            student.setFirstName(rs.getString("firstName"));
            student.setLastName(rs.getString("lastName"));
            student.setDateOfBirth(rs.getString("dateOfBirth"));
            student.setAddress(rs.getString("address"));
            student.setPhoneNumber(rs.getString("phoneNumber"));
            student.setMajor(rs.getString("major"));
            student.setRegistrationYear(rs.getString("registrationYear"));
            student.setGraduatedAtMassasoit(rs.getBoolean("graduatedAtMassasoit"));
            student.setTransfer(rs.getBoolean("transfer"));
            student.setGraduationOrTransferYear(rs.getString("graduationOrTransferYear"));
            students.add(student);
        }
        return students;
    }

}
