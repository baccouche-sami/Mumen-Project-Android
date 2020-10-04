package com.example.android.mumenproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommandeActivity extends AppCompatActivity {
    //Spinner paysSpinner;
    TextView btn;
    EditText prenom, nom, nomEntreprise, pays, adresse, codePostal, ville, mobile, password, cpassword, email;
    String p, cp;
    JSONObject jsonObject;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);
        AndroidNetworking.initialize(this);
        prenom = findViewById(R.id.prenom);
        nom = findViewById(R.id.nom);
        nomEntreprise = findViewById(R.id.nomEntreprise);
        pays = findViewById(R.id.pays);
        adresse = findViewById(R.id.adresse);
        codePostal = findViewById(R.id.codePostal);
        ville = findViewById(R.id.ville);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.cpassword);
        p = password.getText().toString();
        cp = cpassword.getText().toString();


        btn = findViewById(R.id.suiv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (p.equals(cp)) {
                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("prenom", prenom.getText().toString());
                        jsonObject.put("nom", nom.getText().toString());
                        jsonObject.put("nomEntreprise", nomEntreprise.getText().toString());
                        jsonObject.put("pays", pays.getText().toString());
                        jsonObject.put("adresse", adresse.getText().toString());
                        jsonObject.put("codePostale", Integer.parseInt(codePostal.getText().toString()));
                        jsonObject.put("ville", ville.getText().toString());
                        jsonObject.put("mobile", Integer.parseInt(mobile.getText().toString()));
                        jsonObject.put("email", email.getText().toString());
                        jsonObject.put("password", password.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    OkHttpClient client = new OkHttpClient();
                    new OkHttpClient.Builder()
                            .addNetworkInterceptor(new StethoInterceptor())
                            .build();
                    RequestBody body = RequestBody.create(JSON,jsonObject.toString());
                    Request request = new Request.Builder()
                            .url("http://localhost:8080/clients")
                            .post(body)
                            .build();


                    client.newCall(request).enqueue(new Callback() {
                                                        @Override
                                                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                                            Log.i("test", "Working Perfectly");
                                                            Log.i("test", response.body().toString());
                                                        }

                                                        @Override
                                                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                                            Log.e("test", "Not Working ");
                                                        }

                                                    });





                        /*AndroidNetworking.post("http://localhost:8080/clients")
                                .addJSONObjectBody(jsonObject) // posting json
                                .setTag("test")
                                .setPriority(Priority.MEDIUM)
                                .build()
                                .getAsJSONArray(new JSONArrayRequestListener() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        Log.i("test","Working Perfectly");
                                        Log.i("test",response.toString());
                                    }
                                    @Override
                                    public void onError(ANError error) {
                                        Log.e("test","Not Working ");
                                    }
                                });*/

                            Intent i = new Intent(view.getContext(), Commande2Activity.class);
                    i.putExtra("prenom", prenom.getText());
                    i.putExtra("nom", nom.getText());
                    i.putExtra("nomEntreprise", nomEntreprise.getText());
                    i.putExtra("pays", pays.getText());
                    i.putExtra("codePostal", codePostal.getText());
                    i.putExtra("ville", ville.getText());
                    i.putExtra("mobile", mobile.getText());
                    i.putExtra("password", password.getText());
                    startActivity(i);
                } else {
                    Toast.makeText(view.getContext(), "Confirmez votre mot de password", Toast.LENGTH_LONG).show();
                }
            }
        });



        /*
        paysSpinner = (Spinner) findViewById(R.id.pays);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.pays_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        paysSpinner.setAdapter(staticAdapter);*/
    }
}