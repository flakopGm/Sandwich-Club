package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;

        sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    // Método auxiliar para no dejar huecos vacíos mostrando un simple texto informativo.
    // (Para no dejar el espacio en blanco cuando no haya info que mostrar).
    public String textoVacio(String texto) {
        if (texto.equals("")) {
            return getString(R.string.no_data);
        } else {
            return texto;
        }
    }

    private void populateUI(Sandwich sandwich) {
        // Localizamos los textView para posicionar la información resultante.
        TextView conocidoComo = findViewById(R.id.also_known_tv);
        TextView originarioDe = findViewById(R.id.origin_tv);
        TextView ingredientes = findViewById(R.id.ingredients_tv);
        TextView descripcion = findViewById(R.id.description_tv);

        originarioDe.setText(textoVacio(sandwich.getPlaceOfOrigin()));
        descripcion.setText(textoVacio(sandwich.getDescription()));

        List<String> listaApodos = sandwich.getAlsoKnownAs();
        String apodoSandwich = "";
        for (String s : listaApodos) {
            apodoSandwich += s + "\n";
        }
        conocidoComo.setText(textoVacio(apodoSandwich));

        List<String> listaIngredientes = sandwich.getIngredients();
        String ingredientesVarios = "";
        for (String i : listaIngredientes) {
            ingredientesVarios += i + "\n";
        }
        ingredientes.setText(textoVacio(ingredientesVarios));
    }
}
