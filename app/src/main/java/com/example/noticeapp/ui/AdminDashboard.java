package com.example.noticeapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noticeapp.R;
import com.example.noticeapp.model.Notice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdminDashboard extends AppCompatActivity {

    private static final int IMAGE_REQUEST_CODE = 123;
    private ImageButton backbutton;
    private EditText title;
    private Button postbutton;
    private EditText add_title;
    private EditText add_descp;
    private EditText add_dept;
    private EditText add_photo;
    private ImageButton notice_photo;
    private TextView filename;
    private Uri filePath;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        backbutton = findViewById(R.id.backbutton);
        postbutton = findViewById(R.id.postbutton);
        add_title = findViewById(R.id.add_title);
        add_descp = findViewById(R.id.add_descp);
        add_dept = findViewById(R.id.add_dept);
        notice_photo = findViewById(R.id.notice_photo);
        filename = findViewById(R.id.filename);

        notice_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

        postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postnotice();
            }
        });
    }

    private void postnotice() {
        final String title = add_title.getText().toString();
        final String Descrp = add_descp.getText().toString();
        final String Dept = add_dept.getText().toString();
        ProgressDialog progress;
        final String Upload = "";
        final String type = GetFileExtension(filePath);
//        final Date time = Calendar.getInstance().getTime();

        if(filePath != null) {
            progress = new ProgressDialog(this);
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setTitle("File Uploading");
            progress.setProgress(0);
            progress.show();
            mStorageRef = FirebaseStorage.getInstance().getReference();
            final StorageReference mfile= mStorageRef.child("NoticeUploads").child(add_title.getText().toString());
            mfile.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progress.dismiss();
                            mfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    Notice newNotice = new Notice( title, Descrp, url, type, Dept);
                                    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                                    db.child("Notices").child(add_title.getText().toString())
                                            .setValue(newNotice)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(AdminDashboard.this, "File upload success", Toast.LENGTH_SHORT).show();
                                                        add_title.setText(null);
                                                        add_descp.setText(null);
                                                        add_dept.setText(null);
                                                        notice_photo.setImageBitmap(null);
//                                                        notice_photo.setBackgroundResource(R.drawable.ic_menu_gallery);
                                                        filename.setText(null);
                                                    }
                                                }
                                            });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.v("Upload:  ",e+"");
                                    Toast.makeText(AdminDashboard.this, "Url :"+e, Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminDashboard.this, "File upload unsuccessful..", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            int currentProgress = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progress.setProgress(currentProgress);
                        }
                    });

        } else{
            progress = new ProgressDialog(this);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setTitle("File Uploading");

            progress.show();
            Notice newNotice = new Notice( title, Descrp,Upload,type);
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Notices");
            db.child(title).setValue(newNotice);
            Toast.makeText(AdminDashboard.this,"Notice Uploaded",Toast.LENGTH_SHORT).show();
            add_title.setText(null);
            add_descp.setText(null);
            add_dept.setText(null);
            progress.dismiss();
        }

    }

    private void upload() {
        Intent i = new Intent();
        i.setType("*/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Image"),IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data!=null && data.getData() != null){
            filePath = data.getData();
            Bitmap btmp = null;

            String type = GetFileExtension(filePath);
            String name = getFileName(filePath);

            Toast.makeText(getApplicationContext(), "   Selected File:   "+name,Toast.LENGTH_LONG).show();
            filename.setText(name);
            List<String> imageFormat = Arrays.asList(new String[]{"jpg", "JPG", "png", "PNG", "jpeg", "JPEG"});
            if(imageFormat.contains(type)) {
                try {
                    btmp = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                    Bitmap compressedBitmap = Bitmap.createScaledBitmap(btmp,notice_photo.getWidth(),notice_photo.getHeight(),true);
                    notice_photo.setImageBitmap(compressedBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                notice_photo.setBackgroundResource(R.drawable.ic_notes);
            }
        }
    }

    private String GetFileExtension(Uri filePath) {
        if(filePath == null){return "";}
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();

        // Return file Extension
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(filePath));
    }

    private String getFileName(Uri filePath) {
        String result = null;
        if (filePath.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(filePath, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = filePath.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;

    }
}