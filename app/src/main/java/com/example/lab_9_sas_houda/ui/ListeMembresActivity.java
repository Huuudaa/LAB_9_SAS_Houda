package com.example.lab_9_sas_houda.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lab_9_sas_houda.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListeMembresActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> liste;
    RequestQueue fileRequetes;

    private static final String URL =
            "http://10.0.2.2/projet/ws/ListerMembre.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_membres);

        listView = findViewById(R.id.listViewMembres);
        liste = new ArrayList<>();
        fileRequetes = Volley.newRequestQueue(this);

        chargerMembres();
    }

    private void chargerMembres() {

        StringRequest requete = new StringRequest(
                Request.Method.GET,
                URL,
                response -> {
                    Log.d("API_RESPONSE", response);

                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        if (jsonArray.length() == 0) {
                            Toast.makeText(this, "Aucun membre trouvé", Toast.LENGTH_SHORT).show();
                        }

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String nom = obj.optString("nom", "N/A");
                            String prenom = obj.optString("prenom", "N/A");
                            String ville = obj.optString("ville", "N/A");

                            liste.add(prenom + " " + nom + " - " + ville);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                this,
                                android.R.layout.simple_list_item_1,
                                liste
                        );

                        listView.setAdapter(adapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Erreur parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("API_ERROR", error.toString());
                    Toast.makeText(this, "Erreur connexion serveur", Toast.LENGTH_LONG).show();
                }
        );

        fileRequetes.add(requete);
    }
}