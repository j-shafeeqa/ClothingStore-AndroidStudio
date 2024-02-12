package com.example.assignment24;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class BottomNavigationConfig {

    public static void configure(MeowBottomNavigation bottomNavigation, AppCompatActivity activity) {
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.cart));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.profile));


        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        activity.startActivity(new Intent(activity, MainActivity.class));
                        break;
                    case 2:
                        activity.startActivity(new Intent(activity, CartActivity.class));
                        break;
                    case 3:
                        activity.startActivity(new Intent(activity, ProfileActivity.class));
                        break;
                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch(model.getId())
                {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
                return null;
            }
        });


        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch(model.getId())
                {
                    case 1:
                        break;
                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch(model.getId())
                {
                    case 2:
                        break;
                }
                return null;
            }
        });


        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch(model.getId())
                {
                    case 3:
                        break;
                }
                return null;
            }
        });

    }
}

