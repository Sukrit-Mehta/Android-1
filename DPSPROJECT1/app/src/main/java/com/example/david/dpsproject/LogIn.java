package com.example.david.dpsproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

/**
 * Created by david on 2016-10-25.
 */
public class LogIn extends Fragment implements View.OnClickListener {
    View myView;
    FirebaseAuth authentication;
    DatabaseReference dbReference;
    FirebaseAuth.AuthStateListener authStateListener;
    EditText userName;
    EditText userPassword;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Login");
        myView = inflater.inflate(R.layout.login_in,container,false);
        authentication= FirebaseAuth.getInstance(); // get instance of my firebase console
        dbReference = FirebaseDatabase.getInstance().getReference(); // access to database
        FirebaseUser firebaseUser = authentication.getCurrentUser();

              /*  if(firebaseUser!=null){
            Intent front_page = new Intent(getApplicationContext(),FrontPage.class);
            front_page.putExtra("userId",firebaseUser.getUid().toString());
            startActivity(front_page);
            finish();
        }
        authStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               // FirebaseUser
            }
        };*/


        userName = (EditText)myView.findViewById(R.id.userName);
        userPassword = (EditText) myView.findViewById(R.id.userPassword);
        Button b = (Button) myView.findViewById(R.id.signIn);
        b.setOnClickListener(this);



        return myView;
    }

    @Override
    public void onClick(View v) {
        Login(userName.getText().toString(),userPassword.getText().toString());
    }
    protected void Login(final String email, final String password){
        authentication.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // HideProgressDialog();
                if(!(task.isSuccessful())){
                    Toast.makeText(getContext(),"authentication failed",Toast.LENGTH_SHORT).show();
                }
                else{
                    Bundle bundle = new Bundle();
                    Users u = new Users(email,password);
                    FrontPage fragment = new FrontPage();
                    FragmentManager fragmentManager = getFragmentManager();

                    dbReference.child("Users").child(task.getResult().getUser().getUid()).setValue(u);
                    bundle.putString("UID",task.getResult().getUser().getUid().toString());
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();
                   // setArguments(bundle);
                   // fragmentManager.beginTransaction().replace(R.id.content_frame,new FirstFragment()).commit();
                }
            }
        });
    }
}
