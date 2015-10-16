package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongchuangtiyu.denarau.Entities.CardBagEntity;
import com.zhongchuangtiyu.denarau.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by WangMeng on 2015/10/13.
 */
public class CardBagListAdapter extends BaseAdapter
{
    private List<CardBagEntity> entities;
    private Context context;

    public CardBagListAdapter(List<CardBagEntity> entities, Context context)
    {
        this.entities = entities;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return entities.size();
    }

    @Override
    public Object getItem(int position)
    {
        return entities.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        CardBagEntity entity = entities.get(position);
        View view = null;
        ViewHolder viewHolder;
        if (convertView == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.cardbag_list_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        return view;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'cardbag_list_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder
    {
        @Bind(R.id.cardBagCourseImage)
        ImageView cardBagCourseImage;
        @Bind(R.id.cardBagCourseName)
        TextView cardBagCourseName;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
