package nanodegree.udacity.jokedisplaylibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {
    public static String JOKE_KEY = "joke-key";
    TextView jokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokedisplay);
        Intent intent = getIntent();
        jokeTextView = findViewById(R.id.joke_tv);
        if (intent != null) {
            String joke = intent.getStringExtra(JokeDisplayActivity.JOKE_KEY);
            if (joke != null) {
                jokeTextView.setText(joke);
                //Toast.makeText(this, joke, Toast.LENGTH_LONG).show();
            }
        }
    }
}
