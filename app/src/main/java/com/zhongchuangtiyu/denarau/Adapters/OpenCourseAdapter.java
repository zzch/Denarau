package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
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
        ViewHolder viewHolder;
        if (convertView == null)
        {
            view = LayoutInflater.from(mContext).inflate(R.layout.open_course_list_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.openCourseCurrentAmount.setText(list.get(position).getCurrent_students());
        return view;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'open_course_list_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder
    {
        @Bind(R.id.openCourseDate)
        TextView openCourseDate;
        @Bind(R.id.openCourseTime)
        TextView openCourseTime;
        @Bind(R.id.openCourseCurrentAmount)
        TextView openCourseCurrentAmount;
        @Bind(R.id.openCourseStatus)
        TextView openCourseStatus;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
