package com.example.btl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class XuatVatTuAdapter extends ArrayAdapter<XuatVatTuItem> {
    private LayoutInflater inflater;
    private OnQuantityChangeListener quantityChangeListener;

    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }

    public XuatVatTuAdapter(Context context, List<XuatVatTuItem> xuatVatTuList) {
        super(context, 0, xuatVatTuList);
        inflater = LayoutInflater.from(context);
    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener listener) {
        quantityChangeListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.xuat_vat_tu_item, parent, false);

            holder = new ViewHolder();
            holder.tvTenVatTu = convertView.findViewById(R.id.tvTenVatTu);
            holder.tvSoLuongVatTu = convertView.findViewById(R.id.tvSoLuongVatTu);
            holder.btnCong = convertView.findViewById(R.id.btnCong);
            holder.btnTru = convertView.findViewById(R.id.btnTru);
            holder.tvSoLuongXuat = convertView.findViewById(R.id.tvSoLuongXuat);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        XuatVatTuItem item = getItem(position);

        holder.tvTenVatTu.setText(item.getTenVatTu());
        holder.tvSoLuongVatTu.setText(String.valueOf(item.getSoLuongVatTu()));
        holder.tvSoLuongXuat.setText(String.valueOf(item.getSoLuongXuat()));

        holder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increase the quantity of the item
                XuatVatTuItem item = getItem(position);
                int newQuantity = item.getSoLuongXuat() + 1;
                item.setSoLuongXuat(newQuantity);

                // Update the quantity TextView
                holder.tvSoLuongXuat.setText(String.valueOf(newQuantity));

                // Notify the listener about the quantity change
                if (quantityChangeListener != null) {
                    quantityChangeListener.onQuantityChanged();
                }
            }
        });
        holder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increase the quantity of the item
                XuatVatTuItem item = getItem(position);
                int newQuantity = item.getSoLuongXuat() - 1;

                if (newQuantity >= 0) {
                    item.setSoLuongXuat(newQuantity);

                    // Update the quantity TextView
                    holder.tvSoLuongXuat.setText(String.valueOf(newQuantity));

                    // Notify the listener about the quantity change
                    if (quantityChangeListener != null) {
                        quantityChangeListener.onQuantityChanged();
                    }
                }
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView tvTenVatTu;
        TextView tvSoLuongVatTu;
        Button btnCong,btnTru;
        TextView tvSoLuongXuat;
    }

    public int tinhTongSoLuongXuat() {
        int tong = 0;
        for (int i = 0; i < getCount(); i++) {
            XuatVatTuItem item = getItem(i);
            tong += item.getSoLuongXuat();
        }
        return tong;
    }
}