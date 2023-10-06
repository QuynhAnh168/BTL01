package com.example.btl;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.btl.model.VatTu;

import java.util.ArrayList;

public class VatTuAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<VatTu> vatTuList;
    private OnDeleteClickListener onDeleteClickListener;
    private OnEditClickListener onEditClickListener;
    public VatTuAdapter(Context context, ArrayList<VatTu> vatTuList) {
        this.context = context;
        this.vatTuList = vatTuList;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }
    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }
    public void setOnEditClickListener(OnEditClickListener listener) {
        this.onEditClickListener = listener;
    }

    @Override
    public int getCount() {
        return vatTuList.size();
    }

    @Override
    public Object getItem(int position) {
        return vatTuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.vat_tu_item, null);
        }

        TextView txtTenVatTu = view.findViewById(R.id.txtTenVatTu);
        TextView txtSoLuong = view.findViewById(R.id.txtSoLuong);
        TextView txtIDVatTu = view.findViewById(R.id.txtIDVatTu);
        TextView txtLoaiVatTu = view.findViewById(R.id.txtLoaiVatTu);
        Button btnXoa = view.findViewById(R.id.btnXoa);
        Button btnSua = view.findViewById(R.id.btnSua);

        final VatTu vatTu = vatTuList.get(position);

        txtIDVatTu.setText(vatTu.getIdVatTu());
        txtTenVatTu.setText(vatTu.getTenVatTu());
        txtLoaiVatTu.setText(vatTu.getLoaiVatTu());
        txtSoLuong.setText(String.valueOf(vatTu.getSoLuong()));

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(position);
                }
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onEditClickListener != null) {
                    onEditClickListener.onEditClick(position);
                }
            }
        });
        return view;
    }
}