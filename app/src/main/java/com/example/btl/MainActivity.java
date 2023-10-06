package com.example.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edtTenDangNhap;
    EditText edtMatKhau;
    CheckBox chkLuu;
    Button btnDangNhap;
    Button btnThoat;

    String thongtinluu="tk_mk login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        chkLuu = findViewById(R.id.chkLuu);
        btnDangNhap=findViewById(R.id.btnDangNhap);
        btnThoat = findViewById(R.id.btnThoat);

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(1);
            }
        });


        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(thongtinluu,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("UserName", edtTenDangNhap.getText().toString());
                editor.putString("PassWord", edtMatKhau.getText().toString());
                editor.putBoolean("Save",chkLuu.isChecked());
                editor.commit();

                String ten = edtTenDangNhap.getText().toString();
                String matKhau = edtMatKhau.getText().toString();

                if( matKhau.isEmpty())
                {
                    edtMatKhau.setError("Bạn chưa nhập mật khẩu");
                }
                if( ten.isEmpty())
                {
                    edtTenDangNhap.setError("Bạn chưa nhập tên đăng nhập");
                }
                if(ten.equals("admin") && matKhau.equals("admin"))
                {
                    Toast.makeText(MainActivity.this, "Đã đăng nhập", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent( MainActivity.this,GiaoDienChinh.class);
                    startActivity(intent);
                }

            }
        });
    }
    protected void onResume() {
        SharedPreferences sharedPreferences =getSharedPreferences(thongtinluu, MODE_PRIVATE);
        String username =  sharedPreferences.getString("UserName", "");
        String password =  sharedPreferences.getString("PassWord","");
        Boolean save = sharedPreferences.getBoolean("Save", false);
        if(save == true){
            edtTenDangNhap.setText(username);
            edtMatKhau.setText(password);
        }
        super.onResume();
    }

}