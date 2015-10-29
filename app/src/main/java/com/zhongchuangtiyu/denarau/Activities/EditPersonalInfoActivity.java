package com.zhongchuangtiyu.denarau.Activities;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.iwhys.library.widget.DatePickerDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhongchuangtiyu.denarau.Entities.UsersDetail;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.ClipImageActivity;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

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
    private final int START_ALBUM_REQUESTCODE = 1;
    private final int CAMERA_WITH_DATA = 2;
    private final int CROP_RESULT_CODE = 3;
    public static final String TMP_PATH = "clip_temp.jpg";
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
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(EditPersonalInfoActivity.this, "token", null);
        Xlog.d(token + "token----------------------------------------------");
        MyApplication.volleyGET(APIUrls.USERS_DETAIL + token, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                UsersDetail data = UsersDetail.instance(response);
                Xlog.d(data.toString() + "data.toString()-----------------------------------------------");
                if (data.getPortrait() != null) ;
                {
                    Bitmap photo = BitmapFactory.decodeFile(data.getPortrait());
                    personalInfoImageToEdit.setImageBitmap(photo);
                }
                if (data.getName() != null && !data.getName().equals(""))
                {
                    editPersonalInfoNameToRequest.setText(data.getName());
                }
                if (data.getGender() != null && !data.getGender().equals(""))
                {
                    editPersonalInfoGenderToRequest.setText(data.getGender());
                }
                if (data.getBirthday() != null && !data.getBirthday().equals(""))
                {
                    String setBirthDay = data.getBirthday().substring(0, 4) + "年" + data.getBirthday().substring(4, 6) + "月" + data.getBirthday().subSequence(6, 8) + "日";
                    editPersonalInfoBirthToSet.setText(setBirthDay);
                }
            }

            @Override
            public void netFail(VolleyError error)
            {

            }
        });
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
                        uploadBirthDay();
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
                startAlbum();
                break;
            case R.id.btnTakePicture:
                startCapture();
                break;
        }
    }

    private void uploadBirthDay()
    {
        String birthDay = editPersonalInfoBirthToSet.getText().toString();
        String birthDayToUpload = birthDay.substring(0,4) + birthDay.substring(5,7) + birthDay.substring(8, 10);
        Xlog.d(birthDayToUpload + "birthDayToUpload---------------------------------------");
        Map<String, String> map = new HashMap<>();
        String token = CacheUtils.getString(EditPersonalInfoActivity.this,"token",null);
        map.put("token",token);
        map.put("birthday",birthDayToUpload);
        MyApplication.volleyPUT(APIUrls.USERS_BIRTHDAY, map, new MyApplication.VolleyCallBack()
        {
            @Override
            public void netSuccess(String response)
            {
                Toast.makeText(EditPersonalInfoActivity.this, "生日上传成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void netFail(VolleyError error)
            {
                Toast.makeText(EditPersonalInfoActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // String result = null;
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case CROP_RESULT_CODE:
                String path = data.getStringExtra(ClipImageActivity.RESULT_PATH);
                Bitmap photo = BitmapFactory.decodeFile(path);
                personalInfoImageToEdit.setImageBitmap(photo);

                dialog.dismiss();
                break;
            case START_ALBUM_REQUESTCODE:
                startCropImageActivity(getFilePath(data.getData()));
                break;
            case CAMERA_WITH_DATA:
                // 照相机程序返回的,再次调用图片剪辑程序去修剪图片
                startCropImageActivity(Environment.getExternalStorageDirectory()
                        + "/" + TMP_PATH);
                break;
        }
    }

    // 裁剪图片的Activity
    private void startCropImageActivity(String path) {
        ClipImageActivity.startActivity(this, path, CROP_RESULT_CODE);
    }

    private void startAlbum() {
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
            intent.setType("image/*");
            startActivityForResult(intent, START_ALBUM_REQUESTCODE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            try {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, START_ALBUM_REQUESTCODE);
            } catch (Exception e2) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    private void startCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                Environment.getExternalStorageDirectory(), TMP_PATH)));
        startActivityForResult(intent, CAMERA_WITH_DATA);
    }

    /**
     * 通过uri获取文件路径
     *
     * @param mUri
     * @return
     */
    public String getFilePath(Uri mUri) {
        try {
            if (mUri.getScheme().equals("file")) {
                return mUri.getPath();
            } else {
                return getFilePathByUri(mUri);
            }
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    // 获取文件路径通过url
    private String getFilePathByUri(Uri mUri) throws FileNotFoundException {
        Cursor cursor = getContentResolver()
                .query(mUri, null, null, null, null);
        cursor.moveToFirst();
        return cursor.getString(1);
    }

}
