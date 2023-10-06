package com.example.btl.model;

import android.database.Cursor;

import com.example.btl.DataBase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VatTu implements Serializable {
    private static final String TABLE_VAT_TU = "tblVatTu";
    private static final String COLUMN_ID = "idVatTu";
    private static final String COLUMN_TEN_VAT_TU = "tenVatTu";
    private static final String COLUMN_LOAI_VAT_TU = "loaiVatTu";
    private static final String COLUMN_SO_LUONG = "soLuong";
    private String idVatTu;
    private String tenVatTu;
    private String loaiVatTu;
    private Integer soLuong;

    private DataBase myDatabase;

    public VatTu(String idVatTu, String tenVatTu, String loaiVatTu, Integer soLuong) {
        this.idVatTu = idVatTu;
        this.tenVatTu = tenVatTu;
        this.loaiVatTu = loaiVatTu;
        this.soLuong = soLuong;
    }

    public VatTu() {
    }

    public VatTu(DataBase myDatabase) {
        this.myDatabase = myDatabase;
    }

    public String getIdVatTu() {
        return idVatTu;
    }

    public String getTenVatTu() {
        return tenVatTu;
    }

    public String getLoaiVatTu() {
        return loaiVatTu;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setIdVatTu(String idVatTu) {
        this.idVatTu = idVatTu;
    }

    public void setTenVatTu(String tenVatTu) {
        this.tenVatTu = tenVatTu;
    }

    public void setLoaiVatTu(String loaiVatTu) {
        this.loaiVatTu = loaiVatTu;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
    @Override
    public String toString() {
        return "VatTu{" +
                "idVatTu='" + idVatTu + '\'' +
                ", tenVatTu='" + tenVatTu + '\'' +
                ", loaiVatTu='" + loaiVatTu + '\'' +
                ", soLuong='" + soLuong + '\''  +
                '}';
    }
    public List<VatTu> getAll() {
        List<VatTu> vatTuList = new ArrayList<>();
        Cursor cursor = myDatabase.selectData("SELECT * FROM tblVatTu");

        while (cursor.moveToNext())
        {
            String idVatTu = cursor.getString(0);
            String tenVatTu = cursor.getString(1);
            String loaiVatTu = cursor.getString(2);
            int soLuong = cursor.getInt(3);
            vatTuList.add(new VatTu(idVatTu,tenVatTu,loaiVatTu,soLuong));
        }
        return vatTuList;
    }
}
