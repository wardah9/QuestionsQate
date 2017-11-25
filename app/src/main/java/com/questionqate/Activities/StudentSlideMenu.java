package com.questionqate.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.questionqate.Adapters.SubjectListAdapter;
import com.questionqate.Pojo.Student;
import com.questionqate.R;
import com.questionqate.StudentProfile.Profile;


public class StudentSlideMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SubjectListAdapter subjecAdapter;
    TextView nav_name;
    TextView nav_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_slide_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FirebaseDatabase udatabase = FirebaseDatabase.getInstance();
        DatabaseReference umyRef = udatabase.getReference("My_students");

        subjecAdapter = new SubjectListAdapter(this);
        RecyclerView subjects_list = findViewById(R.id.subjects_list);

        nav_name.findViewById(R.id.nav_name);
        nav_email.findViewById(R.id.nav_email);

        umyRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {

                            Log.v("firbase", "key is : " + postSnapShot.getKey()
                                    + " value  is " + postSnapShot.getValue());

                            Student firebaseStudentDetails = dataSnapshot.getValue(Student.class);

                            assert firebaseStudentDetails != null;
                            nav_name.setText(firebaseStudentDetails.getUserName());
                            nav_email.setText(firebaseStudentDetails.getUserEmail());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Failed to read value
                        Log.w("firebase", "Failed to read data.", databaseError.toException());
                    }
                });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        subjects_list.setLayoutManager(mLayoutManager);
        subjects_list.setItemAnimator(new DefaultItemAnimator());
        subjects_list.setAdapter(subjecAdapter);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_score_achievements) {
            startActivity(new Intent(StudentSlideMenu.this, Score_Achievements.class));

        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(StudentSlideMenu.this, Profile.class));

        } else if (id == R.id.nav_share) {
            ShareMyApp();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void ShareMyApp() {

    }

}
