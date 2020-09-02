package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayPointsController implements Initializable {

    @FXML private Label sumOfPoints;
    @FXML private Label titleLabel1;
    @FXML private Label titleLabel2;
    @FXML private Label titleLabel3;
    @FXML private Label titleLabel4;
    @FXML private Label titleLabel5;
    @FXML private Label titleLabel6;
    @FXML private Label titleLabel7;
    @FXML private Label titleLabel8;
    @FXML private Label titleLabel9;
    @FXML private Label titleLabel10;
    @FXML private Label titleLabel11;
    @FXML private Label titleLabel12;
    @FXML private Label titleLabel13;
    @FXML private Label titleLabel14;
    @FXML private Label titleLabel15;
    @FXML private Label pointsLabel1;
    @FXML private Label pointsLabel2;
    @FXML private Label pointsLabel3;
    @FXML private Label pointsLabel4;
    @FXML private Label pointsLabel5;
    @FXML private Label pointsLabel6;
    @FXML private Label pointsLabel7;
    @FXML private Label pointsLabel8;
    @FXML private Label pointsLabel9;
    @FXML private Label pointsLabel10;
    @FXML private Label pointsLabel11;
    @FXML private Label pointsLabel12;
    @FXML private Label pointsLabel13;
    @FXML private Label pointsLabel14;
    @FXML private Label pointsLabel15;
    private ExerciseService exerciseService;
    private int points;

    public DisplayPointsController() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void onStart(ExerciseService exerciseService) {

        this.exerciseService = exerciseService;
        points = 0;

        List<Exercise> exercises = exerciseService.getExercises();
        Label[] titles = {titleLabel1, titleLabel2, titleLabel3, titleLabel4, titleLabel5, titleLabel6, titleLabel7, titleLabel8, titleLabel9, titleLabel10, titleLabel11, titleLabel12, titleLabel13, titleLabel14, titleLabel15};
        Label[] pointsList = {pointsLabel1, pointsLabel2, pointsLabel3, pointsLabel4, pointsLabel5, pointsLabel6, pointsLabel7, pointsLabel8, pointsLabel9, pointsLabel10, pointsLabel11, pointsLabel12, pointsLabel13, pointsLabel14, pointsLabel15};
        Timeline[] timelines = new Timeline[pointsList.length];
        for(int i = 0; i < pointsList.length; i++){
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(5), new KeyValue(pointsList[i].textFillProperty(), Color.LIMEGREEN)),
                    new KeyFrame(Duration.seconds(0), new KeyValue(pointsList[i].textFillProperty(), Color.GREENYELLOW)));
            timelines[i] = timeline;
        }
        for(int i = 0; i < exercises.size(); i++){
            Exercise e = exercises.get(i);
            if(e.isFinished()) {
                points += e.getPoints();
                timelines[i].setCycleCount(Animation.INDEFINITE);
                timelines[i].play();
            }
            titles[i].setText(e.getTitle());
            pointsList[i].setText(" " + String.valueOf(e.getPoints()) + " ");
        }

        if(points == 0) {
            sumOfPoints.setText("Jeszcze nie posiadasz żadnych punktów.");
            AnchorPane.setLeftAnchor(sumOfPoints, 350.0);
        }
        else
            sumOfPoints.setText("Suma Twoich punktów: " + points + " ;)");
    }

    public void returnToMain(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/MainScene.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();
        controller.onStart(exerciseService);
        stage.setScene(new Scene(root));
    }

}