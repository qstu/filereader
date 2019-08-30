package com.kenny.phone.files.utils;

import com.kenny.phone.R;
import com.kenny.phone.interfaces.Communications;

import java.io.File;

/**
 * Created by Kenny on 2015/8/6.
 */
public class DealFile implements Communications {
    public static int fileClassfy(File f) {
        int fileType = 0;
        if (f.isFile()) {
            if (f.getName().endsWith(".txt")) {
                fileType = TXT;
            } else if (f.getName().endsWith(".mp3")) {
                fileType = MP3;
            } else if (f.getName().endsWith(".ppt")) {
                fileType = PPT;
            } else if (f.getName().endsWith(".doc")) {
                fileType = DOC;
            } else if (f.getName().endsWith(".xls")) {
                fileType = XLS;
            } else if (f.getName().endsWith(".pdf")) {
                fileType = PDF;
            } else if (f.getName().endsWith(".jpg")) {
                fileType = JPG;
            } else if (f.getName().endsWith(".db")) {
                fileType = DB;
            } else {
                fileType = OTHER;
            }
        }
        if (f.isDirectory()) {
            fileType = FOLDER;
        }
        return fileType;
    }

    public static int fileTypeToResource(int fileType) {

        switch (fileType) {
            case FOLDER:
                return R.mipmap.folder;
            case OTHER:
                return R.mipmap.unknown;
            case TXT:
                return R.mipmap.txt;
            case DOC:
                return R.mipmap.doc;
            case PDF:
                return R.mipmap.pdf;
            case PPT:
                return R.mipmap.ppt;
            case XLS:
                return R.mipmap.xls;
            case MP3:
                return R.mipmap.mp3;
            case JPG:
                return R.mipmap.jpg;
            case DB:
                return R.mipmap.db;
            default:
                return R.mipmap.unknown;
        }
    }
}
