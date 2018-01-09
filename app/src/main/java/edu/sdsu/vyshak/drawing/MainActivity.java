package edu.sdsu.vyshak.drawing;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    static Paint black;
    static Paint red;
    static Paint blue;
    static Paint green;
    public static String TAG="Main Activity";

    static{
        black = new Paint();
        black.setColor(Color.BLACK);
        red = new Paint();
        red.setColor(Color.RED);
        blue = new Paint();
        blue.setColor(Color.BLUE);
        green = new Paint();
        green.setColor(Color.GREEN);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.blackcolor:
                ClassDemoView.setColorChosen(black);
                return true;
            case R.id.redcolor:
                ClassDemoView.setColorChosen(red);
                return true;
            case R.id.greencolor:
                ClassDemoView.setColorChosen(green);
                return true;
            case R.id.bluecolor:
                ClassDemoView.setColorChosen(blue);
                return true;
            case R.id.delete:
                ClassDemoView.setOption("Delete");
                return true;
            case R.id.draw:
                ClassDemoView.setOption("Draw");
                return true;
            case R.id.move:
                ClassDemoView.setOption("Move");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
