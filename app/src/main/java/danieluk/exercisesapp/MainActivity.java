package danieluk.exercisesapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

/**
    MainActivity- klasa główna, odpowiedzialna za głowny widok i reakcję dolnego menu

 */
public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation_view);

        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transactionStart = fragmentManager.beginTransaction();
        transactionStart.replace(R.id.main_container, new ListExercisesFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menu_item_workout:
                        fragment = new ListExercisesFragment();
                        break;
                    case R.id.menu_item_add:
                        fragment = new AddExerciseFragment();
                        break;
                    case R.id.menu_item_history:
                        fragment = new InfosFragment();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                return true;
            }
        });
    }


}
