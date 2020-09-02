package sample;

import java.util.ArrayList;
import java.util.List;

public class ExerciseService {

    private Exercise currentExercise;
    private List<Exercise> exercises;

    public ExerciseService(){
        exercises = new ArrayList<>();
    }

    public void addExcercise(String title, String content, String result, Boolean finished, int points, int wrongAnswer, int time, List<String> clues){
        exercises.add(new Exercise(exercises.size()+1, title, content, result, finished, points, wrongAnswer, time, clues));
    }

    public void setCurrentExercise(String currentExcrcise) {
        for(Exercise e : exercises)
            if(e.getTitle().equals(currentExcrcise))
                this.currentExercise = e;
    }

    public List<Exercise> getExercises(){

        return this.exercises;
    }

    public void setCurrentExerciseFinished(){

        currentExercise.setFinished();
    }

    public int getCurrentExerciseId(){
        return currentExercise.getId();
    }

    public String getCurrentExerciseResult(){
        return currentExercise.getResult();
    }

    public List<String> getCurrentExerciseClues(){
        return currentExercise.getClues();
    }

    public void setCurrentExerciseTime(int time){
        currentExercise.setTime(time);
    }

    public void setCurrentExerciseWrongAnswers(int wrongAnswers){
        currentExercise.setWrongAnswers(wrongAnswers);
    }

    public String getCurrentExerciseContent(){
        return currentExercise.getContent();
    }

    public String getCurrentExerciseTitle(){
        return currentExercise.getTitle();
    }

    public int getCurrentExercisePoints(){
        return currentExercise.getPoints();
    }

    public int getCurrentExerciseWrongAnswers(){
        return currentExercise.getWrongAnswers();
    }

    public int getCurrentExerciseTime(){
        return currentExercise.getTime();
    }
}
