package com.product.tabletmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.product.tabletmanager.view.fragment.FindDrugFragment;
import com.product.tabletmanager.view.fragment.ListOfDrugsFragment;

public class MainActivity extends AppCompatActivity {

    NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }
}
