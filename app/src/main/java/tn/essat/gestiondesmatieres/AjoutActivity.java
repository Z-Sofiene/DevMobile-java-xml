package tn.essat.gestiondesmatieres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AjoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);

        Button Ajout = findViewById(R.id.btnConfirme);
        Ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ttr = findViewById(R.id.tx1);
                String titre = ttr.getText().toString();
                EditText nv = findViewById(R.id.tx2);
                String niveau = nv.getText().toString();
                TextView Message = findViewById(R.id.textViewMessage);
                if (titre.equals("") || niveau.equals("")){
                    Message.setText("Les deux champs :\n - Titre\n - Niveau\n ne doivent pas etre vide !!! ");
                    return;
                }
                Intent i3 = new Intent(getApplicationContext(), MainActivity.class);
                i3.putExtra("Titre", titre);
                i3.putExtra("Niveau", niveau);
                startActivity(i3);
            }
        });

    }
}