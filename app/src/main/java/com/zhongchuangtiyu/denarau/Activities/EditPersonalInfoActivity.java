package com.zhongchuangtiyu.denarau.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iwhys.library.widget.DatePickerDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhongchuangtiyu.denarau.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditPersonalInfoActivity extends AppCompatActivity implements View.OnClickListener
{

    @Bind(R.id.editPersonalInfoTitleLeft)
    ImageButton editPersonalInfoTitleLeft;
    @Bind(R.id.personalInfoImageToEdit)
    RoundedImageView personalInfoImageToEdit;
    @Bind(R.id.editPersonalInfoImageRl)
    RelativeLayout editPersonalInfoImageRl;
    @Bind(R.id.editPersonalInfoNameToRequest)
    TextView editPersonalInfoNameToRequest;
    @Bind(R.id.editPersonalInfoGenderToRequest)
    TextView editPersonalInfoGenderToRequest;
    @Bind(R.id.editPersonalInfoBirthToSet)
    TextView editPersonalInfoBirthToSet;
    @Bind(R.id.editPersonalInfoBirthRl)
    RelativeLayout editPersonalInfoBirthRl;
    private Button btnSelectFromGallery, btnTakePicture,btnCancel;
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private Dialog dialog;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setData();
        setListeners();

    }

    private void setData()
    {

    }
    private void setListeners()
    {
        editPersonalInfoImageRl.setOnClickListener(this);
        editPersonalInfoBirthRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.editPersonalInfoBirthRl:
                int[] dateValue = new int[]{1990, 1, 1};
                if (editPersonalInfoBirthRl.getTag() != null) {
                    dateValue = (int[]) editPersonalInfoBirthRl.getTag();
                }
                new DatePickerDialog(this, dateValue[0], dateValue[1], dateValue[2], new DatePickerDialog.OnSelectListener() {
                    @Override
                    public void onSelect(int[] values, String displayName) {
                        editPersonalInfoBirthToSet.setText(displayName);
                        editPersonalInfoBirthRl.setTag(values);
                    }
                }).show();
                break;
            case R.id.editPersonalInfoImageRl:
                View view = getLayoutInflater().inflate(R.layout.set_portrait_dialog_layout, null);
                btnSelectFromGallery = (Button) view.findViewById(R.id.btnSelectFromGallery);
                btnTakePicture = (Button) view.findViewById(R.id.btnTakePicture);
                btnCancel = (Button) view.findViewById(R.id.btnCancel);
                btnSelectFromGallery.setOnClickListener(this);
                btnTakePicture.setOnClickListener(this);
                dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
                dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                Window window = dialog.getWindow();
                // 设置显示动画
                window.setWindowAnimations(R.style.main_menu_animstyle);
                WindowManager.LayoutParams wl = window.getAttributes();
                wl.x = 0;
                wl.y = getWindowManager().getDefaultDisplay().getHeight();
                // 以下这两句是为了保证按钮可以水平满屏
                wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
                wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                // 设置显示位置
                dialog.onWindowAttributesChanged(wl);
                // 设置点击外围解散
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                btnCancel.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.btnSelectFromGallery:
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
                break;
            case R.id.btnTakePicture:
                if (isSdcardExisting()) {
                    Intent cameraIntent = new Intent(
                            "android.media.action.IMAGE_CAPTURE");
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
                    cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                } else {
                    Toast.makeText(v.getContext(), "请插入sd卡", Toast.LENGTH_LONG)
                            .show();
                }
                break;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    resizeImage(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (isSdcardExisting()) {
                        resizeImage(getImageUri());
                    } else {
                        Toast.makeText(EditPersonalInfoActivity.this, "未找到存储卡，无法存储照片！",
                                Toast.LENGTH_LONG).show();
                    }
                    break;

                case RESIZE_REQUEST_CODE:
                    if (data != null) {
                        showResizeImage(data);
                    }
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private boolean isSdcardExisting() {
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    public void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    private void showResizeImage(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            personalInfoImageToEdit.setImageDrawable(drawable);
            dialog.dismiss();
        }
    }

    private Uri getImageUri() {
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME));
    }
}
