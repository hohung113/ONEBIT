package com.example.onebitmoblie.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onebitmoblie.Data.ViewModels.NotificationVM;
import com.example.onebitmoblie.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{
    private List<NotificationVM> notificationVMS;

    public NotificationAdapter(List<NotificationVM> notificationVMS) {
        this.notificationVMS = notificationVMS;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification,parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationVM notificationVM = notificationVMS.get(position);
        if(notificationVM == null) return;
        holder.imgIcon.setImageResource(R.drawable.edit);
        holder.tvTitle.setText(notificationVM.getTitle());
        String des = notificationVM.getContent();
        if(des.length() >= 50) {
            des = des.substring(0,50)+"...";
        }
        holder.tvDescription.setText(des);
        if(notificationVM.isCreated()){
            holder.contrNOtification.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        } else{
            holder.contrNOtification.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.bgItemNoti));
        }

        if(notificationVM.getNotificationType().getValue() == 1){
            holder.imgIcon.setImageResource(R.drawable.ic_clock_logo);
        }

        // Lấy chuỗi createdAt
        String createdAt = notificationVM.getCreatedAt(); // Ví dụ: "2024-12-31 14:30:00"

        // Định dạng ngày giờ phù hợp với chuỗi
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            Date createdDate = sdf.parse(createdAt); // Chuyển từ String sang Date
            long diff = System.currentTimeMillis() - createdDate.getTime();

            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;

            if (days > 0) {
                holder.tvTime.setText(days + " day ago");
            } else if (hours > 0) {
                holder.tvTime.setText(hours + " hour ago");
            } else if (minutes > 0) {
                holder.tvTime.setText(minutes + " min ago");
            } else {
                holder.tvTime.setText("now");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            holder.tvTime.setText("not given");
        }
    }

    @Override
    public int getItemCount() {
        if(notificationVMS != null){
            return notificationVMS.size();
        }
        return 0;
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgIcon;
        private TextView tvTitle, tvTime, tvDescription;
        private ConstraintLayout contrNOtification;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            contrNOtification = itemView.findViewById(R.id.contrNOtification);
        }
    }
}
