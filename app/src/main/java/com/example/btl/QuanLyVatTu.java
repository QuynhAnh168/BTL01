package com.example.btl;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.btl.model.VatTu;

import java.util.ArrayList;
import java.util.List;

public class QuanLyVatTu extends AppCompatActivity {

    private ListView vatTuListView;
    private VatTuAdapter vatTuAdapter;
    private ArrayList<VatTu> vatTuList;
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_vat_tu);

        vatTuListView = findViewById(R.id.vatTuListView);
        Button btnThem = findViewById(R.id.btnThem);
        Button btnTimKiem = findViewById(R.id.btnTimKiem);
        Button btnXuatVatTu = findViewById(R.id.btnXuatVatTu);
        EditText edtTenVatTu = findViewById(R.id.edtTenVatTu);
        EditText edtIDVatTu = findViewById(R.id.edtIDVatTu);

        dataBase = new DataBase(this);
        vatTuList = new ArrayList<>();
        vatTuAdapter = new VatTuAdapter(this, vatTuList);
        vatTuListView.setAdapter(vatTuAdapter);
        loadData();

        vatTuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Xử lý khi một mục trong ListView được nhấp
                Toast.makeText(QuanLyVatTu.this, "Đã nhấp vào mục " + position, Toast.LENGTH_SHORT).show();
            }
        });
        btnXuatVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyVatTu.this, XuatVatTu.class);
                startActivity(intent);
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyVatTu.this, ThemVatTu.class);
                startActivity(intent);
            }
        });
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idVatTu = edtIDVatTu.getText().toString();
                String tenVatTu = edtTenVatTu.getText().toString();

                timKiem(idVatTu, tenVatTu);
            }
        });
        vatTuAdapter.setOnDeleteClickListener(new VatTuAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                showDeleteConfirmationDialog(position);
            }
        });
        vatTuAdapter.setOnEditClickListener(new VatTuAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(int position) {
                VatTu selectedVatTu = vatTuList.get(position);
                suaVatTu(selectedVatTu);
                loadData();
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        // Tải lại dữ liệu từ SharedPreferences và hiển thị nó trong ListView
        loadData();
    }

    private void loadData() {
        Cursor cursor = dataBase.selectData("SELECT * FROM tblVatTu");
        vatTuList.clear();
        if (cursor.moveToFirst()) {
            do {
                String idVatTu = cursor.getString(0);
                String tenVatTu = cursor.getString(1);
                String loaiVatTu = cursor.getString(2);
                int soLuong = cursor.getInt(3);

                VatTu vatTu = new VatTu(idVatTu, tenVatTu,loaiVatTu , soLuong);
                vatTuList.add(vatTu);
            } while (cursor.moveToNext());
        }
        vatTuAdapter.notifyDataSetChanged();
    }
    private void timKiem(String idVatTu, String tenVatTu) {

        String query = "SELECT * FROM tblVatTu WHERE 1=1";
        if (!idVatTu.isEmpty()) {
            query += " AND idVatTu = '" + idVatTu + "'";
        }
        if (!tenVatTu.isEmpty()) {
            query += " AND tenVatTu LIKE '%" + tenVatTu + "%'";
        }
        Cursor cursor = dataBase.selectData(query);

        vatTuList.clear();
        if (cursor.moveToFirst()) {
            do {
                String idVatTuResult = cursor.getString(0);
                String tenVatTuResult = cursor.getString(1);
                String loaiVatTuResult = cursor.getString(2);
                int soLuongResult = cursor.getInt(3);

                VatTu vatTu = new VatTu(idVatTuResult, tenVatTuResult, loaiVatTuResult, soLuongResult);
                vatTuList.add(vatTu);
            } while (cursor.moveToNext());
        }
        vatTuAdapter.notifyDataSetChanged();
    }
    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa Vật Tư");
        builder.setMessage("Bạn có chắc chắn muốn xóa vật tư này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                xoaVatTu(position);
            }
        });
        builder.setNegativeButton("Không", null);
        builder.create().show();
    }

    private void xoaVatTu(int position) {
        Cursor cursor = dataBase.selectData("SELECT * FROM tblVatTu");
        if (cursor.moveToPosition(position)) {
            String idVatTu = cursor.getString(0);
            SQLiteDatabase db = dataBase.getWritableDatabase();
            int result = db.delete("tblVatTu", "idVatTu = ?", new String[]{idVatTu});
            if (result > 0) {
                Toast.makeText(QuanLyVatTu.this, "Đã xóa vật tư thành công", Toast.LENGTH_SHORT).show();
                loadData();
            } else {
                Toast.makeText(QuanLyVatTu.this, "Xóa vật tư thất bại", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }
    private void suaVatTu(VatTu vatTu) {
        SharedPreferences sharedPreferences = getSharedPreferences("VatTuData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("idVatTu", vatTu.getIdVatTu());
        editor.putString("tenVatTu", vatTu.getTenVatTu());
        editor.putString("loaiVatTu", vatTu.getLoaiVatTu());
        editor.putInt("soLuong", vatTu.getSoLuong());
        editor.apply();

        Intent intent = new Intent(QuanLyVatTu.this, SuaVatTu.class);
        startActivity(intent);

    }
}