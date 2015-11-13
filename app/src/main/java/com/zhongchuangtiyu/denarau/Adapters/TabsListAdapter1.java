package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongchuangtiyu.denarau.Entities.Promotions;
import com.zhongchuangtiyu.denarau.Entities.Tabs;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.DateUtils;

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
    public void addData(List<Tabs> datas)
    {
        this.list.addAll(datas);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final Tabs tabs = list.get(position);

        View view;
        ViewHolder viewholder;
        if (convertView == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.tabs_list_item1, null);
            viewholder = new ViewHolder(view);
            view.setTag(viewholder);
        } else
        {
            view = convertView;
            viewholder = (ViewHolder) view.getTag();
        }
        viewholder.tabListView2.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (tabs.getItems() != null)
        {
            for (int i = 0; i < tabs.getItems().size(); i++)
            {
                params.setMargins(0, 15, 0, 15);
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setLayoutParams(params);
                TextView name = new TextView(context);
                TextView price = new TextView(context);
                TextView method = new TextView(context);
                name.setText(tabs.getItems().get(i).getName());
                name.setGravity(Gravity.CENTER_HORIZONTAL);
                name.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                price.setText(tabs.getItems().get(i).getTotal_price());
                price.setGravity(Gravity.CENTER_HORIZONTAL);
                price.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                switch (tabs.getItems().get(i).getPayment_method())
                {
                    case "by_ball_member":
                        method.setText("计球卡");
                        break;
                    case "by_time_member":
                        method.setText("计时卡");
                        break;
                    case "unlimited_member":
                        method.setText("畅打卡");
                        break;
                    case "stored_member":
                        method.setText("储值卡");
                        break;
                    case "credit_card":
                        method.setText("信用卡");
                        break;
                    case "cash":
                        method.setText("现金");
                        break;
                    case "check":
                        method.setText("支票");
                        break;
                    case "on_account":
                        method.setText("挂账");
                        break;
                    case "signing":
                        method.setText("签单");
                        break;
                    case "coupon":
                        method.setText("抵用卷");
                        break;
                    default:
                        break;
                }
                method.setGravity(Gravity.CENTER_HORIZONTAL);
                method.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                linearLayout.addView(name);
                linearLayout.addView(price);
                linearLayout.addView(method);
                viewholder.tabListView2.addView(linearLayout);
                Resources res = context.getResources();
                viewholder.tabListView2.setDividerDrawable(res.getDrawable(R.drawable.tabs_divider));
                viewholder.tabListView2.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE | LinearLayout.SHOW_DIVIDER_BEGINNING | LinearLayout.SHOW_DIVIDER_END);
            }
        }

//                TabListAdapter2 adapter2 = new TabListAdapter2(tabs.getItems(), context);
//                viewholder.tabListView2.setAdapter(adapter2);
//                SetListViewHeight.setListViewHeightBasedOnChildren(viewholder.tabListView2);
        viewholder.sequence.setText(tabs.getSequence());
        viewholder.receptionPayment.setText(tabs.getReception_payment());
        String date = DateUtils.getDateToString1(tabs.getDeparture_time());
        viewholder.entranceDate.setText(date);
        String entranceTime = DateUtils.getDateToString2(tabs.getEntrance_time());
        String departureTime = DateUtils.getDateToString2(tabs.getDeparture_time());
        viewholder.tabsTime.setText(entranceTime + "-" + departureTime);
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
        @Bind(R.id.tabs_time)
        TextView tabsTime;
        @Bind(R.id.tabListView2)
        LinearLayout tabListView2;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
