package com.zhongchuangtiyu.denarau.Demos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iwhys.library.widget.BaseDialog;
import com.iwhys.library.widget.DatePickerDialog;
import com.iwhys.library.widget.TimePickerDialog;
import com.zhongchuangtiyu.denarau.R;

import org.w3c.dom.Text;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TimePickerTest extends Activity
{

    @Bind(R.id.button)
    Button button;
    @Bind(R.id.textView42)
    TextView textView42;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker_test);
        ButterKnife.bind(this);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                
            }
        });
    }

}
