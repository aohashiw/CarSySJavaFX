package view;

import controller.DialogCancelController;
import controller.SaveController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class AddView {


    private static volatile AddView addView;

    private Stage addStage;


    private AddView() {
        Image logo = new Image("file:"+MainView.path+"\\images\\logo.png");
        this.addStage = new Stage();
        addStage.setTitle("Add Vehicle");
        addStage.getIcons().add(logo);
        System.out.println("create ..Add View");
    }

    public static AddView getInstance() {
        if (addView == null) {
            synchronized (GeneralView.class) {
                if (addView == null) {
                    addView = new AddView();
                }
            }
        }
        return addView;
    }

    public  void show() {

        Stage addStage = AddView.getInstance().addStage;


        BorderPane root = new  BorderPane();
        root.setPadding(new Insets(20));


        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        Label yearLbl = new Label("Year");
        Label makeLbl = new Label("Make");
        Label seatLbl = new Label("Seats");
        Label typeLbl = new Label("Type");

        TextField yearField = new TextField();
        yearField.setId("year");
        TextField makeField = new TextField();
        makeField.setId("make");
        TextField seatField = new TextField();
        seatField.setId("seat");
        TextField typeField = new TextField();
        typeField.setId("type");

        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(150);
        column1.setHgrow(Priority.ALWAYS);
        column1.setHalignment(HPos.RIGHT);
        column2.setHgrow(Priority.ALWAYS);
        column2.setHalignment(HPos.LEFT);

        gridPane.getColumnConstraints().addAll(column1,column2);


        gridPane.add(yearLbl, 0, 0);
        gridPane.add(makeLbl, 0, 1);
        gridPane.add(typeLbl, 0, 2);
        gridPane.add(seatLbl, 0, 3);

        gridPane.add(yearField, 1, 0);
        gridPane.add(makeField, 1, 1);
        gridPane.add(typeField, 1, 2);
        gridPane.add(seatField, 1, 3);

        HBox hBox = new HBox();
        hBox.setSpacing(40);
        hBox.setAlignment(Pos.CENTER);
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        hBox.getChildren().addAll(saveButton,cancelButton);

        //Set Action
        cancelButton.setOnAction( new DialogCancelController(addStage));
        saveButton.setOnAction(new SaveController(addStage));

        root.setCenter(gridPane);
        root.setBottom(hBox);
        Scene scene = new Scene(root, 400, 300);
        addStage.setScene(scene);
        addStage.show();
    }



}
