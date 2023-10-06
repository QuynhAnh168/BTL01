package com.example.btl;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.model.VatTu;

import java.util.ArrayList;
import java.util.List;

public class XuatVatTu extends AppCompatActivity {

    private ListView vatTuListView;
    private XuatVatTuAdapter xuatVatTuAdapter;
    private ArrayList<XuatVatTuItem> xuatVatTuList;
    //    private ArrayList<VatTu> vatTuList;
    private DataBase dataBase;
    Button btnXuatVatTu;
    TextView tvSoLuongXuat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuat_vat_tu);

        vatTuListView = findViewById(R.id.vatTuListView);
        btnXuatVatTu = findViewById(R.id.btnXuatVatTu);
        tvSoLuongXuat = findViewById(R.id.tvSoLuongXuat);

        dataBase = new DataBase(this);
        xuatVatTuList = new ArrayList<>();
        xuatVatTuAdapter = new XuatVatTuAdapter(this, xuatVatTuList);
        vatTuListView.setAdapter(xuatVatTuAdapter);
        loadData();
        calculateTotalXuat();
        XuatVatTuAdapter adapter = new XuatVatTuAdapter(this, xuatVatTuList);
        xuatVatTuAdapter.setOnQuantityChangeListener(new XuatVatTuAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged() {
                int tongSoLuongXuat = xuatVatTuAdapter.tinhTongSoLuongXuat();
                tvSoLuongXuat.setText(String.valueOf(tongSoLuongXuat));
            }
        });
        btnXuatVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa số lượng vật tư tương ứng trong SQLite
                for (int i = 0; i < xuatVatTuList.size(); i++) {
                    XuatVatTuItem item = xuatVatTuList.get(i);
                    int soLuongConLai = item.getSoLuongConLai();
                    if (soLuongConLai == 0) {
                        // Xóa vật tư nếu số lượng = 0
                        dataBase.deleteVatTu(item.getIdVatTu()); // Thay đổi "yourSQLiteDatabase" thành đối tượng của lớp SQLiteOpenHelper của bạn và "deleteVatTu" là phương thức xóa vật tư trong SQLite của bạn.
                        xuatVatTuList.remove(item);
                        i--; // Giảm chỉ số i để đảm bảo không bỏ qua phần tử sau khi xóa
                    } else {
                        // Cập nhật lại số lượng vật tư trong SQLite
                        dataBase.updateSoLuongVatTu(item.getIdVatTu(), soLuongConLai); // Thay đổi "yourSQLiteDatabase" thành đối tượng của lớp SQLiteOpenHelper của bạn và "updateSoLuongVatTu" là phương thức cập nhật số lượng vật tư trong SQLite của bạn.
                        item.setSoLuongVatTu(soLuongConLai);
                    }
                }

                // Cập nhật lại ListView
                xuatVatTuAdapter.notifyDataSetChanged();

                // Trở về màn hình trước đó
                onBackPressed();
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
        xuatVatTuList.clear();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String tenVatTu = cursor.getString(1);
                int soLuongVatTu = cursor.getInt(2);
                int soLuongXuat = cursor.getInt(3);

                XuatVatTuItem xuatVatTuItem = new XuatVatTuItem(id,tenVatTu , soLuongVatTu,soLuongXuat);
                xuatVatTuList.add(xuatVatTuItem);
            } while (cursor.moveToNext());
        }
        xuatVatTuAdapter.notifyDataSetChanged();
    }
    private void calculateTotalXuat() {
        int tongSoLuongXuat = 0;
        for (XuatVatTuItem item : xuatVatTuList) {
            tongSoLuongXuat += item.getSoLuongXuat();
        }
        tvSoLuongXuat.setText(String.valueOf(tongSoLuongXuat));
    }
    private void xoaVatTu(int position) {
        Cursor cursor = dataBase.selectData("SELECT * FROM tblVatTu");
        if (cursor.moveToPosition(position)) {
            String idVatTu = cursor.getString(0);
            SQLiteDatabase db = dataBase.getWritableDatabase();
            int result = db.delete("tblVatTu", "idVatTu = ?", new String[]{idVatTu});

            db.close();
        }
    }



}