package com.example.noticeapp.data;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.noticeapp.R;
import com.example.noticeapp.model.FileOP;
import com.example.noticeapp.model.GlideMode;
import com.example.noticeapp.model.Notice;
import com.example.noticeapp.ui.ViewNotices;

import java.io.File;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MyAdpater extends RecyclerView.Adapter<MyAdpater.ViewHolder> {

    private Context context;
    private List<Notice> uploads;
    private List<Notice> uploadsfull;

    public MyAdpater(Context context, List<Notice> uploads) {
        this.uploads = uploads;
        this.context = context;
        this.uploadsfull = uploads;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
//        RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(v);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Notice item = (Notice) uploads.get(position);
        final FileOP f= new FileOP(holder.root.getContext());
        holder.txtTitle.setText(item.getTitle());
        holder.txtDesc.setText(item.getDescrp());
        holder.txtDept.setText(item.getDept());
//        final Timestamp time = item.getTime();
//        final String time1 = new SimpleDateFormat("dd MMMM yyyy").format(time);
//        holder.txtTime.setText(time1);

        List<String> imageFormat = Arrays.asList(new String[]{"jpg", "JPG", "png", "PNG", "jpeg", "JPEG"});
        if(imageFormat.contains(item.getType()) && (!item.getType().equals(""))){
            Glide.with(context)
                    .load(item.getUpload())
//                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.imgPreview);
            holder.imgPreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "notices");
                    if (!mediaStorageDir.exists()) {
                        if (!mediaStorageDir.mkdirs()) {
                            Log.d("App", "failed to create directory");
                        }
                    }
                    String dir = mediaStorageDir.getAbsolutePath();
                    f.download(v.getContext(),item.getTitle(), "*", dir,item.getUpload());
                    Toast.makeText(v.getContext(), "Downloading...", Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Glide.with(context)
                    .load(R.drawable.logo)
//                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.imgPreview);;
        }

    }


    @Override
    public int getItemCount() {
        return this.uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout root;
        public TextView txtTitle;
        public TextView txtDesc,txtTime;
        public TextView txtDept;
        public ImageView imgPreview;
        public String text;

        public ViewHolder(View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.list_root);
            txtTitle = itemView.findViewById(R.id.item_noticetitle);
            txtDesc = itemView.findViewById(R.id.item_detail);
            txtDept = itemView.findViewById(R.id.item_dept);
            imgPreview = itemView.findViewById(R.id.item_image);
            txtTime = itemView.findViewById(R.id.item_time);

        }
    }
}
