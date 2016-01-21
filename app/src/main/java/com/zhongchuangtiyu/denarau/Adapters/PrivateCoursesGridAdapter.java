package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhongchuangtiyu.denarau.Entities.PrivateCourses;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.List;

/**
 * 作者：WangMeng on 2016/1/21 12:07
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class PrivateCoursesGridAdapter extends BaseAdapter
{
    private Context mContext;
    private List<PrivateCourses.RecentlyScheduleEntity.ScheduleEntity> list;

    public PrivateCoursesGridAdapter(Context mContext, List<PrivateCourses.RecentlyScheduleEntity.ScheduleEntity> list)
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.private_courses_grid_item,null);
        TextView time = (TextView) view.findViewById(R.id.privateCourseTimeAndState);
        if (list.get(position).getTime().equals("08:00") && list.get(position).getTime().equals("08:00") && list.get(position).getTime().equals("08:00") && list.get(position).getTime().equals("08:00") && list.get(position).getTime().equals("08:00"))
        {

        }
        time.setText(list.get(position).getTime());
        Xlog.d("list.get(position).getScheduleEntity().get(position).getState()" + "----------------" + list.get(position).getState());
        if (list.get(position).getState().equals("available"))
        {
            time.setBackgroundColor(Color.parseColor("#ff0000"));
        }else if (list.get(position).getState().equals("unavailable"))
        {
            time.setBackgroundColor(Color.parseColor("#FF00FF22"));
        }

        return view;
    }
}
