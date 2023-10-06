package com.example.btl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class GiaoDienChinh extends AppCompatActivity {
    Button btnKhachHang;
    Button btnVatTu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giao_dien_chinh);

        btnKhachHang = findViewById(R.id.btnKhachHang);
        btnVatTu = findViewById(R.id.btnVatTu);
        btnKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( GiaoDienChinh.this,QuanLyKhachHang.class);
                startActivity(intent);
            }
        });
        btnVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( GiaoDienChinh.this,QuanLyVatTu.class);
                startActivity(intent);
            }
        });


    }
}
