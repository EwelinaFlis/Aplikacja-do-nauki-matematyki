package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

public class ExerciseController implements Initializable {

    public static String[] welcomeMessage = {"Dzień dobry,\npora na nowe zadanie!", "Czas na trochę\n matematyki!", "Wiem, że dziś pójdzie\nCi świetnie!"};
    public static String[] finishedExcerciseMessage = {"Udało Ci się\nrozwiązać zadanie!", "Brawo,\nzadanie rozwiązane!", "Świetnie Ci poszło!", "Jesteś mistrzem\nmatematyki!"};
    @FXML private Label title;
    @FXML private TextArea excerciseTextArea;
    @FXML private Label points;
    @FXML private TextField result;
    @FXML private Label clueLabel;
    @FXML private Button checkAnswerButton;
    private ExerciseService exerciseService;
    private int wrongAnswers;
    private int time;
    private long startTime;
    private RuleEngine ruleEngine;

    public ExerciseController(){}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    public void onStart(String exerciseTitle, ExerciseService exerciseService) {

        startTime = System.currentTimeMillis();
        ruleEngine = new RuleEngine();
        this.exerciseService = exerciseService;
        exerciseService.setCurrentExercise(exerciseTitle);
        time = exerciseService.getCurrentExerciseTime();
        wrongAnswers = exerciseService.getCurrentExerciseWrongAnswers();
        title.setText(exerciseTitle);
        excerciseTextArea.setText(exerciseService.getCurrentExerciseContent());
        points.setText("Punkty: "+ exerciseService.getCurrentExercisePoints());

        if(exerciseService.getCurrentExerciseContent().length() < 45){
            excerciseTextArea.setPrefHeight(excerciseTextArea.getMinHeight());
        }
        else if(exerciseService.getCurrentExerciseContent().length() < 100 ){
            excerciseTextArea.setPrefHeight(150);
        }
        else if(exerciseService.getCurrentExerciseContent().length() < 126){
            excerciseTextArea.setPrefHeight(200);
        }
        else if(exerciseService.getCurrentExerciseContent().length() < 200){
            excerciseTextArea.setPrefHeight(250);
        }
        else if(exerciseService.getCurrentExerciseContent().length() < 230){
            excerciseTextArea.setPrefHeight(350);
        }
        else{
            excerciseTextArea.setPrefHeight(400);
        }

        Random random = new Random();
        int number = random.nextInt(welcomeMessage.length);
        clueLabel.setText(welcomeMessage[number]);
    }

    public void returnToMain(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/MainScene.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();
        controller.onStart(exerciseService);
        stage.setScene(new Scene(root));
    }

    public String changeClue(String clue){

        String[] arr = clue.split(" ");
        int index = 0;
        String result= "";
        for(String s : arr){
            index += s.length();
            result += s+ " ";
            if(index > 16) {
                result += "\n";
                index = 0;
            }
        }
        return result;
    }

