package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject cartaSandwiches = new JSONObject(json);
            // Extraemos su nombre  y su nombre conocido.
            JSONObject nameJSON = cartaSandwiches.getJSONObject("name");
            String mainNameJSON = nameJSON.getString("mainName");
            JSONArray alsoKnownAsJSON = nameJSON.getJSONArray("alsoKnownAs");
            ArrayList<String> alsoKnownAsList = new ArrayList<>();
            for (int x = 0; x < alsoKnownAsJSON.length(); x++) {
                alsoKnownAsList.add(alsoKnownAsJSON.getString(x));
            }

            // Realizamos la misma acción con el resto de la información.
            String placeOfOriginJSON = cartaSandwiches.getString("placeOfOrigin");
            String descriptionJSON = cartaSandwiches.getString("description");
            String imageUrlJSON = cartaSandwiches.getString("image");
            JSONArray ingredientsJSON = cartaSandwiches.getJSONArray("ingredients");
            ArrayList<String> listaDeIngredientes = new ArrayList();
            for (int x = 0; x < ingredientsJSON.length(); x++) {
                listaDeIngredientes.add(ingredientsJSON.getString(x));
            }
            // Retornamos el sandwich con la info solicitada.
            return new Sandwich(mainNameJSON, alsoKnownAsList, placeOfOriginJSON, descriptionJSON,
                    imageUrlJSON, listaDeIngredientes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
