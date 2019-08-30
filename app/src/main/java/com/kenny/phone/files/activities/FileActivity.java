package com.kenny.phone.files.activities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kenny.phone.files.adapters.FileAdapter;
import com.kenny.phone.files.entities.FileDirectory;
import com.kenny.phone.files.utils.DealFile;
import com.kenny.phone.R;
import com.kenny.phone.interfaces.Communications;

public class FileActivity extends Activity implements OnClickListener, Communications {
    private ListView lvFileDirectory;

    private FileAdapter adapter;
    private static String fileDir;
    private ArrayList<FileDirectory> fileDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        initialData();
        initialView();
    }

    private void initialData() {
        // 初始化原始数据
        fileDirectory = new ArrayList<>();
        // 添加数据
        addDataToArray();
        // 克隆
    }

    private void addDataToArray() {
//        File file = Environment.getExternalStorageDirectory().getParentFile();
        File file = Environment.getExternalStorageDirectory();
        File[] listFileItems = file.listFiles();

        int size = listFileItems.length;
        if(size <= 0){
            Toast.makeText(this, "没有文件",Toast.LENGTH_SHORT).show();
            return;
        }

        fileDir = file.getAbsolutePath();
        for (File file2 : listFileItems) {
            String fileName = file2.getName();
            int fileType = DealFile.fileClassfy(file2);
            FileDirectory files = new FileDirectory(fileType, fileName);
            fileDirectory.add(files);
        }
    }

    private void initialView() {

        Button btnPreStep = findViewById(R.id.btn_pre_step);
        btnPreStep.setOnClickListener(this);
        Button btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
        lvFileDirectory = findViewById(R.id.lv_file_directory);
        adapter = new FileAdapter(this, fileDirectory);
        lvFileDirectory.setAdapter(adapter);
        lvFileDirectory.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                final FileDirectory directory = fileDirectory.get(position);
                new AlertDialog.Builder(FileActivity.this)
                        .setTitle("Delete Entry")
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                fileDirectory.remove(directory);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();

                return true;
            }
        });
        lvFileDirectory.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int fileType = fileDirectory.get(position).getFileType();
                TextView tvFileTextItem = view
                        .findViewById(R.id.tv_file_text_item);

                if (fileType == FOLDER) {
                    fileDir = fileDir + File.separator
                            + tvFileTextItem.getText().toString().trim();
                    System.out.println("------------item: " + fileDir);
                    subDirectory();
                } else {
                    //发送数据
                    Toast.makeText(FileActivity.this, fileDir + "/" + fileDirectory.get(position).getFileName(), Toast.LENGTH_SHORT).show();
                    System.out.println("-------------"+fileDir + "/" + fileDirectory.get(position).getFileName());
                }
                adapter.refresh(fileDirectory);
                adapter.notifyDataSetChanged();
            }


            private void subDirectory() {
                fileDirectory.clear();
                File file = new File(fileDir);
                fileDir = file.getAbsolutePath();
                File[] listFileItems = file.listFiles();
                if (listFileItems == null) {
                    fileDirectory.clear();
                    return;
                }
                for (File file2 : listFileItems) {
                    String fileName = file2.getName();
                    int fileType = DealFile.fileClassfy(file2);
                    FileDirectory files = new FileDirectory(fileType,
                            fileName);
                    adapter.refresh(fileDirectory);
                    fileDirectory.add(files);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 此时目的是检查当前目录的上一级目录（由于我们只读/storage下的目录）
             *
             *   1.如果当前目录为/storage,则直接返回
             *   2.如果当前目录是/storage的下一级目录。。。
             *     1）. 此时查看其父目录
             *        +1.如果此时目录为/storage
             */
            case R.id.btn_pre_step:
                if (fileDir.compareTo(Environment.getExternalStorageDirectory().getParentFile().getAbsolutePath()) == 0) {
                    break;
                }

                File file = new File(fileDir).getParentFile();
                fileDir = file.getAbsolutePath();

                if (fileDir.compareTo(Environment.getExternalStorageDirectory().getParentFile().getAbsolutePath()) == 0) {
                    fileDirectory.clear();
                    addDataToArray();
                    adapter.refresh(fileDirectory);
                    adapter.notifyDataSetChanged();
                    break;
                }

                fileDirectory.clear();
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    for (File file2 : listFiles) {
                        String fileName = file2.getName();
                        int fileType = DealFile.fileClassfy(file2);
                        FileDirectory files = new FileDirectory(fileType, fileName);
                        fileDirectory.add(files);
                    }
                }
                adapter.refresh(fileDirectory);
                adapter.notifyDataSetChanged();

                break;
            case R.id.btn_add:
                new AlertDialog.Builder(FileActivity.this)
                        .setTitle("ADD")
                        .setMessage("Folder Or File")
                        .setPositiveButton("Folder",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Intent intent = new Intent(
                                                FileActivity.this,
                                                AddActivity.class);

                                        startActivityForResult(intent, 100);
                                    }
                                })

                        .setNegativeButton("File",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Intent intent = new Intent(
                                                FileActivity.this,
                                                AddActivity.class);

                                        startActivityForResult(intent, 101);
                                    }
                                }).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        File file;
        String fileName;
        FileDirectory fileTemp;
        switch (requestCode) {
            case 100:
                fileName = data.getStringExtra("fileName");
                file = new File(fileDir, fileName);

                if (!file.exists()) {
                    file.mkdir();
                }
                fileTemp = new FileDirectory(FOLDER, fileName);
                fileDirectory.add(fileTemp);
                adapter.notifyDataSetChanged();
                break;
            case 101:
                fileName = data.getStringExtra("fileName");
                file = new File(fileDir, fileName);
                int fileType = DealFile.fileClassfy(file);
                fileTemp = new FileDirectory(fileType, fileName);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                fileDirectory.add(fileTemp);
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
}
