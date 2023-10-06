package com.example.btl;
public class XuatVatTuItem {
    private String idVatTu;
    private String tenVatTu;
    private int soLuongXuat;
    private int soLuongVatTu;

    public XuatVatTuItem(String idVatTu, String tenVatTu, int soLuongXuat, int soLuongVatTu) {
        this.idVatTu = idVatTu;
        this.tenVatTu = tenVatTu;

        this.soLuongXuat = soLuongXuat;
        this.soLuongVatTu = soLuongVatTu;
    }

    public String getIdVatTu() {
        return idVatTu;
    }

    public String getTenVatTu() {
        return tenVatTu;
    }

    public int getSoLuongVatTu() {
        return soLuongVatTu;
    }

    public void setIdVatTu(String idVatTu) {
        this.idVatTu = idVatTu;
    }

    public void setTenVatTu(String tenVatTu) {
        this.tenVatTu = tenVatTu;
    }

    public void setSoLuongVatTu(int soLuongVatTu) {
        this.soLuongVatTu = soLuongVatTu;
    }

    public int getSoLuongXuat() {
        return soLuongXuat;
    }

    public void setSoLuongXuat(int soLuongXuat) {
        this.soLuongXuat = soLuongXuat;
    }
    public int getSoLuongConLai() {
        return soLuongVatTu - soLuongXuat;
    }
}