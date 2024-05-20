package com.example.ql_sinh_vien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class MSinhVien {
    private int maSinhVien;
    private String hoTen;
    private Date ngaySinh;

    public MSinhVien(String line) throws ParseException {
        String[] parts = line.split("\t");
        maSinhVien = Integer.parseInt(parts[0]);
        hoTen = parts[1];
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        ngaySinh = format.parse(parts[2]);
    }

    public String getLine(){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        return Integer.toString(maSinhVien) + "\t" + hoTen
                + "\t" + format.format(ngaySinh);
    }
    public int getMaSinhVien(){
        return maSinhVien;
    }

    public String getHoTen(){
        return hoTen;
    }

    public Date getNgaySinh(){
        return ngaySinh;
    }
}