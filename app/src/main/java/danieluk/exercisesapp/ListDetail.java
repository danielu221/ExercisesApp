package danieluk.exercisesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.TextView;
public class ListDetail extends AppCompatActivity {

    //private Button btnDelete=(Button) findViewById(R.id.btn_delete);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_detail);

        final long id=getIntent().getLongExtra("id",-1);
        String name=getIntent().getStringExtra("name");
        int series=getIntent().getIntExtra("series",-1);
        int reps=getIntent().getIntExtra("reps",-1);
        int weights=getIntent().getIntExtra("weights",-1);

        String notes=getIntent().getStringExtra("notes");
        TextView txtName=(TextView) findViewById(R.id.detail_name);
        TextView txtSeries=(TextView) findViewById(R.id.detail_series);
        TextView txtReps=(TextView) findViewById(R.id.detail_reps);
        TextView txtWeights=(TextView) findViewById(R.id.detail_weights);
        TextView txtNotes=(TextView) findViewById(R.id.detail_notes);

        txtName.setText(name);
        txtSeries.setText(Integer.toString(series));
        txtReps.setText(Integer.toString(reps));
        txtWeights.setText(Integer.toString(weights));
        txtNotes.setText(notes);

        Button btnDelete=(Button) findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent();
                intent.putExtra("id",id);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}
