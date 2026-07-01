package com.example.imc;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etPoids;
    private EditText etTaille;
    private Button btnCalculer;
    private TextView tvResultat;
    private TextView tvCategorie;
    private ImageView imgCategorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupération des vues
        etPoids      = findViewById(R.id.etPoids);
        etTaille     = findViewById(R.id.etTaille);
        btnCalculer  = findViewById(R.id.btnCalculer);
        tvResultat   = findViewById(R.id.tvResultat);
        tvCategorie  = findViewById(R.id.tvCategorie);
        imgCategorie = findViewById(R.id.imgCategorie);

        // Action du bouton "Calculer IMC"
        btnCalculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculerIMC();
            }
        });
    }

    private void calculerIMC() {
        String poidsStr  = etPoids.getText().toString().trim();
        String tailleStr = etTaille.getText().toString().trim();

        // Vérification : champs non vides
        if (TextUtils.isEmpty(poidsStr) || TextUtils.isEmpty(tailleStr)) {
            Toast.makeText(this, R.string.err_champs_vides, Toast.LENGTH_SHORT).show();
            return;
        }

        double poids;   // en Kg
        double tailleCm; // en cm
        try {
            poids    = Double.parseDouble(poidsStr.replace(",", "."));
            tailleCm = Double.parseDouble(tailleStr.replace(",", "."));
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.err_valeurs, Toast.LENGTH_SHORT).show();
            return;
        }

        // Vérification : valeurs strictement positives
        if (poids <= 0 || tailleCm <= 0) {
            Toast.makeText(this, R.string.err_valeurs, Toast.LENGTH_SHORT).show();
            return;
        }

        // Calcul de l'IMC : poids (kg) / taille (m)^2
        double tailleM = tailleCm / 100.0;
        double imc = poids / (tailleM * tailleM);

        // Affichage du résultat (format français : 2 décimales, virgule)
        String imcFormate = String.format(Locale.FRANCE, "%.2f", imc);
        tvResultat.setText(getString(R.string.resultat_defaut) + " " + imcFormate);

        // Affichage de la catégorie et de l'image adéquate
        afficherCategorie(imc);
    }

    private void afficherCategorie(double imc) {
        if (imc < 18.5) {
            tvCategorie.setText(R.string.cat_maigreur);
            imgCategorie.setImageResource(R.drawable.maigre);
        } else if (imc < 25) {
            tvCategorie.setText(R.string.cat_normal);
            imgCategorie.setImageResource(R.drawable.normal);
        } else if (imc < 30) {
            tvCategorie.setText(R.string.cat_surpoids);
            imgCategorie.setImageResource(R.drawable.surpoids);
        } else if (imc < 35) {
            tvCategorie.setText(R.string.cat_obese);
            imgCategorie.setImageResource(R.drawable.obese);
        } else {
            tvCategorie.setText(R.string.cat_obese_extreme);
            imgCategorie.setImageResource(R.drawable.t_obese);
        }
    }
}
