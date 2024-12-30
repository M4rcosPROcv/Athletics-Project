package Main;

public class App {
    public static void main(String[] args) {
        Main.main(args);
    }
}
//jlink --module-path C:\Users\mdpra\OneDrive\Documents\Marcos\SelfLearning\app --add-modules java.base,java.sql,javafx.base,javafx.controls,javafx.graphics,jakarta.mail,com.google.gson,org.bouncycastle.provider --output C:\Users\mdpra\OneDrive\Documents\Marcos\SelfLearning\packaging\jre --ignore-signing-information
//jpackage --type exe --input C:\Users\mdpra\OneDrive\Documents\Marcos\SelfLearning\app --main-jar Athletics_Project.jar --main-class Main.Main1 --name packtest2 --runtime-image C:\Users\mdpra\OneDrive\Documents\Marcos\SelfLearning\packaging\jre --app-version 1.0.0 --icon C:\Users\mdpra\Downloads\icon.ico --dest C:\Users\mdpra\OneDrive\Documents\Marcos\SelfLearning