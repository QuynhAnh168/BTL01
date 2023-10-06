package com.example.btl;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.btl.model.VatTu;

public class DataBase extends SQLiteOpenHelper {
    private static final String DBName = "BaiTapLon";
    private static final int VERSION = 1;
    public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DataBase(@Nullable Context context) {
        super(context, DBName, null , VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tableVatTu = "create table if not exists tblVatTu (idVatTu text primary key autoincrement ," +
                "tenVatTu text, loaiVatTu text, soLuong integer)";
        sqLiteDatabase.execSQL(tableVatTu);

        String tableKhachHang = "create table if not exists tblKhachHang (idKhachHang text primary key ," +
                "tenKhachHang text, soDienThoai text, ngaySinh date)";
        sqLiteDatabase.execSQL(tableKhachHang);

        String tableNhanVien = "create table if not exists tblNhanVien (idNhanVien text primary key autoincrement  ," +
                "tenNhanVien text, soDienThoai text, chucVu text, luong real)";
        sqLiteDatabase.execSQL(tableNhanVien);

        String tableDichVu = "create table if not exists tblDichVu (idDichVu text primary key   ," +
                "tenDichVu text, giaTien real, ngay date)";
        sqLiteDatabase.execSQL(tableDichVu);

        String tableLichHen = "create table if not exists tblLichHen (idLichHen text primary key   ," +
                "idKhachHang text, idNhanVien text, idDichVu text, ngayHen date, thoiGianHen time, trangThai text, ghiChu text, FOREIGN KEY (idKhachHang) REFERENCES tblKhachHang (idKhachHang),\n" +
                "                    FOREIGN KEY (idNhanVien) REFERENCES tblNhanVien (idNhanVien),\n" +
                "                    FOREIGN KEY (idDichVu) REFERENCES tblDichVu (idDichVu))";
        sqLiteDatabase.execSQL(tableLichHen);
    }
    public void excSql(String sql)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public Cursor selectData(String sql)
    {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql,null);
        return cursor;
    }

    public boolean vatTuExists(String idVatTu) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM tblVatTu WHERE  idVatTu = ?";
        Cursor cursor = db.rawQuery(query, new String[]{idVatTu});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public void addVatTu(VatTu vatTu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idVatTu", vatTu.getIdVatTu());
        values.put("tenVatTu", vatTu.getTenVatTu());
        values.put("loaiVatTu", vatTu.getLoaiVatTu());
        values.put("soLuong", vatTu.getSoLuong());

        db.insert("tblVatTu", null, values);
        db.close();
    }
    public boolean capNhatVatTu(String idVatTu, String tenVatTu, String loaiVatTu, int soLuong) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Thực hiện câu truy vấn cập nhật dữ liệu vào bảng VatTu
        // Ví dụ:
        ContentValues values = new ContentValues();
        values.put("tenVatTu", tenVatTu);
        values.put("loaiVatTu", loaiVatTu);
        values.put("soLuong", soLuong);

        int rowsAffected = db.update("tblVatTu", values, "idVatTu = ?", new String[] { idVatTu });
        db.close();

        return rowsAffected > 0;
    }
    public void deleteVatTu(String idVatTu) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tblVatTu", "idVatTu = ?", new String[]{String.valueOf(idVatTu)});
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void updateSoLuongVatTu(String idVatTu, int soLuong) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("soLuong", soLuong);

        db.update("tblVatTu", values, "idVatTu = ?", new String[]{String.valueOf(idVatTu)});
        db.close();
    }
}
