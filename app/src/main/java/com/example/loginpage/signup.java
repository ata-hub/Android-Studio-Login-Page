package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class signup extends AppCompatActivity {
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        DB=new DBHelper(this);
        Button signupbtn=(Button) findViewById(R.id.signupbutton);
        TextView username=(TextView) findViewById(R.id.username);
        TextView password=(TextView) findViewById(R.id.password);
        TextView repassword=(TextView) findViewById(R.id.repassword);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=username.getText().toString();
                String pass=password.getText().toString();
                String repass=repassword.getText().toString();
                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass)){
                    Toast.makeText(signup.this, "All fields must be filled before signing up", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser=DB.checkUsername(user);
                        if(checkuser==false){
                            //username doesn't exist in database, safe to create new one
                            Boolean insert=DB.insertData(user,pass);
                            if(insert==true){
                                Toast.makeText(signup.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                //go to the activity page
                                Intent intent=new Intent(getApplicationContext(),SecondActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(signup.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            //username exists,taken, find new username
                            Toast.makeText(signup.this, "User already exists", Toast.LENGTH_SHORT).show();

                        }

                    }
                    else{
                        Toast.makeText(signup.this, "passwords are not matching", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}