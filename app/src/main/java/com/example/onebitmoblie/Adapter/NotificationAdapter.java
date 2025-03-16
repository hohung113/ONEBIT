package com.example.onebitmoblie.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onebitmoblie.DAO.NotificationLineDAO;
import com.example.onebitmoblie.Data.ViewModels.NotificationVM;
import com.example.onebitmoblie.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{
    private List<NotificationVM> notificationVMS;
    private Context context;
    private NotificationLineDAO notificationLineDAO;

    public NotificationAdapter(List<NotificationVM> notificationVMS, Context context) {
        this.notificationVMS = notificationVMS;
        this.context = context;
        notificationLineDAO = new NotificationLineDAO();
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
        //isCreated alf isRead
        if(notificationVM.isCreated()){
            holder.contrNOtification.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        } else{
            holder.contrNOtification.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.bgItemNoti));
        }

        if(notificationVM.getNotificationType().getValue() == 1){
            holder.imgIcon.setImageResource(R.drawable.ic_clock_logo);
        }

        // Lấy chuỗi createdAt
        formatTime(notificationVM.getCreatedAt(), holder.tvTime);



        holder.itemView.setOnClickListener(view -> {
            showNotificationDialog(notificationVM);

            if(!notificationVM.isCreated()){
                notificationLineDAO.markAsRead(notificationVM.getNotificationLineId());
                notificationVM.setCreated(true);
                notifyItemChanged(position);
            }
        });
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
    private void showNotificationDialog(NotificationVM notification) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(notification.getTitle());
        builder.setMessage("Thời gian: " + notification.getCreatedAt() + "\n\n" +"Type: "+notification.getNotificationType() + "\n\n" + notification.getContent());
        builder.show();
    }

    public void formatTime(String createdAt, TextView textView) {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Định dạng thời gian từ UTC

        try {
            Date createdDate = isoFormat.parse(createdAt);
            long diff = System.currentTimeMillis() - createdDate.getTime();

            long minutes = diff / (60 * 1000);
            long hours = minutes / 60;
            long days = hours / 24;

            if (days > 0) {
                textView.setText(days + "d ago");
            } else if (hours > 0) {
                textView.setText(hours + "h ago");
            } else if (minutes > 0) {
                textView.setText(minutes + "m ago");
            } else {
                textView.setText("Now");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            textView.setText("N/A");
        }
    }
}
