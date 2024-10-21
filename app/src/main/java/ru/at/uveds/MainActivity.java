package ru.at.uveds;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        RadioGroup rg = findViewById(R.id.radioGroup);
        CheckBox toast = findViewById(R.id.checkToast);
        RadioButton rd, rc, rf;
        rd = findViewById(R.id.defaultToastCheck);
        rc = findViewById(R.id.customToastCheck);
        rf = findViewById(R.id.furinaToastCheck);
        rd.setEnabled(false);
        rc.setEnabled(false);
        rf.setEnabled(false);
        rf.setChecked(true);
        toast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                rd.setEnabled(!rd.isEnabled());
                rc.setEnabled(!rc.isEnabled());
                rf.setEnabled(!rf.isEnabled());
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                TextView selection = findViewById(R.id.check);
                if (i == R.id.defaultToastCheck) {
                    selection.setText("Вы выбрали default");
                } else if (i == R.id.customToastCheck) {
                    selection.setText("Вы выбрали custom");
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void showToast(View view) {

        RadioButton rb = findViewById(R.id.defaultToastCheck);
        RadioButton rc = findViewById(R.id.customToastCheck);
        if (rb.isChecked()) {
            Toast toast = Toast.makeText(this, "Это Toast", Toast.LENGTH_LONG);

            toast.setGravity(Gravity.TOP, 0, 160);
            toast.setMargin(50, 50);
            toast.show();
        } else if (rc.isChecked()) {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.center_toast, (ViewGroup) findViewById(R.id.toast_layout));

            TextView text = layout.findViewById(R.id.text);
            text.setText("я по центру");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        } else {
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            LinearLayout toastLayout = new LinearLayout(getApplicationContext());

            ImageView img = new ImageView(getApplicationContext());
            img.setImageResource(R.drawable.furina);
            toastLayout.addView(img, 0);
            toast.setView(toastLayout);
            toast.show();
        }
    }

    public void showSnackbar(View view) {
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
        ToggleButton fc = findViewById(R.id.furinaButton);
        if (fc.isChecked()) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View customSnackbarView = inflater.inflate(R.layout.image_snack, null);
            ImageView image = customSnackbarView.findViewById(R.id.snackbar_image);
            TextView text = customSnackbarView.findViewById(R.id.snackbar_text);
            text.setText("Сюрприз?)");
            image.setImageResource(R.drawable.furina);
            snackbar.getView().setVisibility(View.INVISIBLE);
            @SuppressLint("RestrictedApi") Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
            layout.removeAllViews();
            layout.addView(customSnackbarView);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Сюрприз!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            snackbar.setText("Сюрприз?)");
            snackbar.setAction("Да!", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Сюрприз!", Toast.LENGTH_LONG).show();
                }
            });
        }

        snackbar.show();
    }


    public void uved(View view) {
        CheckBox toast, snack;
        toast = findViewById(R.id.checkToast);
        snack = findViewById(R.id.checkSnack);
        if (toast.isChecked()) {
            showToast(view);
        }
        if (snack.isChecked()) {
            showSnackbar(view);
        }


    }

    public void onFurinaClicked(View view) {
        Toast.makeText(getApplicationContext(), ((ToggleButton) view).getText(), Toast.LENGTH_SHORT).show();
    }
}
