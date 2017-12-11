package com.questionqate.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.questionqate.Adapters.SubjectListAdapter;
import com.questionqate.Dialog.LoadingDialog;
import com.questionqate.R;
import com.questionqate.StudentProfile.Profile;

import org.json.JSONObject;


public class StudentSlideMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SubjectListAdapter subjecAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_slide_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        RecyclerView subjects_list = findViewById(R.id.subjects_list);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        subjects_list.setLayoutManager(mLayoutManager);

        subjects_list.setItemAnimator(new DefaultItemAnimator());

        LoadingDialog dialog = new LoadingDialog(this, "Loading.. please wait!");
        dialog.setCancelable(false);
        dialog.show();

        AndroidNetworking.post(" https://us-central1-questionsqate-9a3d7.cloudfunctions.net/getSubjects")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        subjecAdapter = new SubjectListAdapter(StudentSlideMenu.this, response);
                        subjects_list.setAdapter(subjecAdapter);
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(StudentSlideMenu.this, "cannot get subjects", Toast.LENGTH_SHORT).show();
                        System.out.println("getSubjectError: "+ anError.getErrorBody());
                    }
                });


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
            finish();
            //  super.onBackPressed();
        }
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_score_achievements) {
            startActivity(new Intent(StudentSlideMenu.this, Score_Achievements.class));

        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(StudentSlideMenu.this, Profile.class));


        } else if (id == R.id.nav_share) {
            setShareIntent();

        } else if (id == R.id.nav_exit) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setShareIntent() {

    }

}
