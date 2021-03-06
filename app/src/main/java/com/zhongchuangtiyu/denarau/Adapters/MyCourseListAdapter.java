package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.zhongchuangtiyu.denarau.Entities.MyCourses;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.DateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：WangMeng on 2016/1/26 15:53
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class MyCourseListAdapter extends BaseAdapter
{
    private Context mContext;
    private List<MyCourses> list;

    public MyCourseListAdapter(Context mContext, List<MyCourses> list)
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
    public void addData(List<MyCourses> datas)
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
            view = LayoutInflater.from(mContext).inflate(R.layout.my_course_list_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mcMonth.setText(DateUtils.getDateToString5(Long.valueOf(list.get(position).getLesson().getStarted_at())));
        viewHolder.mcDay.setText(DateUtils.getDateToString6(Long.valueOf(list.get(position).getLesson().getStarted_at())));
        viewHolder.mcTime.setText(DateUtils.getDateToString2(Long.valueOf(list.get(position).getLesson().getStarted_at())));
        viewHolder.courseType.setText(list.get(position).getLesson().getName());
        viewHolder.coachName.setText(list.get(position).getLesson().getCourse().getCoach().getName());
        viewHolder.golfCourseName.setText(list.get(position).getClub_name());
        switch (list.get(position).getState())
        {
            case "progressing":
                viewHolder.btnRate.setText("等待上课");
                viewHolder.btnRate.setBackgroundResource(R.mipmap.wodekecheng_zt_bj);
                break;
            case "cancelled":
                viewHolder.btnRate.setText("已取消");
                viewHolder.btnRate.setBackgroundResource(R.mipmap.yy_yiyuyue);
                break;
            case "confirming":
                viewHolder.btnRate.setText("等待评分");
                viewHolder.btnRate.setBackgroundResource(R.mipmap.wodekecheng_zt_bj);
                break;
            case "finished":
                viewHolder.btnRate.setText("已完成");
                viewHolder.btnRate.setBackgroundResource(R.mipmap.yy_yiyuyue);
                break;
            default:
                break;
        }
        return view;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'my_course_list_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder
    {
        @Bind(R.id.mcMonth)
        TextView mcMonth;
        @Bind(R.id.mcDay)
        TextView mcDay;
        @Bind(R.id.mcTime)
        TextView mcTime;
        @Bind(R.id.courseType)
        TextView courseType;
        @Bind(R.id.golfCourseName)
        TextView golfCourseName;
        @Bind(R.id.coachName)
        TextView coachName;
        @Bind(R.id.btnRate)
        TextView btnRate;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
