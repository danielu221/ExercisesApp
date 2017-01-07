package danieluk.exercisesapp;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Danielu on 2017-01-06.
 */

public class CustomListAdapter extends ArrayAdapter<Exercise> {
    private Activity activity;
    private ArrayList<Exercise> lExercise;
    private static LayoutInflater inflater = null;

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

    public static class ViewHolder {
        public TextView name;
        public TextView series;
        public TextView reps;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.exercise_info, null);
                holder = new ViewHolder();

                holder.name = (TextView) vi.findViewById(R.id.item_name);
                holder.series=(TextView)vi.findViewById(R.id.item_series);
                holder.reps=(TextView)vi.findViewById(R.id.item_reps);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }



            holder.name.setText(lExercise.get(position).name);
            holder.series.setText(lExercise.get(position).series);
            holder.reps.setText(lExercise.get(position).reps);


        } catch (Exception e) {


        }
        return vi;
    }
}
