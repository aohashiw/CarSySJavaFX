package view;

import controller.DataExportController;
import controller.DirectoryChooseController;
import controller.FilterController;
import controller.FreshController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import model.entity.Vehicle;
import model.util.JdbcUtils;

import java.util.List;
import java.util.stream.Collectors;


public class MainView extends Application {

    private Stage mainStage;

    public static List<Vehicle> vehicleList;
    public static List<Vehicle> filterVehicleList;
    public static ScrollPane myList = new ScrollPane();

    public static final String path = System.getProperty("user.dir");

    @Override
    public void start(Stage mainStage) {
        try {

            vehicleList = JdbcUtils.selectVehicle();
            filterVehicleList = vehicleList;
            this.mainStage = mainStage;

            // Set Logo and Title
            Image logo = new Image("file:"+path+"\\images\\logo.png");
            mainStage.getIcons().add(logo);
            mainStage.setTitle("Thrifty Rent System");

            //Set root Node
            VBox root = new VBox();

            // Create  Menu
            MenuBar menuBar = new MenuBar();
            menuBar.prefWidthProperty().bind(mainStage.widthProperty());

            Menu fileMenu = new Menu("File");

            MenuItem importMenuItem = new MenuItem("Import");
            MenuItem exportMenuItem = new MenuItem("Export");
            MenuItem exitMenuItem = new MenuItem("Exit");

            fileMenu.getItems().addAll(importMenuItem, exportMenuItem, new SeparatorMenuItem(), exitMenuItem);

            Menu manageMenu = new Menu("Manage");
            MenuItem addMenuItem = new MenuItem("Add Vehicle");
            manageMenu.getItems().add(addMenuItem);

            menuBar.getMenus().addAll(fileMenu,manageMenu);


            //Set Action
            importMenuItem.setOnAction(new DirectoryChooseController());
            exportMenuItem.setOnAction(new DataExportController());
            exitMenuItem.setOnAction(actionEvent -> Platform.exit());
            addMenuItem.setOnAction(actionEvent -> AddView.getInstance().show());

            BorderPane borderPane = new BorderPane();

            //  search and filter choices
            HBox filter= new HBox();
            filter.setSpacing(10);
            filter.setAlignment(Pos.CENTER);
            Label status = new Label("Status:");
            Label type = new Label("Type:");
            Label make = new Label("Make:");
            Label seats = new Label("Seats:");
            ChoiceBox typeChoices = new ChoiceBox(FXCollections.observableArrayList("Car", "Van"));
            ChoiceBox seatChoices = new ChoiceBox(FXCollections.observableArrayList(4, 7,15));
            ChoiceBox makeChoices = new ChoiceBox(FXCollections.observableArrayList("Honda", "Toyota"));
            ChoiceBox statusChoices = new ChoiceBox(FXCollections.observableArrayList("Available","Rented", "Maintenance"));

            Button filterButton = new Button("Filter");
            Button  refresh = new Button(" Refresh ");

            filter.getChildren().addAll(
                    type,typeChoices,
                    seats,seatChoices,
                    make,makeChoices,
                    status,statusChoices,
                    filterButton,refresh);


            // set Action

            filterButton.setOnAction(new FilterController());
            refresh.setOnAction(new FreshController());

            typeChoices.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    filterVehicleList=filterVehicleList.stream()
                            .filter( v -> newValue.equals(v.getType()))
                            .collect(Collectors.toList());

                }
            });

            seatChoices.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    filterVehicleList=filterVehicleList.stream()
                            .filter( v -> newValue.equals(v.getSeats()))
                            .collect(Collectors.toList());

                }
            });

            statusChoices.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    filterVehicleList=filterVehicleList.stream()
                            .filter( v -> newValue.equals(v.getStatus()))
                            .collect(Collectors.toList());

                }
            });

            makeChoices.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    filterVehicleList=filterVehicleList.stream()
                            .filter( v -> newValue.equals(v.getMake()))
                            .collect(Collectors.toList());

                }
            });




            GeneralView generalView = GeneralView.getInstance();
            myList.setContent(generalView.listCreate(MainView.filterVehicleList));

            borderPane.setTop(filter);
            borderPane.setCenter(myList );

            root.getChildren().add(menuBar);
            root.getChildren().add(borderPane);


            Scene scene = new Scene(root, 800, 600);
            mainStage.setScene(scene);
            mainStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static void main(String[] args) {
        launch(args);
    }


}
