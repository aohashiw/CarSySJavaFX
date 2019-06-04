package view;

import controller.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entity.Record;
import model.entity.Vehicle;
import model.util.JdbcUtils;



public class SingleVehicleView {


    public static void fresh(Stage stage,int id){
        Vehicle v = JdbcUtils.selectVehicleById(id);
        ListView l = (ListView)stage.getScene().lookup("#list");
        Label status = (Label)stage.getScene().lookup("#status");
        l.setItems(FXCollections.observableArrayList(JdbcUtils.selectRecordByVid(id)));
        status.setText("Status: "+v.getStatus());
    }

    public static void show(Integer id){
        Stage detailStage = new Stage();
        detailStage.setTitle(id.toString());
        Image logo = new Image("file:"+MainView.path+"\\images\\logo.png");
        detailStage.getIcons().add(logo);


        Vehicle v = JdbcUtils.selectVehicleById(id);

        BorderPane borderPane = new BorderPane();


        // Images
        String url = "file:"+MainView.path+"\\images\\"+v.getImg();
        Image image = new Image(url);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(660);
        imageView.setFitHeight(360);

        //Label
        VBox vBox = new VBox(26);
        vBox.setPadding(new Insets(14));
        Label idLbl = new Label("Id: "+v.getId());
        Label type = new Label("Type: "+v.getType());
        Label make = new Label("Make: "+v.getMake());
        Label year = new Label("Year: "+v.getYear());
        Label seats = new Label("Seats: "+v.getSeats());
        Label status = new Label("Status: "+v.getStatus());
        Label rent_rate = new Label("Seats: "+v.getRent_rate());
        Label late_rate = new Label("Status: "+v.getLate_rate());
        status.setId("status");
        vBox.getChildren().addAll(idLbl,type,make,year,seats,status,rent_rate,late_rate);


        //ButtonGroups
        HBox hBox = new HBox(25);    // space = 10
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(15));
        Button rentButton = new Button("Rent vehicle");
        Button returnButton = new Button("Return vehicle");
        Button maintenanceButton = new Button("Vehicle Maintenance");
        Button completeButton = new Button("Complete Maintenance");
        Button refresh = new Button("Refresh ");
        hBox.getChildren().addAll(rentButton, returnButton,maintenanceButton,completeButton,refresh);



        //RecordList
        ObservableList<Record> strList = FXCollections.observableArrayList(JdbcUtils.selectRecordByVid(id));
        ListView<Record> listView = new ListView<>(strList);
        listView.setId("list");
        listView.setItems(strList);

        //Set Action
        rentButton.setOnAction(new RentController(detailStage,id));
        returnButton.setOnAction(new ReturnController(detailStage,id));
        maintenanceButton.setOnAction(new MaintenanceController(id));
        completeButton.setOnAction(new CMaintenanceController(id));
        refresh.setOnAction( e -> SingleVehicleView.fresh(detailStage,id));

        borderPane.setLeft(imageView);
        borderPane.setCenter(vBox);
        borderPane.setBottom(hBox);
        borderPane.setPadding(new Insets(20));

        VBox root = new VBox();
        root.getChildren().addAll(borderPane,listView);
        Scene scene = new Scene(root, 900, 600);
        detailStage.setScene(scene);
        detailStage.show();
    }

}
