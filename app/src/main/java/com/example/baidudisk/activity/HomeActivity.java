package com.example.baidudisk.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.baidudisk.R;

public class HomeActivity extends AppCompatActivity{
    FileFragment fileFragment=new FileFragment();
    ProFileFragment profileFragment=new ProFileFragment();
    FragmentManager fragmentManager=getSupportFragmentManager();
    FragmentTransaction transaction =fragmentManager.beginTransaction();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        transaction.add(R.id.fragment,fileFragment);
        transaction.commit();
    }

    public void mePage(View view) {
        changeFragment(R.id.fragment,profileFragment);
    }

    public void filePage(View view) {
        changeFragment(R.id.fragment,fileFragment);
    }

    void changeFragment(int resource,Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        transaction.replace(resource,fragment);
        transaction.commit();
    }
}