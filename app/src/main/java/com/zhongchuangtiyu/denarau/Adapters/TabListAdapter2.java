package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhongchuangtiyu.denarau.Entities.Tabs;
import com.zhongchuangtiyu.denarau.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：WangMeng on 2015/11/10 17:19
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class TabListAdapter2 extends BaseAdapter
{

    private List<Tabs.ItemsEntity> list;
    private Context context;

    public TabListAdapter2(List<Tabs.ItemsEntity> list, Context context)
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Tabs.ItemsEntity entity = list.get(position);
        ViewHolder viewHolder;
        View view;
        if (convertView == null)
        {

            view = LayoutInflater.from(context).inflate(R.layout.tabs_list_item2, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.itemsName.setText(entity.getName());
        viewHolder.itemsTotalPrice.setText(entity.getTotal_price());
        viewHolder.ItemsPaymentMethod.setText(entity.getPayment_method());
        return view;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'tabs_list_item2.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder
    {
        @Bind(R.id.itemsName)
        TextView itemsName;
        @Bind(R.id.itemsTotalPrice)
        TextView itemsTotalPrice;
        @Bind(R.id.ItemsPaymentMethod)
        TextView ItemsPaymentMethod;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
