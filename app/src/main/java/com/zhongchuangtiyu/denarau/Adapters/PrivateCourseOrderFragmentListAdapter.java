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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：WangMeng on 2016/2/1 15:44
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class PrivateCourseOrderFragmentListAdapter extends BaseAdapter
{
    private Context mContext;
    private List<PrivateCourses.RecentlyScheduleEntity.ScheduleEntity> list;

    public PrivateCourseOrderFragmentListAdapter(Context mContext, List<PrivateCourses.RecentlyScheduleEntity.ScheduleEntity> list)
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
        view = LayoutInflater.from(mContext).inflate(R.layout.dialog_fragment_list_item, null);
        viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        viewHolder.fragmentTv.setText(list.get(position).getTime());
        if (list.get(position).getState().equals("available")) {
            view.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            view.setAlpha(0.1f);
        }
        return view;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'dialog_fragment_list_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder
    {
        @Bind(R.id.fragmentTv)
        TextView fragmentTv;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
