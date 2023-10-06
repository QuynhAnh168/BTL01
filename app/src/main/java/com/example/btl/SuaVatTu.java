package com.example.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.btl.model.VatTu;
public class SuaVatTu extends AppCompatActivity {
    private EditText edtIDVatTu;
    private EditText edtTenVatTu;
    private EditText edtSoLuong;
    private Spinner spnLoaiVatTu;
    private Button btnLuu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_vat_tu);

        // Ánh xạ các thành phần giao diện
        edtIDVatTu = findViewById(R.id.edtIDVatTu);
        edtTenVatTu = findViewById(R.id.edtTenVatTu);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        spnLoaiVatTu = findViewById(R.id.spnLoaiVatTu);
        btnLuu = findViewById(R.id.btnLuu);
        DataBase dataBase = new DataBase(this);
        SharedPreferences sharedPreferences = getSharedPreferences("VatTuData", Context.MODE_PRIVATE);
        // Load dữ liệu từ SharedPreferences và đổ vào các trường
        loadDataFromSharedPreferences();

        // Xử lý sự kiện nút "Cập nhật"
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capNhatVatTu();
            }
        });
        edtIDVatTu.setEnabled(false);
        edtIDVatTu.setFocusable(false);
    }

    private void loadDataFromSharedPreferences() {
        // Đọc dữ liệu từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("VatTuData", Context.MODE_PRIVATE);
        String idVatTu = sharedPreferences.getString("idVatTu", "");
        String tenVatTu = sharedPreferences.getString("tenVatTu", "");
        int soLuong = sharedPreferences.getInt("soLuong", 0);
        String loaiVatTu = sharedPreferences.getString("loaiVatTu", "");

        // Đổ dữ liệu VatTu vào các trường
        edtIDVatTu.setText(idVatTu);
        edtTenVatTu.setText(tenVatTu);
        edtSoLuong.setText(String.valueOf(soLuong));
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.material_types, android.R.layout.simple_spinner_item);        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLoaiVatTu.setAdapter(spinnerAdapter);
        int spinnerPosition = spinnerAdapter.getPosition(loaiVatTu);
        spnLoaiVatTu.setSelection(spinnerPosition);
    }
    private void capNhatVatTu() {
        // Lấy dữ liệu từ các trường
        String idVatTu = edtIDVatTu.getText().toString();
        String tenVatTu = edtTenVatTu.getText().toString();
        int soLuong = Integer.parseInt(edtSoLuong.getText().toString());
        String loaiVatTu = spnLoaiVatTu.getSelectedItem().toString();
        if (idVatTu.isEmpty())
        {
            edtIDVatTu.setError("Chưa nhập id vật tư");
        }
        else
        if (tenVatTu.isEmpty())
        {
            edtTenVatTu.setError("Chưa nhập tên vật tư");
        }
        else {
            // Tạo một đối tượng DatabaseHelper
            DataBase dataBase = new DataBase(this);

            // Cập nhật dữ liệu vật tư trong CSDL
            boolean success = dataBase.capNhatVatTu(idVatTu, tenVatTu, loaiVatTu, soLuong);

            if (success) {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                // Cập nhật dữ liệu trong SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("VatTuData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("idVatTu", idVatTu);
                editor.putString("tenVatTu", tenVatTu);
                editor.putInt("soLuong", soLuong);
                editor.putString("loaiVatTu", loaiVatTu);
                editor.apply();

                finish(); // Đóng activity sau khi cập nhật thành công
            } else {
                Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
