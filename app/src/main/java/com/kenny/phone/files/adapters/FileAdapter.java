package com.kenny.phone.files.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kenny.phone.files.entities.FileDirectory;
import com.kenny.phone.files.utils.DealFile;
import com.kenny.phone.R;

import java.util.ArrayList;

/**
 * Created by Kenny on 2015/8/5.
 */
public class FileAdapter extends BaseAdapter{

    private ArrayList<FileDirectory> fileDirectory;
    private Context context;

    public FileAdapter(Context context, ArrayList<FileDirectory> fileDirectory) {
        this.fileDirectory = fileDirectory;
        this.context = context;
    }

    public void refresh(ArrayList<FileDirectory> fileDirectory){
        if(fileDirectory == null){
            this.fileDirectory = null;
            return;
        }
        if(!fileDirectory.equals(this.fileDirectory)){
            this.fileDirectory = fileDirectory;
        }
    }

    @Override
    public int getCount() {
        if (fileDirectory == null)
            return 0;
        return fileDirectory.size();
    }

    @Override
    public Object getItem(int position) {
        if (fileDirectory == null)
            return null;
        return fileDirectory.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (fileDirectory == null)
            return 0;
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item,
                    null);
        }
        ImageView ivFilePicture = (ImageView) convertView
                .findViewById(R.id.iv_file_picture);
        TextView tvFileTextItem = (TextView) convertView
                .findViewById(R.id.tv_file_text_item);
        int pictureResource = DealFile.fileTypeToResource(fileDirectory.get(position).getFileType());
        ivFilePicture.setImageResource(pictureResource);
        tvFileTextItem.setText(fileDirectory.get(position)
                .getFileName());
        return convertView;
    }
    private static class ViewHolderFile{
        ImageView ivFilePicture;
        TextView tvFileName;
    }
    private static class ViewHolderFolder{
        ImageView ivFilePicture;
        TextView tvFileName;
    }
}
