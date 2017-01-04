package danieluk.exercisesapp;

import android.database.Cursor;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private ExercisesDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dbHelper = new ExercisesDbAdapter(this);
        dbHelper.open();

        //Clean all data
        dbHelper.deleteAllExercises();
        //Add some data
        dbHelper.insertSomeExercises();

        //Generate ListView from SQLite Database
        displayListView();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation_view);
    }

    private void displayListView() {


        Cursor cursor = dbHelper.fetchAllExercises();

        // The desired columns to be bound
        String[] columns = new String[] {
                ExercisesDbAdapter.KEY_NAME,
                ExercisesDbAdapter.KEY_SERIES,
                ExercisesDbAdapter.KEY_REPS
                //CountriesDbAdapter.KEY_CONTINENT,
                // CountriesDbAdapter.KEY_REGION,
                // CountriesDbAdapter.KEY_NUM
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.item_name,
                R.id.item_series,
                R.id.item_reps
                //R.id.continent,
                // R.id.region,
                // R.id.num
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.exercise_info,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

    /*
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String countryCode =
                        cursor.getString(cursor.getColumnIndexOrThrow("code"));
                Toast.makeText(getApplicationContext(),
                        countryCode, Toast.LENGTH_SHORT).show();

            }
        });

        EditText myFilter = (EditText) findViewById(R.id.myFilter);
        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataAdapter.getFilter().filter(s.toString());
            }
        });

        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return dbHelper.fetchCountriesByName(constraint.toString());
            }
        });
        */

    }
}
