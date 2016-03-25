package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongchuangtiyu.denarau.Entities.Members;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.DateUtils;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：WangMeng on 2016/3/22 17:36
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class MembersListAdapter extends BaseAdapter
{
    private List<Members> list;
    private Context context;

    public MembersListAdapter(List<Members> list, Context context)
    {
        this.list = list;
        this.context = context;
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
    public void addData(List<Members> datas)
    {
        this.list.addAll(datas);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view;
        ViewHolder viewHolder;
        if (convertView == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.swipecard_list_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if (list.get(position).getAction().equals("charge"))
        {
            viewHolder.chongzhi.setVisibility(View.VISIBLE);
        }else if (list.get(position).getAction().equals("refund"))
        {
            viewHolder.tuikuan.setVisibility(View.VISIBLE);
        }else if (list.get(position).getAction().equals("consumption"))
        {

            Collection types = new HashSet();
            for (int i = 0; i < list.get(position).getItems().size(); i++)
            {
                String type = list.get(position).getItems().get(i).getType();
                types.add(type);

            }
            for (Object o :
                    types)
            {
                if (o.toString().contains("playing"))
                {
                    viewHolder.dawei.setVisibility(View.VISIBLE);
                }
                if (o.toString().contains("extra"))
                {
                    viewHolder.qita.setVisibility(View.VISIBLE);
                }
                if (o.toString().contains("provision"))
                {
                    viewHolder.canyin.setVisibility(View.VISIBLE);
                }
            }
        }
        viewHolder.swipeCardTime.setText(DateUtils.getDateToString9(Long.valueOf(list.get(position).getCreated_at())));
        viewHolder.swipeCardTime.setText(DateUtils.getDateToString10(Long.valueOf(list.get(position).getCreated_at())));
        if (list.get(position).getType().equals("income"))
        {
            viewHolder.swipeCardSum.setText("+" +
                    list.get(position).getAmount());
        }else if (list.get(position).getType().equals("expenditure"))
        {
            viewHolder.swipeCardSum.setText("-" +
                    list.get(position).getAmount());
        }
        return view;
    }

    static class ViewHolder
    {
        @Bind(R.id.swipeCardDate)
        TextView swipeCardDate;
        @Bind(R.id.swipeCardTime)
        TextView swipeCardTime;
        @Bind(R.id.canyin)
        ImageView canyin;
        @Bind(R.id.chongzhi)
        ImageView chongzhi;
        @Bind(R.id.dawei)
        ImageView dawei;
        @Bind(R.id.qita)
        ImageView qita;
        @Bind(R.id.tuikuan)
        ImageView tuikuan;
        @Bind(R.id.swipeCardSum)
        TextView swipeCardSum;


        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
