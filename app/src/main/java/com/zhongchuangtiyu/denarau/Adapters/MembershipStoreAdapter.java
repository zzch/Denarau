package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongchuangtiyu.denarau.Entities.MembershipStoreEntity;
import com.zhongchuangtiyu.denarau.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangm on 2015/10/14.
 */
public class MembershipStoreAdapter extends BaseAdapter
{
    private List<MembershipStoreEntity> entities;
    private Context context;

    public MembershipStoreAdapter(List<MembershipStoreEntity> entities, Context context)
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
        MembershipStoreEntity entity = entities.get(position);
        View view;
        ViewHolder viewholder;
        if (convertView == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.memberstore_list_item, null);
            viewholder = new ViewHolder(view);
            view.setTag(viewholder);
        }else
        {
            view = convertView;
            viewholder = (ViewHolder) view.getTag();
        }
        return view;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'memberstore_list_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder
    {
        @Bind(R.id.memberStoreBrassieImage)
        ImageView memberStoreBrassieImage;
        @Bind(R.id.memberStoreBreassieName)
        TextView memberStoreBreassieName;
        @Bind(R.id.memberStoreBreassiePrice)
        TextView memberStoreBreassiePrice;
        @Bind(R.id.btnMemberStoreOrderBreassie)
        Button btnMemberStoreOrderBreassie;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
