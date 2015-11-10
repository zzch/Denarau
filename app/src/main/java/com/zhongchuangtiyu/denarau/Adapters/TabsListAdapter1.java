package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zhongchuangtiyu.denarau.Entities.Tabs;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.SetListViewHeight;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：WangMeng on 2015/11/10 15:05
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class TabsListAdapter1 extends BaseAdapter
{
    private List<Tabs> list;
    private Context context;

    public TabsListAdapter1(List<Tabs> list, Context context)
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
        Tabs tabs = list.get(position);
        View view;
        ViewHolder viewholder;
        if (convertView == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.tabs_list_item1, null);
            viewholder = new ViewHolder(view);
            view.setTag(viewholder);
        }else
        {
            view = convertView;
            viewholder = (ViewHolder) view.getTag();
        }
        TabListAdapter2 adapter2 = new TabListAdapter2(tabs.getItems(), context);
        viewholder.tabListView2.setAdapter(adapter2);
        SetListViewHeight.setListViewHeightBasedOnChildren(viewholder.tabListView2);
        viewholder.sequence.setText(tabs.getSequence());
        return view;

    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'tabs_list_item1.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder
    {
        @Bind(R.id.sequence)
        TextView sequence;
        @Bind(R.id.reception_payment)
        TextView receptionPayment;
        @Bind(R.id.entranceDate)
        TextView entranceDate;
        @Bind(R.id.entrance_time)
        TextView entranceTime;
        @Bind(R.id.departure_time)
        TextView departureTime;
        @Bind(R.id.consumeCourse)
        TextView consumeCourse;
        @Bind(R.id.tabListView2)
        ListView tabListView2;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
