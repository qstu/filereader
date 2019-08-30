package com.kenny.phone.files.activities;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.kenny.phone.R;

public class AddActivity extends Activity {

	private EditText etName;
	private Button btnAdd;
	private String fileType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		initialData();
		initialView();
	}

	private void initialData() {
		Intent intent = getIntent();
		fileType = intent.getStringExtra("name");

	}

	String name;

	private void initialView() {
		etName = (EditText) findViewById(R.id.et_add_name);
		btnAdd = (Button) findViewById(R.id.btn_add);
		btnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				name = etName.getText().toString().trim();
				Intent intent = new Intent(AddActivity.this, FileActivity.class);
				intent.putExtra("fileName", name);
				setResult(RESULT_OK, intent);
				finish();
			}

		});
	}

}
