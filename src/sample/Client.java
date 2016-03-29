package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Client extends Application {
    private static String[] cmdArgs;
    private BorderPane layout;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 300, 275);
        primaryStage.setTitle("File Sharer v1.0");


        if (cmdArgs.length == 0) {
            System.exit(0);
        }

        String computerName;
        String path;
        if (cmdArgs.length > 0) {
            computerName = cmdArgs[0];
            path = cmdArgs[1];
        }



        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        cmdArgs = args;
        launch(args);
    }
}
