package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhongchuangtiyu.denarau.Entities.Announcements;
import com.zhongchuangtiyu.denarau.Entities.AnnouncementsDetail;
import com.zhongchuangtiyu.denarau.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangm on 2015/11/4.
 */
public class AnnouncementsListAdapter extends BaseAdapter
{
    private Context context;
    private List<Announcements> list;

    public AnnouncementsListAdapter(Context context, List<Announcements> list)
    {
        this.context = context;
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
        Announcements announcements = list.get(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.announcements_list_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        String beginDate = String.valueOf(announcements.getPublished_at() * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String sd = simpleDateFormat.format(new Date(Long.parseLong(beginDate)));
        viewHolder.announcementsItemDate.setText(sd);
        viewHolder.announcementsItemTitle.setText(announcements.getTitle());
        viewHolder.announcementsItemDetail.setText(announcements.getSummary());
        return view;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'announcements_list_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder
    {
        @Bind(R.id.announcementsItemTitle)
        TextView announcementsItemTitle;
        @Bind(R.id.announcementsItemDetail)
        TextView announcementsItemDetail;
        @Bind(R.id.announcementsItemDate)
        TextView announcementsItemDate;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
