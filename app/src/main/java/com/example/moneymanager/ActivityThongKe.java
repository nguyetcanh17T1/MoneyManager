package com.example.moneymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ActivityThongKe extends Activity {
 private TextView tvthu,tvchi,tvdu;
 private Button back;
public static DatabaseHandler db;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_thong_ke);
  back=findViewById(R.id.btnbackk);
  tvthu=findViewById(R.id.thu);
  tvchi=findViewById(R.id.chi);
  tvdu=findViewById(R.id.du);
  db=new DatabaseHandler(this);
  String stt="0",stc="0",sd;
  if(db.taikhoanthu().get(0)!=null) stt=db.taikhoanthu().get(0);
  tvthu.setText(stt);
  if(db.taikhoanchi().get(0)!=null) stc =db.taikhoanchi().get(0);
  tvchi.setText(stc);
  long sotienthu,sotienchi,sodu;
  sotienthu=Long.parseLong(stt);
  sotienchi=Long.parseLong(stc);
  sodu=sotienthu-sotienchi;
  sd=""+sodu;
  tvdu.setText(sd);
  back.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View view) {
    Intent intent=new Intent(ActivityThongKe.this,MainActivity.class);
    startActivity(intent);
   }
  });
 }
}