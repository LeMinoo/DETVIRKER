package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    private Button mFirebaseBtn;
    private DatabaseReference mDatabase;
    private EditText mNameFiedl;
    private EditText mPriceField;
    private EditText mDecription;
    private String cat1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseBtn=(Button) findViewById(R.id.FirebaseButton);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mNameFiedl=(EditText) findViewById(R.id.Name);
        mNameFiedl.setOnClickListener(this);
        mPriceField=(EditText) findViewById(R.id.price);
        mPriceField.setOnClickListener(this);
        mDecription=(EditText) findViewById(R.id.description);
        mPriceField.setOnClickListener(this);
        Spinner spinner=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.Catagory,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

      mFirebaseBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name =mNameFiedl.getText().toString().trim();
                String price=mPriceField.getText().toString().trim();
                String decription=mDecription.getText().toString().trim();
                //create af child in root object
                // asign som valuse to that

                HashMap<String,String> dataMap=new HashMap<String, String>();
                dataMap.put("Price",price);
                dataMap.put("decription",decription);


                mDatabase.child("Catagory").child(cat1).child(name).setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this,"item added",Toast.LENGTH_LONG).show();
                        } else
                        {
                            Toast.makeText(MainActivity.this,"Error you fucktard",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String cat=parent.getItemAtPosition(position).toString();
        cat1=cat;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View v) {
            mNameFiedl.getText().clear();
            mPriceField.getText().clear();
            mDecription.getText().clear();
    }
}
