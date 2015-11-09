package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhongchuangtiyu.denarau.Entities.Reservations;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 作者：WangMeng on 2015/11/5 15:50
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class ReservationsListAdapter extends BaseAdapter
{
    private Context context;
    private List<Reservations> list;

    public ReservationsListAdapter(Context context, List<Reservations> list)
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
    public void addData(List<Reservations> datas)
    {
        this.list.addAll(datas);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Reservations reservations = list.get(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.reservations_list_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.clubName.setText(reservations.getClub().getName());
        if (reservations.getState().equals("submitted"))
        {
            viewHolder.state.setText("待确认");
        }else if (reservations.getState().equals("confirmed"))
        {
            viewHolder.state.setText("已预约");
            viewHolder.state.setTextColor(Color.parseColor("#e54a4b"));
        }else if (reservations.getState().equals("finished"))
        {
            viewHolder.state.setText("已完成");
        }
//        viewHolder.state.setText(reservations.getState());
        String beginDate = String.valueOf(reservations.getWill_playing_at()*1000);
        Xlog.d(reservations.getWill_playing_at() * 1000 + "reservations.getWill_playing_at()-----------------------------");
        SimpleDateFormat sdf=new SimpleDateFormat("MM月dd号 HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        String sd = sdf.format(new Date(Long.parseLong(beginDate)));
        Xlog.d(sd+"sd---------------------------");
        viewHolder.willPlayingAt.setText(sd);
        return view;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'reservations_list_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder
    {
        @Bind(R.id.will_playing_at)
        TextView willPlayingAt;
        @Bind(R.id.clubName)
        TextView clubName;
        @Bind(R.id.state)
        TextView state;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
