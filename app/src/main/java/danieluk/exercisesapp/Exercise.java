package danieluk.exercisesapp;

/**
 * Exercise- klasa do tworzenia obiektów będącymi ćwiczeniami
 */

public class Exercise implements Comparable<Exercise> {

    int id;
    String name;
    int series;
    int reps;
    int weights;
    String notes;
    String date;

    public int compareTo(Exercise other){
        return date.compareTo(other.date);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeights() {
        return weights;
    }

    public void setWeights(int weights) {
        this.weights = weights;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }





}
