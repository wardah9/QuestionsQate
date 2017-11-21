package com.questionqate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.questionqate.Score_AchievementsList.Score_Achievements;
import com.questionqate.StudentProfile.Profile;
import com.questionqate.SubjectList_stu.SubjectListAdapter;

public class StudentSlideMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SubjectListAdapter subjecAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_slide_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        subjecAdapter = new SubjectListAdapter(this);

        RecyclerView subjects_list = findViewById(R.id.subjects_list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        subjects_list.setLayoutManager(mLayoutManager);
        subjects_list.setItemAnimator(new DefaultItemAnimator());
        subjects_list.setAdapter(subjecAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Home) {
            // wt ?

        } else if (id == R.id.nav_score_achievements) {
            startActivity(new Intent(StudentSlideMenu.this, Score_Achievements.class));

        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(StudentSlideMenu.this, Profile.class));

        } else if (id == R.id.nav_share) {
            //share ??

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
