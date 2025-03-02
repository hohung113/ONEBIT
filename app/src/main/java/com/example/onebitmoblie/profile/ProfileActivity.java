package com.example.onebitmoblie.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.onebitmoblie.Helper.ConvertImageBase64;
import com.example.onebitmoblie.R;
import com.example.onebitmoblie.schedule.ScheduleActivity;
import com.example.onebitmoblie.homepage.HomeActivity;

public class ProfileActivity extends Activity {
    Button btnHome, btnTracking, btnNotification, btnProfile;
    ImageButton btnFAQ;
    Button btnEditName, btnEditDob, btnCurrentJob, btnEditGender, btnAddImage;
    Uri selectedImageUri; // Lưu ảnh đã chọn

    private ActivityResultLauncher<Intent> pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profiledetail);

        pickImageLauncher = new ActivityResultLauncher<Intent>() {
            @Override
            public void launch(Intent input, androidx.core.app.ActivityOptionsCompat options) {
                startActivityForResult(input, 100);
            }

            @Override
            public void unregister() {}

            @Override
            public ActivityResultContract<Intent, ?> getContract() {
                return new ActivityResultContracts.StartActivityForResult();
            }
        };


        btnHome = findViewById(R.id.btnHome);
        btnTracking = findViewById(R.id.btnTracking);
        btnNotification = findViewById(R.id.btnNotification);
        btnProfile = findViewById(R.id.btnProfile);
        btnFAQ = findViewById(R.id.btnFAQ);
        btnHome.setOnClickListener(view -> startActivity(new Intent(this, HomeActivity.class)));
        btnFAQ.setOnClickListener(view -> startActivity(new Intent(this, ScheduleActivity.class)));

        btnEditName = findViewById(R.id.btnEditName);
        btnEditDob = findViewById(R.id.btnEditDob);
        btnCurrentJob = findViewById(R.id.btnCurrentJob);
        btnEditGender = findViewById(R.id.btnEditGender);
        btnAddImage = findViewById(R.id.btnAddImage);

        btnEditName.setOnClickListener(view -> showEditDialog("Edit Name", "Enter your name"));
        btnEditDob.setOnClickListener(view -> showEditDialog("Edit Date of Birth", "Enter your date of birth"));
        btnCurrentJob.setOnClickListener(view -> showEditDialog("Edit Phone", "Enter your phone number"));
        btnEditGender.setOnClickListener(view -> showEditDialog("Edit Gender", "Enter your gender"));

        btnAddImage.setOnClickListener(view -> openGallery());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                String base64Image  = ConvertImageBase64.convertImageToBase64(this, selectedImageUri);
                if(base64Image != null){
                    Toast.makeText(this, "Selected Image: " + selectedImageUri.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void showEditDialog(String title, String hint) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit, null);
        builder.setView(dialogView);

        EditText editText = dialogView.findViewById(R.id.editText);
        editText.setHint(hint);

        builder.setTitle(title)
                .setPositiveButton("Save", (dialog, which) -> {
                    String input = editText.getText().toString();
                    if (!input.isEmpty()) {
                        Toast.makeText(this, title + ": " + input, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
