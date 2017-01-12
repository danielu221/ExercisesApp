package danieluk.exercisesapp;



import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * AddExerciseFragment - fragment odpowiedzialny za dodawanie ćwiczeń
 *
 *
 */

public class AddExerciseFragment extends Fragment {
    private ExercisesDbAdapter dbHelper;
    private TextInputLayout inputLayoutName,inputLayoutSeries,inputLayoutReps,inputLayoutWeights;
    private EditText inputName,inputSeries,inputReps,inputWeights,inputNotes,inputDate;
    private Button btnEnter;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

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
        inputDate=(EditText) view.findViewById(R.id.input_date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow=sdf.format(new Date());
        inputDate.setText(dateNow);
        myCalendar = Calendar.getInstance();

        // ustawianie daty na podstawie kalendarza
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(); // ustawianie aktualnej wartości pola
            }

        };

        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnEnter=(Button) view.findViewById(R.id.btn_enter);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        return view;
    }
    // ustawienie wprowadzonej daty, na te wybrana z kalendarza
    private void updateLabel(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        inputDate.setText(sdf.format(myCalendar.getTime()));
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
        String inputDateStr=inputDate.getText().toString();

        dbHelper.createExercise(inputNameStr,inputSeriesInt,inputRepsInt,inputWeightsInt,inputNotesStr,inputDateStr);
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
        String series = inputSeries.getText().toString().trim();

        if (series.isEmpty() ) {
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
