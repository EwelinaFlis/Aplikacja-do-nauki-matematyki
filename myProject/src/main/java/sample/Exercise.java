package sample;

import java.util.List;

public class Exercise {

    private int id;
    private String title;
    private String content;
    private String result;
    private boolean finished;
    private int points;
    private int wrongAnswers;
    private int time;
    private List<String> clues;

    public Exercise(int id, String title, String content, String result, Boolean finished, int points, int wrongAnswers, int time, List<String> clues) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.result = result;
        this.finished = finished;
        this.points = points;
        this.wrongAnswers = wrongAnswers;
        this.time = time;
        this.clues = clues;
    }

    public int getId(){
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getResult() {
        return result;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished() {
        this.finished = true;
    }

    public int getPoints() {
        return points;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<String> getClues(){
        return this.clues;
    }
}
