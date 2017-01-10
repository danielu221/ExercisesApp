package danieluk.exercisesapp;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * CustomListAdapter - klasa używana przy tworzeniu ListView
 */

public class CustomListAdapter extends ArrayAdapter<Exercise> {
    private Activity activity;
    private ArrayList<Exercise> lExercise;
    private static LayoutInflater inflater = null;
    public static String prevDate=null;

    public CustomListAdapter (Activity activity, int textViewResourceId,ArrayList<Exercise> _lExercise) {
        super(activity, textViewResourceId, _lExercise);
        try {
            this.activity = activity;
            this.lExercise = _lExercise;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lExercise.size();
    }

    public Exercise getItem(Exercise position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //String prevDate=null;
        try {
            View vi = null;
            Exercise exercise=getItem(position);

            if (convertView == null) {
                vi = inflater.inflate(R.layout.exercise_info, null);
            } else {

                vi=convertView;
            }
            String thisDate=exercise.getDate();


            TextView name=(TextView) vi.findViewById(R.id.item_name);
            TextView series=(TextView)vi.findViewById(R.id.item_series);
            TextView reps=(TextView)vi.findViewById(R.id.item_reps);
            TextView date=(TextView)vi.findViewById(R.id.item_date);

            //warunki potrzebne do pokazywania/ukrywania headera z datą,
            // każda sekcja ma ćiwczenia wykonywane w danym dniu
            if (prevDate == null || !prevDate.equals(thisDate) || position==0) {
               date.setVisibility(View.VISIBLE);
            } else {
               date.setVisibility(View.GONE);
            }
            prevDate=thisDate;

            Log.d("CustomListAdapter","prevDate: "+prevDate+" thisDate: "+thisDate);

            name.setText(exercise.getName());
            series.setText(Integer.toString(exercise.getSeries()));
            reps.setText(Integer.toString(exercise.getReps()));
            date.setText(exercise.getDate());

            return vi;

        } catch (Exception e) {
            return null;

        }

    }
}
