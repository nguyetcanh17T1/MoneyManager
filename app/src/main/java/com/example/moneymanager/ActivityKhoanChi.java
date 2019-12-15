package com.example.moneymanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ActivityKhoanChi extends AppCompatActivity {
    private Button btnADD,btnBack,btnReset;
    private ListView lvKC;
    private EditText edtND,edtST;
    private List<KhoanThuChi> arrayKhoanChi;
    private CustomAdapter adapter;
    public static DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoan_chi2);
        db = new DatabaseHandler(this);
        btnBack=findViewById(R.id.btnback);
        btnADD=findViewById(R.id.btnADD);
        lvKC=findViewById(R.id.lvKC);
        edtND=findViewById(R.id.edtND);
        edtST=findViewById(R.id.edtST);
        arrayKhoanChi=new ArrayList<>();
        arrayKhoanChi=db.getAllKC();
        setAdapter();
        btnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KhoanThuChi khoanThuChi = create();
                if(khoanThuChi!=null){
                    db.themkhoanchi(khoanThuChi);
                    setAdapter();
                }
                arrayKhoanChi.clear();
                arrayKhoanChi.addAll(db.getAllKC());
                setAdapter();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityKhoanChi.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnReset=findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtND.setText("");
                edtST.setText("");
            }
        });
        lvKC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder adb = new AlertDialog.Builder(ActivityKhoanChi.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + (position+1)+" ?");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("OK", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.xoachi(positionToRemove+1);
                        arrayKhoanChi.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                adb.show();
            }
        });
    }

    public KhoanThuChi create(){
        String noidung = edtND.getText().toString();
        String sotien=edtST.getText().toString();
        KhoanThuChi khoanThuChi= new KhoanThuChi(noidung,sotien);
        return khoanThuChi;
    }

    public void setAdapter(){
        if(adapter==null){
            adapter=new CustomAdapter(this,R.layout.item_listview,arrayKhoanChi);
            lvKC.setAdapter(adapter);
        }
        else{
            adapter.notifyDataSetChanged();
             lvKC.setSelection(0);
        }
    }
}
