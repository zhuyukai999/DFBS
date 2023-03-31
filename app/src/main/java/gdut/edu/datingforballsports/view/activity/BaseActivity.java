package gdut.edu.datingforballsports.view.activity;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    protected void buttonClick(int id, View.OnClickListener listener) {
        findViewById(id).setOnClickListener(listener);
    }

}
