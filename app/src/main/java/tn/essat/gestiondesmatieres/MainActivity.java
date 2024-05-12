package tn.essat.gestiondesmatieres;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    List<Matiere> liste;
    Vector<Integer> pds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyDB bd = new MyDB(getApplicationContext(), "mydb.db", null, 1);
        SQLiteDatabase table = bd.getWritableDatabase();

        Bundle bdl = getIntent().getExtras();
        if (bdl != null && bdl.containsKey("Titre") && bdl.containsKey("Niveau")) {
            String titre = bdl.getString("Titre");
            String niveau = bdl.getString("Niveau");
            ContentValues values = new ContentValues();
            values.put("titre", titre);
            values.put("niveau", niveau);
            table.insert("Matiere", null, values);
        }
        ListView lv = findViewById(R.id.lv1);
        afficherListeView(table,lv);

        Button Ajout = findViewById(R.id.btnAjout);
        Ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), AjoutActivity.class);
                startActivity(i1);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this);
                build.setTitle("Suppression");
                build.setMessage("Voulez-vous supprimer cette matiere?");
                build.setNegativeButton("Non", null);
                build.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int idToDelete = pds.get(position); // Get the ID of the clicked item

                        // Delete the item if ID is valid
                        if (idToDelete != -1) {
                            table.delete("Matiere", "id = ?", new String[]{String.valueOf(idToDelete)});
                            afficherListeView(table, lv);
                        } else {
                            Log.e("Error", "Échec de la recherche de l'identifiant à supprimer");
                        }
                    }
                });
                AlertDialog diag = build.create();
                diag.show();
            }
        });
    }
    public void afficherListeView(SQLiteDatabase bd, ListView lv){
        pds = new Vector<>();
        liste = new ArrayList<>();
        Cursor cr = bd.query("Matiere", new String[]{"id", "titre", "niveau"}, null, null, null, null, null);
        cr.moveToFirst();

        while(!cr.isAfterLast()) {
            int id = Integer.parseInt(cr.getString(0));
            String titre = cr.getString(1);
            String niveau = cr.getString(2);
            Matiere matiere = new Matiere(id, titre, niveau);
            liste.add(matiere);
            pds.add(new Integer(cr.getString(0)));
            cr.moveToNext();
        }
        cr.close();

        MyListAdapter adp= new MyListAdapter(getApplicationContext(),liste);
        lv.setAdapter(adp);
    }
}
