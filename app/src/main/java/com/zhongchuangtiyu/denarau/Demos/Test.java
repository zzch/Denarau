package com.zhongchuangtiyu.denarau.Demos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongchuangtiyu.denarau.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wangm on 2015/11/2.
 */
public class Test extends Activity
{
    @Bind(R.id.view7)
    CircleImageView view7;
    @Bind(R.id.textView9)
    TextView textView9;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.textView22)
    TextView textView22;
    @Bind(R.id.imageView3)
    ImageView imageView3;
    @Bind(R.id.button3)
    Button button3;
    private int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);
        ButterKnife.bind(this);
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MyCustomDialog dialog = new MyCustomDialog(Test.this, "预约时间", new MyCustomDialog.OnCustomDialogListener()
                {
                    @Override
                    public void back(String name)
                    {
                        textView22.setText(name);
                    }
                });
                dialog.show();
            }
        });
        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                i++;
                setImage();
            }
        });
    }

    private void setImage()
    {
        switch (i)
        {
            case 1:
                imageView3.setImageResource(R.mipmap.oneicon);
                break;
            case 2:
                imageView3.setImageResource(R.mipmap.twoicon);
                break;
            case 3:
                imageView3.setImageResource(R.mipmap.threeicon);
                break;
            case 4:
                imageView3.setImageResource(R.mipmap.fouricon);
                break;
            case 5:
                imageView3.setImageResource(R.mipmap.fiveicon);
                break;
            case 6:
                imageView3.setImageResource(R.mipmap.sixicon);
                break;
            case 7:
                imageView3.setImageResource(R.mipmap.sevenicon);
                break;
            case 8:
                imageView3.setImageResource(R.mipmap.eighticon);
                break;
            default:
                break;
        }
    }

    private void customDialog()
    {

    }

}
