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

public class ActivityKhoanThu extends AppCompatActivity {
    private Button btnADD,btnBack,btnReset;
    private ListView lvKT;
    private EditText edtND,edtST;
    private List<KhoanThuChi> arrayKhoanThu;
    private CustomAdapter adapter;
    public static DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoan_thu2);
        db = new DatabaseHandler(getApplicationContext());
        btnBack=findViewById(R.id.btnBack);
        btnADD=findViewById(R.id.btnAdd);
        lvKT=findViewById(R.id.lvKT);
        edtND=findViewById(R.id.edtND);
        edtST=findViewById(R.id.edtST);
        arrayKhoanThu =new ArrayList<>();
        arrayKhoanThu =db.getAllKT();
        setAdapter();
        btnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KhoanThuChi khoanThuChi = create();
                if(khoanThuChi !=null){
                    db.themkhoanthu(khoanThuChi);
                    setAdapter();
                }
                arrayKhoanThu.clear();
                arrayKhoanThu.addAll(db.getAllKT());
                setAdapter();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityKhoanThu.this,MainActivity.class);
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
        lvKT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder adb = new AlertDialog.Builder(ActivityKhoanThu.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + (position+1)+" ?");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("OK", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.xoathu(positionToRemove+1);
                        arrayKhoanThu.remove(position);
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
        KhoanThuChi khoanThuChi = new KhoanThuChi(noidung,sotien);
        return khoanThuChi;
    }
    public void setAdapter(){
        if(adapter==null){
            adapter=new CustomAdapter(this,R.layout.item_listview, arrayKhoanThu);
            lvKT.setAdapter(adapter);
        }
        else{
            adapter.notifyDataSetChanged();
           lvKT.setSelection(0);
        }
    }
}