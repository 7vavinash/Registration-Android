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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Signup_DjangoActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    EditText et_email, et_password, et_username;
    String email, password, username;
    Button b_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__django);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_username = (EditText) findViewById(R.id.et_username);
        b_signup = (Button) findViewById(R.id.b_signup);

        // Volley
        requestQueue = Volley.newRequestQueue(this);

        b_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email.getText().toString();
                username = et_username.getText().toString();
                password = et_password.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    signupUser();
                }

            }
        });
    }

    private void signupUser() {
        String url = "http://10.1.130.82:8000/signup/";    // replace with ur own url

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    Intent i = new Intent(getApplication(), Login_DjangoActivity.class);
                    startActivity(i);
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Signup_DjangoActivity.this, "Failed to Login",
                            Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", username);
                    params.put("email", email);
                    params.put("password1", password);
                    params.put("password2", password);

                    return params;
                }
            };
            requestQueue.add(request);
    }
}
