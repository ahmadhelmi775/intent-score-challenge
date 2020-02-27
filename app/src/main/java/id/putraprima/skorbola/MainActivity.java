package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import id.putraprima.skorbola.model.Data;

public class MainActivity extends AppCompatActivity {

    public static final String DATA_KEY = "data";
    public static final String LOGOHOME = "logoHome";
    public static final String LOGOAWAY = "logoAway";
    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODEX = 2;
    private static final String TAG = MainActivity.class.getCanonicalName();
    private EditText homeInput;
    private EditText awayInput;
    private ImageView homeLogo;
    private ImageView awayLogo;
    private Uri imageUri;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeInput = findViewById(R.id.home_team);
        awayInput = findViewById(R.id.away_team);
        homeLogo = findViewById(R.id.home_logo);
        awayLogo = findViewById(R.id.away_logo);

        //TODO
        //Fitur Main Activity
        //1. Validasi Input Home Team
        //2. Validasi Input Away Team
        //3. Ganti Logo Home Team
        //4. Ganti Logo Away Team
        //5. Next Button Pindah Ke MatchActivity
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            return;
        }
        if(requestCode == GALLERY_REQUEST_CODE){
            if(data != null){
                try{
                    imageUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    homeLogo.setImageBitmap(bitmap);
                } catch (IOException e){
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        } else if(requestCode == GALLERY_REQUEST_CODEX){
            if(data != null){
                try{
                    imageUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    awayLogo.setImageBitmap(bitmap);
                } catch (IOException e){
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void handleNext(View view) {

        String home = homeInput.getText().toString();
        String away = awayInput.getText().toString();

        homeLogo.buildDrawingCache();
        awayLogo.buildDrawingCache();

        Bitmap image = homeLogo.getDrawingCache();
        Bitmap image2 = awayLogo.getDrawingCache();

//        Validate Data
        if (home.isEmpty() && away.isEmpty()){
            homeInput.setError("Isi Terlebih Dahulu");
            awayInput.setError("Isi Terlebih Dahulu");
        }

        if (home.isEmpty()){
            homeInput.setError("Isi Terlebih Dahulu");
        } else if (away.isEmpty()){
            awayInput.setError("Isi Terlebih Dahulu");
        } else if (bitmap == null){
            Toast.makeText(this,"Silahkan Isi gambar terlebih dahulu", Toast.LENGTH_LONG).show();
        } else{
            Data data = new Data(home, away);
            Intent i = new Intent(this, MatchActivity.class);
            i.putExtra(DATA_KEY, data);
            i.putExtra(LOGOHOME, image);
            i.putExtra(LOGOAWAY, image2);
            startActivity(i);
        }

//        End Validate Data
    }

    public void changeLogohome(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public void changeLogoaway(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODEX);
    }
}
