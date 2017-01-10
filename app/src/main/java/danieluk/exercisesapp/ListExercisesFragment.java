package danieluk.exercisesapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;

/**
 * ListExercisesFragment- klasa odpowiedzialna za wyświetlanie listy ćwiczeń
 */

public class ListExercisesFragment extends Fragment {
    String TAG="ListExercisesFragment";
    private ExercisesDbAdapter dbHelper;
    private ListView listView;
    private ArrayList<Exercise> lExercise=new ArrayList<Exercise>();
    private CustomListAdapter dataAdapter;
    private static  final int REQUEST_CODE=100;
    public ListExercisesFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.listView1);
        dbHelper = new ExercisesDbAdapter(getActivity());
        dbHelper.open();

        //usuń wszystkie ćwiczenia - do testów
        //dbHelper.deleteAllExercises();
        //Dodaj ćwiczenia przykładowe
        //dbHelper.insertSomeExercises();


        //wygeneruj listę z bazy danych
        displayListView();

        //po naciśnięciu na element listy, wyślij informacje do klasy ListDetails za pomocą Intent
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
               public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                   Exercise exercise=(Exercise) listView.getItemAtPosition(position);
                   //wysyłam dane do ListDetail
                   Intent intent = new Intent(getContext(),ListDetail.class);
                   intent.putExtra("id",exercise.getId());
                   intent.putExtra("name",exercise.getName());
                   intent.putExtra("series",exercise.getSeries());
                   intent.putExtra("reps",exercise.getReps());
                   intent.putExtra("weights",exercise.getWeights());
                   intent.putExtra("notes",exercise.getNotes());
                   intent.putExtra("date",exercise.getDate());
                   startActivityForResult(intent,REQUEST_CODE);
               }
       });

        return view;
    }
    // przy otrzymaniu zwrotnego sygnału z klasy ListDetail
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==REQUEST_CODE){

            int id=data.getIntExtra("id",-1);
            if(id!=-1){
                Log.d(TAG,"sukces"+Integer.toString(id));
                deleteExerciseWithID(id);
                Toast.makeText(getActivity().getApplicationContext(), "Exercise deleted!", Toast.LENGTH_SHORT).show();
            }
            else{
                Log.d(TAG,"Niestety");
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }


    private void deleteExerciseWithID(int id){
        for(Exercise e:lExercise){
            if( e.getId()==id ){
                dbHelper.deleteRowWithId(id);
                lExercise.remove(e);
                dataAdapter.notifyDataSetChanged();
                break;
            }
        }
    }
    private void displayListView() {
        Cursor cursor = dbHelper.fetchAllExercises();

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int _id = cursor.getColumnIndex(ExercisesDbAdapter.KEY_ROWID);
            int name = cursor.getColumnIndex(ExercisesDbAdapter.KEY_NAME);
            int series = cursor.getColumnIndex(ExercisesDbAdapter.KEY_SERIES);
            int reps = cursor.getColumnIndex(ExercisesDbAdapter.KEY_REPS);
            int weights = cursor.getColumnIndex(ExercisesDbAdapter.KEY_WEIGHTS);
            int notes = cursor.getColumnIndex(ExercisesDbAdapter.KEY_NOTES);
            int date = cursor.getColumnIndex(ExercisesDbAdapter.KEY_DATE);

            Exercise exercise = new Exercise();
            exercise.setId(cursor.getInt(_id));
            exercise.setName(cursor.getString(name));
            exercise.setSeries(cursor.getInt(series));
            exercise.setReps(cursor.getInt(reps));
            exercise.setWeights(cursor.getInt(weights));
            exercise.setNotes(cursor.getString(notes));
            exercise.setDate(cursor.getString(date));


            lExercise.add(exercise);
        }

        Collections.sort(lExercise);


        dataAdapter = new CustomListAdapter(getActivity(),0, lExercise);
        // Przypisz dataAdapter do listView
        listView.setAdapter(dataAdapter);


    }

}

