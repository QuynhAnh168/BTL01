package com.example.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.btl.model.VatTu;

public class ThemVatTu extends AppCompatActivity {
    EditText edtIDVatTu, edtTenVatTu, edtSoLuong;
    Button btnThem;
    Spinner spnLoaiVatTu;

    String DBName = "BaiTapLon";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_vat_tu);
        // Ánh xạ
        edtIDVatTu = findViewById(R.id.edtIDVatTu);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        edtTenVatTu = findViewById(R.id.edtTenVatTu);
        btnThem = findViewById(R.id.btnThem);
        spnLoaiVatTu = findViewById(R.id.spnLoaiVatTu);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of the database
                DataBase dataBase = new DataBase(ThemVatTu.this, DBName, null, 1);

                // Get input values from the EditText and Spinner
                String idVatTu = edtIDVatTu.getText().toString();
                String tenVatTu = edtTenVatTu.getText().toString();
                //String loaiVatTu = edtLoaiVatTu.getText().toString();
                String loaiVatTu = spnLoaiVatTu.getSelectedItem().toString();
                String soLuongString = edtSoLuong.getText().toString();

                // Convert the quantity value to int
                int soLuong;
                try {
                    soLuong = Integer.parseInt(soLuongString);
                } catch (NumberFormatException e) {
                    // Handle the case where the value cannot be converted to int
                    edtSoLuong.setError("Sai định dạng");
                    return; // Exit the method
                }


                if (idVatTu.isEmpty())
                {
                    edtIDVatTu.setError("Chưa nhập id vật tư");
                }
                else
                if (tenVatTu.isEmpty())
                {
                    edtTenVatTu.setError("Chưa nhập tên vật tư");
                }
                else

                if (soLuongString.isEmpty())
                {
                    edtSoLuong.setError("Chưa nhập số lượng");
                }
                else
                {
                    if (dataBase.vatTuExists(idVatTu)) {
                        Toast.makeText(ThemVatTu.this, "ID đã tồn tại", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        // Create a new VatTu object with the retrieved information
                        VatTu vatTu = new VatTu(idVatTu, tenVatTu, loaiVatTu, soLuong);

                        // Add the VatTu object to the database
                        dataBase.addVatTu(vatTu);
                        Toast.makeText(ThemVatTu.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ThemVatTu.this, QuanLyVatTu.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }
}