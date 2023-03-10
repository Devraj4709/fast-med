package com.example.dev.devraj_22;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Doctordetails extends AppCompatActivity {


        private String[][] doctor_details1=
                {
                        {"Doctor Name: Ajit Saste", "Hospital Address: Pimpri", "Exp: Syrs", "Mobile No:9898989898", "680"},
                        {"Doctor Name: Prasad Pawar", "Hospital Address: Nigdi", "Exp: 15yrs", "Mobile No:7898989898", "900"},
                        {"Doctor Name: Swapnil Kale", "Hospital Address: Pune", "Exp: 8yrs", "Mobile No:8898989898", "300"},
                        {"Doctor Name: Deepak Deshmukh", "Hospital Address: Chinchwad", "Exp: 6yrs", "Mobile No:9898000000", "500"},
                        {"Doctor Name: Ashok Panda", "Hospital Address: Katraj", "Exp: 7yrs", "Mobile No:7798989898", "800"}
                };
        private String[][] doctor_details2 =
                {
                        {"Doctor Name: Neelam Patil", "Hospital Address: Pimpri", "Exp: Syrs", "Mobile No:9898989898", "680"},
                        {"Doctor Name: Swati Pawar", "Hospital Address: Nigdi", "Exp: 15yrs", "Mobile No:7898989898", "900"},
                        {"Doctor Name: Neeraja Kale", "Hospital Address: Pune", "Exp: 8yrs", "Mobile No:8898989898", "300"},
                        {"Doctor Name: Mayuri Deshmukh", "Hospital Address: Chinchwad", "Exp: 6yrs", "Mobile No:9898000000", "500"},
                        {"Doctor Name: Minakshi Panda", "Hospital Address: Katraj", "Exp: 7yrs", "Mobile No:7798989898", "800"}
                };
        private String[][] doctor_details3 =
                {
                        {"Doctor Name: Seena Patil", "Hospital Address: Pimpri", "Exp: 4yrs", "Mobile No:9898989898", "200"},
                        {"Doctor Name: Pnkaj Parab", "Hospital Address: Nigdi", "Exp: Syrs", "Mobile No:7898989898", "388"},
                        {"Doctor Name: Monish Jain", "Hospital Address: Pune", "Exp: 7yrs", "Mobile No:8898989898", "300"},
                        {"Doctor Name: Vishal Deshmukh", "Hospital Address: Chinchwad", "Exp: 6yrs", "Mobile No:9898888888", "500"},
                        {"Doctor Name: Shrikant Panda", "Hospital Address: Katraj", "Exp: 7yrs", "Mobile No:7798989898", "680"}
                };
        private String[][] doctor_details4 =
                {

                        {"Doctor Name: Nilesh Borate", "Hospital Address: Pimpri", "Exp: 5yrs", "Mobile No:9898989898", "1600"},
                        {"Doctor Name: Pankaj Pawar", "Hospital Address: Nigdi", "Exp: 15yrs", "Mobile No:7898989898", "1980"},
                        {"Doctor Name: Swapnil Lele", "Hospital Address: Pune", "Exp: 8yrs", "Mobile No:8898989898", "1300"},
                        {"Doctor Name: Deepak Kumar", "Hospital Address: Chinchwad", "Exp: 6yrs", "Mobile No:9898888888", "1500"},
                        {"Doctor Name: Ankul Panda", "Hospital Address: Katraj", "Exp: 7yrs", "Mobile No:7798989898", "1888"}
                };
        TextView tv;
        String[][] doctor_details ={};
        HashMap<String,String> item;
        ArrayList list;
        SimpleAdapter sa;
        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_doctordetails);

            tv =findViewById(R.id.textviewDDTitle);
            Intent intent=getIntent();
            String title =intent.getStringExtra("title");
            tv.setText(title);

            if (title.compareTo("Physician")==0)
                doctor_details =doctor_details1;
            else if (title.compareTo("Dentist")==0)
                doctor_details =doctor_details2;
            else if (title.compareTo("Cardeologist")==0)
                doctor_details =doctor_details3;
            else if (title.compareTo("Dietician")==0)
                doctor_details =doctor_details4;

            list =new ArrayList();
            for(int i=0;i<doctor_details.length;i++){
                item = new HashMap<String, String>();
                item.put("line1",doctor_details[i][0]);
                item.put("line2",doctor_details[i][1]);
                item.put("line3",doctor_details[i][2]);
                item.put("line4",doctor_details[i][3]);
                item.put("line5","Cons Fee "+doctor_details[i][4]+ "/-");
                list.add(item);
            }
            sa =new SimpleAdapter(this,list,
                    R.layout.row_item,
                    new String[]{"line1","line2","line3","line4","line5"},
                    new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e});
            ListView lst =findViewById(R.id.listview);
            lst.setAdapter(sa);

            lst.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent1 = new Intent(Doctordetails.this, BookAppointment.class);
                    intent1.putExtra("text1", title);
                    intent1.putExtra("text2", doctor_details[i][0]);
                    intent1.putExtra("text3", doctor_details[i][1]);
                    intent1.putExtra("text4", doctor_details[i][2]);
                    intent1.putExtra("text5", doctor_details[i][3]);
                    startActivity(intent1);
                }

            });

        }





    }
