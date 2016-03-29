package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.Observable;

public class Client extends Application {
    private static String[] cmdArgs;
    private BorderPane layout;
    private GridPane summary;
    private Button download, upload;
    private ListView<String> clientList, serverList;


    @Override
    public void start(Stage primaryStage) throws Exception{
        File resource = new File("./resources");
        if (!resource.exists()) {
          resource.mkdir();
        }
        //uncomment when ready for final product
        /*
        if (cmdArgs.length == 0) {
            System.exit(0);
        }*/

        String computerName;
        String path;
        /*
        if (cmdArgs.length > 0) {
            computerName = cmdArgs[0];
            path = cmdArgs[1];
        }*/

        layout = new BorderPane();
        summary = new GridPane();


        summary.setPadding(new Insets(2, 2, 2, 2));
        summary.setVgap(3);
        summary.setHgap(3);

        clientList = new ListView();
        ObservableList<String> items = FXCollections.observableArrayList();
        File dir = new File("args[1]");
        File[] list = dir.listFiles();

        for(int i =0; i < list.length; i++){
            if(list[i].isFile()){
                if(list[i].getName().endsWith(".txt")){
                    items.add(list[i].getName());
                }
            }
        }

        clientList.setItems(items);
        clientList.setPrefWidth(200);
        clientList.setPrefHeight(400);



        download = new Button("Download");
        download.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        summary.add(download, 0, 0);

        upload = new Button("Upload");
        upload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        summary.add(upload, 1, 0);


        Group root = new Group();
        Scene scene = new Scene(root, 300, 275);
        primaryStage.setTitle("File Sharer v1.0");







        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        cmdArgs = args;
        launch(args);
    }
}
