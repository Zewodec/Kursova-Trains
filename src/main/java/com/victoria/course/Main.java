package com.victoria.course;

import com.victoria.course.command.*;
import com.victoria.course.controller.LifecycleController;
import com.victoria.course.response.ResponseEntity;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main extends Application {

    private BorderPane root;
    private TextArea infoTextArea;
    private final LifecycleController lc = new LifecycleController();

    // Dialogs
    private Dialog<Pair<String, Integer>> editCreateTrainDialog;

    // Dialogs invokers
    private Button editCreateTrainBtn;
    private Button searchWagonsPassengersDiapason;

    @Override
    public void init() throws Exception {
        root = new BorderPane();

        // Info panel part
        Text infoPanelCaption = new Text("Informational panel");

        infoTextArea = new TextArea();
        infoTextArea.setFocusTraversable(false);
        infoTextArea.setEditable(false);
        infoTextArea.setFont(new Font(16));

        VBox infoPanel = new VBox(25, infoPanelCaption, infoTextArea);
        infoPanel.setPadding(new Insets(20, 25, 20, 25));
        infoPanel.setAlignment(Pos.CENTER);

        // Controls panel part
        Button showWagonsBtn = new Button("Wagons");
        showWagonsBtn.setOnAction(e -> {
            ResponseEntity response = lc.executeCommand(ShowWagonsCommand.NAME);
            infoTextArea.setText(response.toString());
        });

        Button showTrainBtn = new Button("Train");
        showTrainBtn.setOnAction(e -> {
            ResponseEntity response = new ResponseEntity();
            response.addPair("train", lc.getTrain());
            infoTextArea.setText(response.toString());
        });

        Button evalPassengersAndLuggageBtn = new Button("Passengers/Luggage");
        evalPassengersAndLuggageBtn.setOnAction(e -> {
            ResponseEntity response = lc.executeCommand(EvalPassengersAndLuggageCommand.NAME);
            infoTextArea.setText(response.toString());
        });

        ComboBox<Sort.Direction> sortOrder = new ComboBox<>(
                FXCollections.observableArrayList(Sort.Direction.ASC, Sort.Direction.DESC)
        );

        Button sortByComfortBtn = new Button("Sort (comfort)");
        sortByComfortBtn.setOnAction(e -> {
            lc.getParamsHolder().put("sortDirection", sortOrder.getValue());
            ResponseEntity response = lc.executeCommand(SortTrainWagonsByComfortCommand.NAME);
            infoTextArea.setText(response.toString());
        });

        editCreateTrainBtn = new Button("Edit/Create Train");
        searchWagonsPassengersDiapason = new Button("Diapason search");

        // Control panel init
        HBox controlsPanel = new HBox(25,
                showTrainBtn,
                showWagonsBtn,
                editCreateTrainBtn,
                evalPassengersAndLuggageBtn,
                searchWagonsPassengersDiapason,
                sortByComfortBtn,
                sortOrder
        );

        controlsPanel.setAlignment(Pos.CENTER);
        controlsPanel.setPadding(new Insets(20, 20, 20, 20));

        root.setCenter(infoPanel);
        root.setBottom(controlsPanel);
    }

    public void initDialogs() {
        // Edit/Create dialog
        editCreateTrainDialog = new Dialog<>();

        editCreateTrainDialog.setTitle("Create/Edit Train");
        editCreateTrainDialog.setHeaderText("Enter wagon type and quantity to add to the train");

        ButtonType createEditBtnType = new ButtonType("Create/Edit", ButtonBar.ButtonData.OK_DONE);
        editCreateTrainDialog.getDialogPane().getButtonTypes().addAll(createEditBtnType, ButtonType.CANCEL);

        GridPane createEditGrid = new GridPane();
        createEditGrid.setHgap(10);
        createEditGrid.setVgap(10);
        createEditGrid.setPadding(new Insets(20, 50, 10, 10));

        TextField manufTypeField = new TextField();
        manufTypeField.setPromptText("eg. PW-C2-2007-T3");
        TextField quantityField = new TextField();
        quantityField.setPromptText("eg. 5 or -3");

        createEditGrid.add(new Label("Manuf. type"), 0, 0);
        createEditGrid.add(manufTypeField, 1, 0);
        createEditGrid.add(new Label("Quantity"), 0, 1);
        createEditGrid.add(quantityField, 1, 1);

        editCreateTrainDialog.getDialogPane().setContent(createEditGrid);
        editCreateTrainDialog.setResultConverter(dialogBtn -> {
            if(dialogBtn == createEditBtnType) {
                return new Pair<>(manufTypeField.getText(), Integer.parseInt(quantityField.getText()));
            }
            return null;
        });

        editCreateTrainBtn.setOnAction(e -> {
            Optional<Pair<String, Integer>> pair = editCreateTrainDialog.showAndWait();
            if(!pair.isPresent())
                return;

            lc.getParamsHolder().put("wagons", pair.get());
            ResponseEntity response = lc.executeCommand(CreateTrainCommand.NAME);
            infoTextArea.setText(response.toString());
        });

        // Diapason search dialog
        Dialog<Map<String, Integer>> diapasonSearchDialog = new Dialog<>();
        diapasonSearchDialog.setTitle("Wagons diapason search");
        diapasonSearchDialog.setHeaderText("Enter left and right bound of passengers to search");

        ButtonType searchButton = new ButtonType("Search", ButtonBar.ButtonData.OK_DONE);
        diapasonSearchDialog.getDialogPane().getButtonTypes().addAll(searchButton, ButtonType.CANCEL);

        GridPane boundGrid = new GridPane();
        boundGrid.setHgap(10);
        boundGrid.setVgap(10);
        boundGrid.setPadding(new Insets(20, 50, 10, 10));

        TextField leftBound = new TextField();
        leftBound.setPromptText("eg. 30");
        TextField rightBound = new TextField();
        rightBound.setPromptText("eg. 100");

        boundGrid.add(new Label("Left bound"), 0, 0);
        boundGrid.add(leftBound, 1, 0);
        boundGrid.add(new Label("Right bound"), 0, 1);
        boundGrid.add(rightBound, 1, 1);

        diapasonSearchDialog.getDialogPane().setContent(boundGrid);
        diapasonSearchDialog.setResultConverter(dialogBtn -> {
            if(dialogBtn == searchButton) {
                return new HashMap<String, Integer>() {{
                    put("lb", Integer.parseInt(leftBound.getText()));
                    put("rb", Integer.parseInt(rightBound.getText()));
                }};
            }
            return null;
        });

        searchWagonsPassengersDiapason.setOnAction(e -> {
            Optional<Map<String, Integer>> resultMap = diapasonSearchDialog.showAndWait();
            if(!resultMap.isPresent())
                return;

            lc.getParamsHolder().put("searchBounds", resultMap.get());
            ResponseEntity response = lc.executeCommand(SearchWagonsByDiapasonCommand.NAME);
            infoTextArea.setText(response.toString());
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(root, 640, 480);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Курсова робота студенти Рудик Вікторії Ігорівни, КН-205");

        // Dialogs part
        initDialogs();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
