package nyc.c4q.simongameupdate;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private TextView levelView;
    private int level = 1;
    private ArrayList<Button> player = new ArrayList<>();
    private ArrayList<Button> simon = new ArrayList<>();
    Animation blink;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blink = AnimationUtils.loadAnimation(this, R.anim.blink);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        levelView = (TextView) findViewById(R.id.level_view);

    }


    public void playSimon(View view) {
        Log.d(TAG, "HELLO");
        Button[] buttons = {button1, button2, button3, button4};
        int index = new Random().nextInt(buttons.length);
        Log.d(TAG, "Index: " + index);
        simon.add(buttons[index]);
        Log.d(TAG, "Simon size: " + simon.size());
        for (int i = 0; i < simon.size(); i++) {
            final int j = i;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    simon.get(j).startAnimation(blink);
                }
            }, 1000 * i + 1);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Your turn!", Toast.LENGTH_SHORT).show();
            }
        }, 1000 * simon.size());
    }



    public void playerClick(View view) {
        player.add((Button) view);
        view.startAnimation(blink);
        if (player.size() == simon.size()) {
            checkScore(view);
        }
    }

    public void checkScore(View view){
        Log.d(TAG,"PLAYER: "+player);
        Log.d(TAG,"SIMON: "+simon);
        if (!simon.equals(player)){
            Toast.makeText(getApplicationContext(),"You Lose",Toast.LENGTH_SHORT).show();
            simon = new ArrayList<>();
            player = new ArrayList<>();
            level = 1;
            levelView.setText("LEVEL : " + level);

        } else {
            player = new ArrayList<>();
            level++;
            levelView.setText("LEVEL : " + level);
            final View v = view;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    playSimon(v);
                }
            }, 1000);
            }

        }


}