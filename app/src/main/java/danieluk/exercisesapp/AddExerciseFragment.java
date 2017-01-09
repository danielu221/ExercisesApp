package danieluk.exercisesapp;



import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Danielu on 2017-01-04.
 */

public class AddExerciseFragment extends Fragment {
    private ExercisesDbAdapter dbHelper;
    private TextInputLayout inputLayoutName,inputLayoutSeries,inputLayoutReps,inputLayoutWeights;
    private EditText inputName,inputSeries,inputReps,inputWeights,inputNotes;
    private Button btnEnter;


    public AddExerciseFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_exercise_fragment, container, false);

        inputLayoutName=(TextInputLayout) view.findViewById(R.id.input_layout_name);
        inputLayoutSeries=(TextInputLayout) view.findViewById(R.id.input_layout_series);
        inputLayoutReps=(TextInputLayout) view.findViewById(R.id.input_layout_reps);
        inputLayoutWeights=(TextInputLayout) view.findViewById(R.id.input_layout_weights);
        inputName=(EditText) view.findViewById(R.id.input_name);
        inputSeries=(EditText) view.findViewById(R.id.input_series);
        inputReps=(EditText) view.findViewById(R.id.input_reps);
        inputWeights=(EditText) view.findViewById(R.id.input_weights);
        inputNotes=(EditText) view.findViewById(R.id.input_notes);

        btnEnter=(Button) view.findViewById(R.id.btn_enter);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        return view;
    }
    // submitForm - sprawdzenie, czy wymagane pola są uzupełnione
    // i ewentualne dodanie do bazy danych
    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateSeries()) {
            return;
        }
        if (!validateReps()) {
            return;
        }

        if (!validateWeights()) {
            return;
        }
        dbHelper = new ExercisesDbAdapter(getActivity());
        dbHelper.open();
        String inputNameStr=inputName.getText().toString();
        int inputSeriesInt= Integer.parseInt(inputSeries.getText().toString());
        int inputRepsInt=Integer.parseInt(inputReps.getText().toString());
        int inputWeightsInt=Integer.parseInt(inputWeights.getText().toString());
        String inputNotesStr=inputNotes.getText().toString();

        //dodawanie daty
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date=sdf.format(new Date());

        dbHelper.createExercise(inputNameStr,inputSeriesInt,inputRepsInt,inputWeightsInt,inputNotesStr,date);
        dbHelper.close();

        clearInputs();
        requestFocus(inputName);
        Toast.makeText(getActivity().getApplicationContext(), "Exercise added!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateSeries() {
        String email = inputSeries.getText().toString().trim();

        if (email.isEmpty() ) {
            inputLayoutSeries.setError(getString(R.string.err_msg_series));
            requestFocus(inputSeries);
            return false;
        } else {
            inputLayoutSeries.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateReps() {
        String reps = inputReps.getText().toString().trim();

        if (reps.isEmpty() ) {
            inputLayoutReps.setError(getString(R.string.err_msg_reps));
            requestFocus(inputReps);
            return false;
        } else {
            inputLayoutReps.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateWeights() {
        String weights = inputWeights.getText().toString().trim();

        if (weights.isEmpty() ) {
            inputLayoutWeights.setError(getString(R.string.err_msg_weights));
            requestFocus(inputWeights);
            return false;
        } else {
            inputLayoutWeights.setErrorEnabled(false);
        }

        return true;
    }

    //wybór pola, na które focus
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    //czyszczenie pól po ich uzupełnieniu
    private void clearInputs(){
        inputName.setText("");
        inputSeries.setText("");
        inputReps.setText("");
        inputWeights.setText("");
        inputNotes.setText("");

    }





}
