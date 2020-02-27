package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private TextView hasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        hasil = findViewById(R.id.result);

        Bundle extras = getIntent().getExtras();
        String result = getIntent().getExtras().getString("winner");
        if (extras != null){
            hasil.setText(result);
        }
    }
}
