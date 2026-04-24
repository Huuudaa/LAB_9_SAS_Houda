package com.example.lab_9_sas_houda.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lab_9_sas_houda.R;
import com.example.lab_9_sas_houda.beans.Membre;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AjouterMembreActivity extends AppCompatActivity
        implements View.OnClickListener {

    private EditText editNom, editPrenom;
    private Spinner spinnerVille;
    private RadioButton radioHomme, radioFemme;
    private Button btnAjouter, btnVoirMembres;
    private TextView tvReponse;
    private RequestQueue fileRequetes;

    private static final String URL_AJOUT =
            "http://10.0.2.2/projet/ws/ajouterMembre.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_membre);

        // Initialisation des composants
        editNom = findViewById(R.id.editNom);
        editPrenom = findViewById(R.id.editPrenom);
        spinnerVille = findViewById(R.id.spinnerVille);
        radioHomme = findViewById(R.id.radioHomme);
        radioFemme = findViewById(R.id.radioFemme);
        btnAjouter = findViewById(R.id.btnAjouter);
        btnVoirMembres = findViewById(R.id.btnVoirMembres);
        tvReponse = findViewById(R.id.tvReponse);

        fileRequetes = Volley.newRequestQueue(this);

        btnAjouter.setOnClickListener(this);

        // Bouton voir liste des membres
        btnVoirMembres.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListeMembresActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnAjouter) {
            envoyerMembre();
        }
    }

    private void envoyerMembre() {
        StringRequest requete = new StringRequest(
                Request.Method.POST,
                URL_AJOUT,
                reponse -> {
                    Log.d("REPONSE", reponse);

                    tvReponse.setText("");
                    Toast.makeText(this, "Membre ajouté avec succès !", Toast.LENGTH_SHORT).show();
                },
                erreur -> {
                    Log.e("VOLLEY", "Erreur : " + erreur.getMessage());
                    tvReponse.setText("Erreur réseau : " + erreur.getMessage());
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                String genre = radioHomme.isChecked() ? "homme" : "femme";

                Map<String, String> parametres = new HashMap<>();
                parametres.put("nom", editNom.getText().toString().trim());
                parametres.put("prenom", editPrenom.getText().toString().trim());
                parametres.put("ville", spinnerVille.getSelectedItem().toString());
                parametres.put("genre", genre);

                return parametres;
            }
        };

        fileRequetes.add(requete);
    }
}