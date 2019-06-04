package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.entity.Record;
import model.entity.Vehicle;
import model.exception.InvalidException;
import model.util.JdbcUtils;
import view.AlertView;
import view.MainView;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class RentController implements EventHandler<ActionEvent> {

    private Stage rentStage;
    private int id;

    public RentController(Stage stage, int id) {
        this.id = id;
        this.rentStage = stage;
    }


    @Override
    public void handle(ActionEvent e) {

        Vehicle v = JdbcUtils.selectVehicleById(id);

        if (v.getStatus().equals(Vehicle.Rented)) {
            AlertView.getInstance().show("Vehicle: " + id + " could not be rented now!");
        }

        if (v.getStatus().equals(Vehicle.Maintenance)) {
            AlertView.getInstance().show("Vehicle: " + id + " is under maintenance \n It could not be rented now!");

        }

        if (v.getStatus().equals(Vehicle.Available)) {
            RentShow(v);
        }

    }

    static void RentShow(Vehicle v) {
        Stage rentStage = new Stage();
        rentStage.setTitle("Rent Vehicle");
        Image logo = new Image("file:C:\\lyw\\COSC_tasks\\Assignment2\\images\\logo.png");
        rentStage.getIcons().add(logo);

        Label uidLabel = new Label("Customer Id ");
        Label daysLabel = new Label("Rent Days");
        TextField uidField = new TextField();
        TextField daysField = new TextField();
        uidField.setId("uid");
        daysField.setId("day");

        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(150);
        column1.setHalignment(HPos.RIGHT);
        column2.setHalignment(HPos.LEFT);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.getColumnConstraints().addAll(column1, column2);
        gridPane.add(uidLabel, 0, 0);
        gridPane.add(daysLabel, 0, 1);
        gridPane.add(uidField, 1, 0);
        gridPane.add(daysField, 1, 1);


        HBox hBox = new HBox();
        hBox.setSpacing(40);
        hBox.setAlignment(Pos.CENTER);
        Button rentButton = new Button("Rent");
        Button cancelButton = new Button("Cancel");

        //Set Action
        cancelButton.setOnAction(new DialogCancelController(rentStage));
        rentButton.setOnAction(e -> rent(rentStage, v));

        hBox.getChildren().addAll(rentButton, cancelButton);
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setCenter(gridPane);
        root.setBottom(hBox);

        Scene scene = new Scene(root, 400, 280);
        rentStage.setScene(scene);
        rentStage.show();
    }

    static void rent(Stage stage, Vehicle v) {
        Scene scene = stage.getScene();
        TextField uidField = (TextField) (scene.lookup("#uid"));
        TextField dayField = (TextField) scene.lookup("#day");
        String uid = uidField.getText();
        String day = dayField.getText();
        try {
            checkValid(uid, day, v);
            uid = uid.replace(" ", "");
            day = day.replace(" ", "");
            int u_id = Integer.parseInt(uid);
            int days = Integer.parseInt(day);
            LocalDate current = LocalDate.now();
            LocalDate r_date =current.plusDays(days);
            Record r = new Record(u_id,v.getId(),current,r_date,days*v.getRent_rate());
            JdbcUtils.insertRecord(r);
            JdbcUtils.updateVehicle(v.getId(),Vehicle.Rented,false);
            stage.close();
            System.out.println(r.getRental_fee());
            AlertView.getInstance().show("Success.\nExcepted fee is "+days*v.getRent_rate());
        } catch (InvalidException e1) {
            AlertView.getInstance().show("Fail.\n" + e1.getMessage());

        }

    }

    static void checkValid(String uid, String day, Vehicle v) throws InvalidException {
        StringBuffer msg = new StringBuffer();

        if (!Pattern.matches("^\\s*\\d+\\s*$", uid))
            msg.append(" Invalid Customer Id!\n");

        if (!Pattern.matches("^\\s*\\d+\\s*$", day)) {
            msg.append(" Invalid Rent Days.\n");
        } else {
            int days = Integer.parseInt(day.replace(" ", ""));
            if (v.getType().equals("Car")) {
                if (days > 14 || days < 3) {
                    msg.append("Car's rent Days mut between 3-14.");
                }
            } else {
                Period period = Period.between(v.getComplete_Maintenance_date(),LocalDate.now());
                System.out.println(period);
                System.out.println(period.getDays());
                System.out.println(days);
                if(period.getDays()>Vehicle.interval-days){
                    msg.append("You can not rent this Van now.\n" +
                            "Please maintenance first or reduce rent days.");
                }

            }
        }


        if (msg.length() > 0) {
            msg.append(" Please try again");
            throw new InvalidException(msg.toString());
        }


    }

}




