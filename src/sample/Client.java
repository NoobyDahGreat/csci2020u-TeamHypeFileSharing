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
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Optional;

public class Client extends Application {
    private static String[] cmdArgs;
    private BorderPane layout;
    private GridPane summary;
    private Button download, upload;
    private ListView<String> clientList, serverList;


    @Override
    public void start(Stage primaryStage) throws Exception{
        // Get hostname from user
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("File Sharer v1.0");
        dialog.setHeaderText("Setup Phase");
        dialog.setContentText("Please enter the name of your computer:");

       // Traditional way to get the response value.
        String computerName = " ";
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            computerName = result.get();
        }

        // Dialog promting user to choose dir
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File sharedDirectory = directoryChooser.showDialog(primaryStage);
        String sharedDir = sharedDirectory.getAbsolutePath();

        RequestSender requestSender = new RequestSender(computerName, 8081, sharedDir);

        summary = new GridPane();


        summary.setPadding(new Insets(2, 2, 2, 2));
        summary.setVgap(3);
        summary.setHgap(3);

        clientList = new ListView();
        ObservableList<String> items = FXCollections.observableArrayList();
        File[] list = sharedDirectory.listFiles();

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
        summary.add(clientList, 0, 1);

        String item = clientList.getSelectionModel().getSelectedItem();

        serverList = new ListView();
        ObservableList<String> serverItems = requestSender.dir();

        serverList.setItems(null);
        serverList.setPrefHeight(400);
        serverList.setPrefWidth(200);
        summary.add(serverList, 1, 1);

        download = new Button("Download");
        download.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(item);
                requestSender.download(item);
            }
        });
        summary.add(download, 0, 0);

        upload = new Button("Upload");
        upload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                requestSender.upload("./client/get_this_you_shit.txt");
            }
        });
        summary.add(upload, 1, 0);



        layout = new BorderPane();
        layout.setCenter(summary);



        Group root = new Group();
        Scene scene = new Scene(layout, 500, 475);
        primaryStage.setTitle("File Sharer v1.0");







        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        cmdArgs = args;
        launch(args);
    }
}