    public void checkAnswer(MouseEvent mouseEvent) {

        if(result.getText().equals(String.valueOf(exerciseService.getCurrentExerciseResult()))){
            Random random = new Random();
            int number = random.nextInt(finishedExcerciseMessage.length);
            clueLabel.setText(finishedExcerciseMessage[number]);

            exerciseService.setCurrentExerciseFinished();
            checkAnswerButton.setDisable(true);
            result.setDisable(true);

            FileOutputStream outputStream = null;
            FileInputStream inputStream;
            try {
                Properties properties = new Properties();
                File file = new File("src/main/resources/user.properties");
                inputStream = new FileInputStream(file);
                properties.load(inputStream);

                properties.setProperty(String.valueOf(exerciseService.getCurrentExerciseId()), "finished");

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
        else {
            double rightAnswer = 0;
            String rightAnswerString = exerciseService.getCurrentExerciseResult();
            try {                                                        //jesli ulamek dziesietny lub liczba calkowita
                 rightAnswer = Double.parseDouble(rightAnswerString);
            } catch (NumberFormatException nfe) {                          //jesli ulamek zwykly
                String[] friction = rightAnswerString.split("/");
                rightAnswer = Double.parseDouble(friction[0]) / Double.parseDouble(friction[1]);
            }

            boolean wrongFormat = false;
            double userAnswer = 0;
            String userAnswerString = result.getText().replace(",", ".").trim();
            try {
                userAnswer = Double.parseDouble(userAnswerString);      //jeśli ulamek jest dziesietny
            } catch (NumberFormatException nfe) {
                    String[] friction = userAnswerString.split("/");
                    if (friction.length == 2){
                        try {
                            userAnswer = Double.parseDouble(friction[0]) / Double.parseDouble(friction[1]);
                        } catch (NumberFormatException nfe2){
                            wrongFormat = true;
                        }
                    }
                    else
                        wrongFormat = true;
            }

            long  excerciseDuration = System.currentTimeMillis() - startTime;
            time += (int)excerciseDuration/60000.0;      //poprzedni czas + od poprzedniego wywolania metody w pelnych minutach
            startTime = System.currentTimeMillis();
            String approximateTime = "1";
            if(time > 10)
                approximateTime = "10";
            else if(time > 5)
                approximateTime = "5";

            wrongAnswers++;               //przeliczenie zlych odpowiedzi
            String wrongAnswer = "1";
            if(wrongAnswers > 5)
                wrongAnswer = "5+";
            else if (wrongAnswers >= 3)
                wrongAnswer = "3";

            if(wrongFormat) {
                clueLabel.setText("Zły format\n odpowiedzi.");
                AnchorPane.setTopAnchor(clueLabel, 45.0);
                AnchorPane.setLeftAnchor(clueLabel, 85.0);
            }
            else {
                RuleEngine.ProximityToResult proximity;               //wyliczenie wielkosci bledu
                if(Math.abs(userAnswer - rightAnswer) < 1.0)
                    proximity = RuleEngine.ProximityToResult.SMALL;
                else if(Math.abs(userAnswer - rightAnswer) < 3.0)
                    proximity = RuleEngine.ProximityToResult.MEDIUM;
                else
                    proximity = RuleEngine.ProximityToResult.LARGE;

                String clue = ruleEngine.getClue(wrongAnswer, approximateTime, proximity);  //pobranie wskazówki z systemu eksperckiego
                Random random = new Random();
                List<String> cluesList = exerciseService.getCurrentExerciseClues();                  //poobranie wskazowek dla konkretnego exercises
                String finalClue = "";
                if(clue.equals(clueLabel.getText()) && cluesList.size() > 0){
                    int index = random.nextInt(cluesList.size());
                    finalClue = changeClue(cluesList.get(index));
                }
                else if(cluesList.size() > 0) {
                    int index = random.nextInt(cluesList.size()+1);
                    if (index == cluesList.size())
                        finalClue = clue;
                    else
                        finalClue = changeClue(cluesList.get(index));
                }
                else
                    finalClue = clue;

                clueLabel.setText(finalClue);
                if(finalClue.length() > 48){
                    AnchorPane.setTopAnchor(clueLabel, 30.0);
                    AnchorPane.setLeftAnchor(clueLabel, 65.0);
                }
                else {
                    AnchorPane.setTopAnchor(clueLabel, 45.0);
                    AnchorPane.setLeftAnchor(clueLabel, 85.0);
                }
            }
                                                        //na koniec
            exerciseService.setCurrentExerciseTime(time);                   //zapisanie nowego czasu i ilości błędnych odp dla exercises
            exerciseService.setCurrentExerciseWrongAnswers(wrongAnswers);

            FileOutputStream outputStream = null;
            FileInputStream inputStream;
            try {
                Properties properties = new Properties();
                File file = new File("src/main/resources/user.properties");
                inputStream = new FileInputStream(file);
                properties.load(inputStream);

                properties.setProperty("wrongAnswer"+ exerciseService.getCurrentExerciseId(), String.valueOf(wrongAnswers));
                properties.setProperty("time"+ exerciseService.getCurrentExerciseId(), String.valueOf(time));

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
}