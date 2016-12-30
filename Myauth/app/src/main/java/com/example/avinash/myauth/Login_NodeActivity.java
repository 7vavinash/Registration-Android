package com.example.avinash.myauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login_NodeActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    EditText et_email, et_password;
    String email, password;
    Button b_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__node);
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
        String url = "http://10.1.130.82:3000/users/login_jwt/";    // replace with ur own url
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
                    Toast.makeText(Login_NodeActivity.this, "Failed to Login",
                            Toast.LENGTH_SHORT).show();
                }
            } ) ;

//            When using string request to set parameters
//            {
//                @Override
//                protected Map<String, String> getParams() {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("username", email);
//                    params.put("password", password);
//
//                    return params;
//                }
//            };

            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
