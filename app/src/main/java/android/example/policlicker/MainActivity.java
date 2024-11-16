package android.example.policlicker;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // se DECLARA obiectele create in .xml

    private TextView tvPoints;
    private int points=0; // ! PC se vor restarta la 0, la lansarea aplicatiei. Momentan, evolutia nu e salvata.
    private int cps=10; //cookies per second. Rata de crestere. Clasa CookieCounter va opera cu cps

    private CookieCounter cookieCounter = new CookieCounter();

    @Override
    public void onClick(View v) { //event la click pe emblema UPB
        if(v.getId() == R.id.emblema){ //incrementare
            Animation a = AnimationUtils.loadAnimation(this, R.anim.cookie_animation);//se creeaza o animatie
            a.setAnimationListener(new SimpleAnimationListener(){
                @Override
                public void onAnimationEnd(Animation animation){
                    cookieClick(); //metoda eveniment
                }
            });
            v.startAnimation(a);
        }
    }

    private void cookieClick() { //metoda eveniment
        points++; //la fiecare click, incrementare
        tvPoints.setText(Integer.toString(points)); // setTest cere String, dar Points e primitiva Integer
        //metoda CONFIGURATA pentru afisarea Toastului
        showToast(R.string.clicked); //clicked e String definit in res\values\strings.xml
    }

    private void showToast(int stringID) { //metoda de afisare a toast-ului primeste nr de afisat stringID
        // instantiere Toast
        Toast toast = new Toast(this);
        // configurare Toast
        toast.setGravity(Gravity.CENTER, 0, 0); // xOffset se scriu implicit, automayt
        toast.setDuration(Toast.LENGTH_SHORT);
        TextView textView = new TextView(this);
        textView.setText(stringID); //setText de ce merge si pe int?
        textView.setTextSize(40f); // f pt ca e float
        textView.setTextColor(Color.CYAN); //cyan, de la cyanide
        toast.setView(textView);
        // afisare Toast
        toast.show(); // asta este ACTIUNEA, de fapt, dar intreaga metoda showToast face o configurare
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPoints = findViewById(R.id.tvPoints); //variabila counter
    }

    public class CookieCounter{ // clasa pentru incrementare temporala. Opereaza cu cps
        private Timer timer;
        public void CookieCounter(){ // metoda
            timer = new Timer(); //instantiere timer.
            timer.scheduleAtFixedRate(new TimerTask(){ //3 argumente: task, delay, period. TASK e METODA
                @Override
                public void run(){
                    runOnUiThread(new Runnable(){
                        @Override
                                public void run(){
                                    update();
                        }
                    });
                }
            }, 1000, 1000); // delay: 1000ms dupa pornirea aplicatiei. Intre timp, se deschid alte procese.
        }
    }

    private void update() { //update la puncte, dupa fiecare secunda
        points += cps; // +cps, caci perioada este de 1000ms = 1s
        tvPoints.setText(Integer.toString(points));
    }


}