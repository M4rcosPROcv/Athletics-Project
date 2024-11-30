package Student;


public class Student {

    private int studentID;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String address;
    private String phoneNumber;
    private String major;
    private String registrationYear;
    private boolean graduatedAtMassasoit;
    private boolean transfer;
    private String graduationOrTransferYear;

    public Student(){
        this(0, "", "", "", "", "", "", "", false, false, "");
    }

    public Student(int studentID, String firstName,String lastName, String dateOfBirth, String address, 
                      String phoneNumber, String major,String registrationYear, boolean graduatedAtMassasoit, 
                      boolean transfer, String graduationOrTransferYear){
                         this.studentID = studentID;
                         this.firstName = firstName;
                         this.lastName = lastName;
                         this.dateOfBirth = dateOfBirth;
                         this.address = address;
                         this.phoneNumber = phoneNumber;
                         this.major = major;
                         this.registrationYear = registrationYear;
                         this.graduatedAtMassasoit = graduatedAtMassasoit;
                         this.transfer = transfer;
                         this.graduationOrTransferYear = graduationOrTransferYear;
                      }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getRegistrationYear() {
        return registrationYear;
    }

    public void setRegistrationYear(String registrationYear) {
        this.registrationYear = registrationYear;
    }

    public boolean isGraduatedAtMassasoit() {
        return graduatedAtMassasoit;
    }

    public void setGraduatedAtMassasoit(boolean graduatedAtMassasoit) {
        this.graduatedAtMassasoit = graduatedAtMassasoit;
    }

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    public String getGraduationOrTransferYear() {
        return graduationOrTransferYear;
    }

    public void setGraduationOrTransferYear(String graduationOrTransferYear) {
        this.graduationOrTransferYear = graduationOrTransferYear;
    }

    @Override
    public String toString() {
        return "Student [studentID=" + studentID + ", firstName=" + firstName + ", lastName=" + lastName
                + ", dateOfBirth=" + dateOfBirth + ", address=" + address + ", phoneNumber=" + phoneNumber + ", major="
                + major + ", registrationYear=" + registrationYear + ", graduatedAtMassasoit=" + graduatedAtMassasoit
                + ", transfer=" + transfer + ", graduationOrTransferYear=" + graduationOrTransferYear + "]";
    }
                      
}
