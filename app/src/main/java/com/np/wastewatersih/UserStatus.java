package com.np.wastewatersih;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserStatus extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_status);  BottomNavigationView bottomNavigationView= findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.navigation_report:
                        Intent intent=new Intent(getApplicationContext(),forUser.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        UserStatus.this.startActivity(intent);
                        break;
                    case R.id.navigation_status:
                        Intent intent1=new Intent(getApplicationContext(),UserStatus.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        UserStatus.this.startActivity(intent1);
                        break;
                    case R.id.navigation_account:
                        Intent intent2=new Intent(getApplicationContext(),UserProfile.class);
                        //  startActivity(intent2);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        UserStatus.this.startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        mRecyclerView=findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads=new ArrayList<>();
        mDatabaseRef=FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Upload upload=postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                mAdapter=new MyAdapter(getApplicationContext(), mUploads);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}