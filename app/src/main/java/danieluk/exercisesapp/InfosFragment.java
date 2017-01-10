package danieluk.exercisesapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * InfosFragment - fragment będący informacjami na temat aplikacji
 */

public class InfosFragment extends Fragment {
    public InfosFragment(){}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.infos_fragment, container, false);
        }
}

