package danieluk.exercisesapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Danielu on 2017-01-04.
 */

public class HistoryFragment extends Fragment {
    public HistoryFragment(){}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.history_fragment, container, false);
        }
}

