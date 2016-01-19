package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhongchuangtiyu.denarau.Entities.OpenCourses;
import com.zhongchuangtiyu.denarau.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：WangMeng on 2016/1/19 17:59
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class OpenCourseAdapter extends BaseAdapter
{
    private Context mContext;
    private List<OpenCourses> list;

    public OpenCourseAdapter(Context mContext, List<OpenCourses> list)
    {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view;
        if (convertView == null)
        {
            view = LayoutInflater.from(mContext).inflate(R.layout.open_course_list_item, null);
        } else
        {
            view = convertView;
        }
        TextView openCourseDate = (TextView) view.findViewById(R.id.openCourseDate);
        TextView openCourseTime = (TextView) view.findViewById(R.id.openCourseTime);
        TextView openCourseCurrentAmount = (TextView) view.findViewById(R.id.openCourseCurrentAmount);
        TextView openCourseStatus = (TextView) view.findViewById(R.id.openCourseStatus);
        openCourseDate.setText(String.valueOf(list.get(position).getStarted_at()));
        openCourseTime.setText(String.valueOf(list.get(position).getFinished_at()));
        openCourseCurrentAmount.setText("(" + list.get(position).getMaximum_students() + "/" + list.get(position).getCurrent_students() + ")");
        openCourseStatus.setText(list.get(position).getState());
        return view;
    }
}
