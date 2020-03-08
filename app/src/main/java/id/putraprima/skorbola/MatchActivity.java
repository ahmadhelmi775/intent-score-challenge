package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import id.putraprima.skorbola.model.Data;

public class MatchActivity extends AppCompatActivity {
    public static final String DATA_KEY = "data";
    public static final String WINNER = "winner";
    private static final int SCORER_ACTIVITY_REQUEST_CODE = 0;
    private TextView homeText;
    private TextView awayText;
    private TextView hscore;
    private TextView ascore;
    private Data data;
    private ImageView homeLogo;
    private ImageView awayLogo;
    private int homeScore = 0;
    private int awayScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        homeText = findViewById(R.id.txt_home);
        awayText = findViewById(R.id.txt_away);
        hscore = (TextView) findViewById(R.id.score_home);
        ascore = (TextView) findViewById(R.id.score_away);
        homeLogo = findViewById(R.id.home_logo);
        awayLogo = findViewById(R.id.away_logo);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            data = getIntent().getParcelableExtra(DATA_KEY);
            awayText.setText(data.getAwayName());
            homeText.setText(data.getHomeName());

            Bundle extra = getIntent().getExtras();
            Bitmap home = extra.getParcelable("logoHome");
            Bitmap away = extra.getParcelable("logoAway");
            homeLogo.setImageBitmap(home);
            awayLogo.setImageBitmap(away);

        }
        //TODO
        //1.Menampilkan detail match sesuai data dari main activity
        //2.Tombol add score menambahkan memindah activity ke scorerActivity dimana pada scorer activity di isikan nama pencetak gol
        //3.Dari activity scorer akan mengirim kembali ke activity matchactivity otomatis nama pencetak gol dan skor bertambah +1
        //4.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang beserta nama pencetak gol ke ResultActivity, jika seri di kirim text "Draw",
    }

    public void handleResult(View view) {

        String winner;

        if (homeScore > awayScore) {
            winner = data.getHomeName() + " Is Winner";
        } else if (homeScore < awayScore) {
            winner = data.getAwayName() + " Is Winner";
        } else {
            winner = "DRAW";
        }

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(WINNER, winner);
        startActivity(intent);

    }

    public void handleAway(View view) {
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, SCORER_ACTIVITY_REQUEST_CODE);
        homeScore++;
        hscore.setText(Integer.toString(awayScore));
    }

    public void handleHome(View view) {
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, SCORER_ACTIVITY_REQUEST_CODE);
        awayScore++;
        ascore.setText(Integer.toString(homeScore));
    }

    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SCORER_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // Get String data from Intent
                String returnString = data.getStringExtra("keyName");


                // Set text view with string
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText(returnString);
            }
        }
    }
}