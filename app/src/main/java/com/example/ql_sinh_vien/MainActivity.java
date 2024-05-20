package com.example.ql_sinh_vien;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MSinhVien> sinhViens = new ArrayList<>();
    private EditText edtMaSinhVien;
    private EditText edtHoTen;
    private EditText edtNgaySinh;
    private ListView lsvSinhVien;
    private void findViews(){
        edtMaSinhVien = findViewById(R.id.edtMaSinhVien);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtNgaySinh = findViewById(R.id.edtNgaySinh);
        lsvSinhVien = findViewById(R.id.lsvSinhVien);
    }
    private void napSinhViens() throws IOException, ParseException {
        InputStreamReader reader = new InputStreamReader(this.openFileInput("sinhViens.txt"));
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        int n = Integer.parseInt(line);
        for (int i = 0; i < n; ++i){
            line = bufferedReader.readLine();
            MSinhVien sinhVien = new MSinhVien(line);
            sinhViens.add(sinhVien);
        }
        bufferedReader.close();
        reader.close();
        Toast.makeText(this,Integer.toString(sinhViens.size()),Toast.LENGTH_LONG).show();
    }
    private void napLsvSinhVien(){
        Context context = this;
        //BUOC 1: TAO RA ADAPTER NAP DU LIEU VAO MAU SINH_VIEN_ITEM DA THIET KE
        ArrayAdapter<MSinhVien> adapter = new ArrayAdapter<MSinhVien>(this,R.layout.sinh_vien_item, sinhViens){
            public View getView(int position, View convertView, ViewGroup parent){
                if(convertView == null) {
                    MSinhVien sinhVien = sinhViens.get(position);
                    LayoutInflater layoutInflater = LayoutInflater.from(context);
                    convertView = layoutInflater.inflate(R.layout.sinh_vien_item, null);
                    TextView tviMaSinhVien = convertView.findViewById(R.id.tviMaSinhVien);
                    TextView tviHoTen = convertView.findViewById(R.id.tviHoTen);
                    TextView tviNgaySinh = convertView.findViewById(R.id.tviNgaySinh);
                    tviMaSinhVien.setText(Integer.toString(sinhVien.getMaSinhVien()));
                    tviHoTen.setText((sinhVien.getHoTen()));
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                    tviNgaySinh.setText(format.format(sinhVien.getNgaySinh()));
                }
                return convertView;
            }
        };
        //BUOC 2: GAN ADAPTER CHO LISTVIEW
        lsvSinhVien.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_sinh_vien);
        findViews();
        try {
            napSinhViens();
        } catch (IOException e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        } catch (ParseException e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
        napLsvSinhVien();
    }
    public void ThemSinhVien(View view) throws ParseException, IOException {
        try {
            String line = edtMaSinhVien.getText().toString() + "\t"
                    + edtHoTen.getText().toString().replace("\t", " ") + "\t"
                    + edtNgaySinh.getText().toString();
            MSinhVien sinhVien = new MSinhVien(line);
            sinhViens.add(sinhVien);
            luuSinhViens();
            napLsvSinhVien();
        }
        catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_LONG).show();
        }
    }
    private void luuSinhViens() throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(this.openFileOutput("sinhViens.txt", MODE_PRIVATE));
        int n = sinhViens.size();
        String data = Integer.toString(n) + "\n";
        for (int i = 0; i < n; i ++)
            data += sinhViens.get(i).getLine() + "\n";
        writer.write(data);
        writer.close();
    }
}