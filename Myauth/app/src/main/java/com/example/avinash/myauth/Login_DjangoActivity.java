package com.example.avinash.myauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login_DjangoActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    EditText et_email, et_password;
    String email, password;
    Button b_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_django);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        b_login = (Button) findViewById(R.id.b_login);

        // Volley
        requestQueue = Volley.newRequestQueue(this);

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email.getText().toString();
                password = et_password.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    loginUser();
                }

            }
        });

    }

    private void loginUser() {
        String url = "http://10.1.130.82:8000/api-token-auth/login/";    // replace with ur own url
        String jsonString = "{username:"+email+",password:"+password+"}";
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
                    new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                SharedPref.setToken(getApplicationContext(), response.getString("token"));
                                Intent i = new Intent(getApplication(),Home.class);
                                startActivity(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Login_DjangoActivity.this, "Failed to Login",
                            Toast.LENGTH_SHORT).show();
                }
            } ) ;



            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
