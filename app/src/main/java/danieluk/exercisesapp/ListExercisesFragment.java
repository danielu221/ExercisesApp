package danieluk.exercisesapp;

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

import java.util.ArrayList;

/**
 * Created by Danielu on 2017-01-04.
 */

public class ListExercisesFragment extends Fragment {
    String TAG="ListExercisesFragment";
    private ExercisesDbAdapter dbHelper;
    //private SimpleCursorAdapter dataAdapter;
    private ListView listView;
    private ArrayList<Exercise> lExercise=new ArrayList<Exercise>();
    private CustomListAdapter dataAdapter;
    public ListExercisesFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.listView1);

        dbHelper = new ExercisesDbAdapter(getActivity());
        dbHelper.open();

        //usuń wszystkie ćwiczenia - do testów
        dbHelper.deleteAllExercises();
        //Dodaj ćwiczenia przykładowe
        dbHelper.insertSomeExercises();


        //wygeneruj listę z bazy danych
        displayListView();

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
           public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                Exercise exercise=(Exercise) listView.getItemAtPosition(position);
                dbHelper.deleteRowWithId(exercise.getId());
                lExercise.remove(exercise);
                dataAdapter.notifyDataSetChanged();
                Log.d(TAG,"pozycja" + Integer.toString(position));
           }
       });



        return view;
    }

    private void displayListView() {
        Cursor cursor = dbHelper.fetchAllExercises();

        /*
        // The desired columns to be bound
        String[] columns = new String[]{
                ExercisesDbAdapter.KEY_NAME,
                ExercisesDbAdapter.KEY_SERIES,
                ExercisesDbAdapter.KEY_REPS
                //CountriesDbAdapter.KEY_CONTINENT,
                // CountriesDbAdapter.KEY_REGION,
                // CountriesDbAdapter.KEY_NUM
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.item_name,
                R.id.item_series,
                R.id.item_reps
                // R.id.continent,
                // R.id.region,
                // R.id.num
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                getActivity(), R.layout.exercise_info,
                cursor,
                columns,
                to,
                0);
                */

            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                int _id = cursor.getColumnIndex(ExercisesDbAdapter.KEY_ROWID);
                int name = cursor.getColumnIndex(ExercisesDbAdapter.KEY_NAME);
                int series = cursor.getColumnIndex(ExercisesDbAdapter.KEY_SERIES);
                int reps = cursor.getColumnIndex(ExercisesDbAdapter.KEY_REPS);
                int weights = cursor.getColumnIndex(ExercisesDbAdapter.KEY_WEIGHTS);
                int notes = cursor.getColumnIndex(ExercisesDbAdapter.KEY_NOTES);


                Exercise exercise = new Exercise();
                exercise.setId(cursor.getInt(_id));
                exercise.setName(cursor.getString(name));
                exercise.setSeries(cursor.getInt(series));
                exercise.setReps(cursor.getInt(reps));
                exercise.setWeights(cursor.getInt(weights));
                exercise.setNotes(cursor.getString(notes));
                lExercise.add(exercise);
            }
            dataAdapter = new CustomListAdapter(getActivity(), 0, lExercise);
            // Assign adapter to ListView
            listView.setAdapter(dataAdapter);

        //dbHelper.close();
    }
}

