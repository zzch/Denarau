package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhongchuangtiyu.denarau.Entities.CoachesDetail;
import com.zhongchuangtiyu.denarau.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：WangMeng on 2015/10/27 14:26
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class CoachDetailListAdapter extends BaseAdapter
{
    private List<CoachesDetail.CoursesEntity> list;
    private Context context;

    public CoachDetailListAdapter(List<CoachesDetail.CoursesEntity> list, Context context)
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
        CoachesDetail.CoursesEntity coachesDetail = list.get(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.coach_detail_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.coachDetailListItemTutorialType.setText(coachesDetail.getName());
        viewHolder.coachDetailListItemTutorialPrice.setText(coachesDetail.getPrice() + "元");
        return view;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'coach_detail_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder
    {
        @Bind(R.id.coachDetailListItemTutorialType)
        TextView coachDetailListItemTutorialType;
        @Bind(R.id.coachDetailListItemTutorialPrice)
        TextView coachDetailListItemTutorialPrice;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
