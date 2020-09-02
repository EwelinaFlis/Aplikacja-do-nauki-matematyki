package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    @FXML private ListView toDoExcercises;
    @FXML private ListView finishedExcercises;
    @FXML private TextField enterNameText;
    @FXML private Label nameLabel;
    @FXML private Label helloLabel;
    @FXML private Label alreadyFinishedLabel;
    @FXML private AnchorPane enterNamePane;
    @FXML private AnchorPane changeNamePane;
    @FXML private AnchorPane alreadyFinishedPane;
    @FXML private TextField changeNameText;
    private ExerciseService exerciseService;

    public MainController() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onStart(){
        changeNamePane.setVisible(false);
        exerciseService = new ExerciseService();

        // odczytanie zadan z plikow
        File folder = new File("src/main/resources/exercises");
        File[] fileList = folder.listFiles();
        String title, content, result, points, wrongAnswer, time;
        List<String> clues;
        int numberOfExcercises = 0;

        try (InputStream in = new FileInputStream("src/main/resources/user.properties")) {
            Properties properties = new Properties();
            properties.load(in);

            for (File f : fileList) {
                if (f.isFile()) {
                    clues = new ArrayList<>();
                    Scanner scanner = null;
                    try {
                        scanner = new Scanner(f);  //pobranie parametrów z pliku
                        title = scanner.nextLine();
                        content = scanner.nextLine();
                        result = scanner.nextLine();
                        points = scanner.nextLine();

                        numberOfExcercises++;
                        String finished = properties.getProperty(String.valueOf(numberOfExcercises));  //pobranie property

                        Boolean finishedBoolean = true;
                        if (finished == null) {
                            toDoExcercises.getItems().add(title);
                            finishedBoolean = false;
                        }
                        else
                            finishedExcercises.getItems().add(title);

                        wrongAnswer = properties.getProperty("wrongAnswer"+String.valueOf(numberOfExcercises));  //pobranie property

                        if(wrongAnswer == null)
                            wrongAnswer = "0";

                        time = properties.getProperty("time"+String.valueOf(numberOfExcercises));

                        if(time == null)
                            time = "0";

                        while(scanner.hasNextLine()){
                            clues.add(scanner.nextLine());
                        }

                        exerciseService.addExcercise(title, content, result, finishedBoolean, Integer.parseInt(points), Integer.parseInt(wrongAnswer), Integer.parseInt(time), clues);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // odczytanie imienia i wynikow uzytkownika
        try (InputStream in = new FileInputStream("src/main/resources/user.properties")) {
            Properties properties = new Properties();
            properties.load(in);
            String user = properties.getProperty("user");
            if(user != null){
                helloLabel.setText("Cześć");
                nameLabel.setText(user + "!");
                enterNamePane.setVisible(false);
            }
            else {
                enterNamePane.setVisible(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onStart(ExerciseService exerciseService){
        this.exerciseService = exerciseService;
        for(Exercise e : exerciseService.getExercises()){
            if(e.isFinished())
                finishedExcercises.getItems().add(e.getTitle());
            else
                toDoExcercises.getItems().add(e.getTitle());
        }

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/main/resources/user.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            String user = properties.getProperty("user");
            if(user != null){
                helloLabel.setText("Cześć");
                nameLabel.setText(user + "!");
                enterNamePane.setVisible(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void chooseExcercise(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/ExerciseScene.fxml"));
        Parent root = loader.load();
        ExerciseController controller = loader.getController();
        exerciseService.setCurrentExercise(toDoExcercises.getSelectionModel().getSelectedItem().toString());
        controller.onStart(exerciseService.getCurrentExerciseTitle(), exerciseService);
        stage.setScene(new Scene(root));
    }

    public void chooseFinishedExcercise(MouseEvent mouseEvent) throws  IOException{
        alreadyFinishedLabel.setText("To zadanie zostało\njuż rozwiązane ;)");
        alreadyFinishedPane.setVisible(true);
    }

    public void enterName(MouseEvent mouseEvent){
        if(enterNameText.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uwaga!");
            alert.setHeaderText(null);
            alert.setContentText("Wpisz swoje imie!");
            alert.show();
        }
        else {
            helloLabel.setText("Cześć");
            nameLabel.setText(enterNameText.getText() + "!");
            enterNamePane.setVisible(false);

            //zapisanie imienia użytkownika
            FileOutputStream outputStream = null;
            FileInputStream inputStream = null;
            try {
                Properties properties = new Properties();
                File file = new File("src/main/resources/user.properties");
                inputStream = new FileInputStream(file);
                properties.load(inputStream);

                properties.setProperty("user", enterNameText.getText());

                outputStream = new FileOutputStream(file);
                properties.store(outputStream, "This is a user properties file.");

            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                try{
                    outputStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void changeName(MouseEvent mouseEvent) throws IOException {
        changeNamePane.setVisible(true);
        String name = nameLabel.getText();
        name = name.substring(0, name.length()-1);
        if(!nameLabel.getText().isEmpty())
            changeNameText.setText(name);
        else
            changeNameText.setText("Imię...");
    }

    public void changeNameSave(MouseEvent mouseEvent) throws IOException{
        changeNamePane.setVisible(false);
        if(!changeNameText.getText().isEmpty()) {
            nameLabel.setText(changeNameText.getText() + "!");

            FileOutputStream outputStream = null;
            FileInputStream inputStream = null;
            try {
                Properties properties = new Properties();
                File file = new File("src/main/resources/user.properties");
                inputStream = new FileInputStream(file);
                properties.load(inputStream);

                properties.setProperty("user", changeNameText.getText());

                outputStream = new FileOutputStream(file);
                properties.store(outputStream, "This is a user properties file.");

            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                try{
                    outputStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void changeNameCancel(MouseEvent mouseEvent) throws IOException{
        changeNamePane.setVisible(false);
    }

    public void finishedExerciseOK(MouseEvent mouseEvent) throws IOException{
        alreadyFinishedPane.setVisible(false);
    }

    public void displayPoints(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/DisplayPointsScene.fxml"));
        Parent root = loader.load();
        DisplayPointsController controller = loader.getController();
        controller.onStart(exerciseService);
        stage.setScene(new Scene(root));
    }

}

