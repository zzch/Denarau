package com.zhongchuangtiyu.denarau.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.zhongchuangtiyu.denarau.Entities.OpenCourses;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.APIUrls;
import com.zhongchuangtiyu.denarau.Utils.CacheUtils;
import com.zhongchuangtiyu.denarau.Utils.CustomToast;
import com.zhongchuangtiyu.denarau.Utils.DateUtils;
import com.zhongchuangtiyu.denarau.Utils.MyApplication;
import com.zhongchuangtiyu.denarau.Utils.Xlog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：WangMeng on 2016/1/19 17:59
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class OpenCourseAdapter extends BaseAdapter
{
    private Context mContext;
    private List<OpenCourses> list;
    private AlertDialog.Builder builder;
    public OpenCourseAdapter(Context mContext, List<OpenCourses> list)
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view;
        final OpenCourseViewHolder viewHolder;
        if (convertView == null)
        {
            view = LayoutInflater.from(mContext).inflate(R.layout.open_course_list_item, null);
            viewHolder = new OpenCourseViewHolder(view);
            view.setTag(viewHolder);
        } else
        {
            view = convertView;
            viewHolder = (OpenCourseViewHolder)view.getTag();
        }
        viewHolder.openCourseDate.setText(DateUtils.getDateToString(Long.valueOf(list.get(position).getStarted_at()) * 1000));
        viewHolder.openCourseTime.setText(DateUtils.getDateToString2(Long.valueOf(list.get(position).getStarted_at()) * 1000) + "-" + DateUtils.getDateToString2(Long.valueOf(list.get(position).getFinished_at()) * 1000));
        viewHolder.openCourseCurrentAmount.setText("(" + list.get(position).getCurrent_students() + "/" + list.get(position).getMaximum_students() + ")");
        viewHolder.openCourseStatus.setText(list.get(position).getState());
        if (list.get(position).getState().equals("full"))
        {
            Xlog.d("list.get(position).getState()list.get(position).getState()" + list.get(position).getState());
            viewHolder.openCourseStatus.setBackgroundResource(R.mipmap.yy_yiman);
            viewHolder.openCourseStatus.setText("已满");
            viewHolder.openCourseStatus.setClickable(false);
        }else if (list.get(position).getState().equals("available"))
        {
            viewHolder.openCourseStatus.setBackgroundResource(R.mipmap.yy_keyuyue);
            viewHolder.openCourseStatus.setText("可预约");
            viewHolder.openCourseStatus.setClickable(true);
        }else if (list.get(position).getState().equals("reserved"))
        {
            viewHolder.openCourseStatus.setBackgroundResource(R.mipmap.yy_yiyuyue);
            viewHolder.openCourseStatus.setText("已预约");
            viewHolder.openCourseStatus.setClickable(false);
        }
        viewHolder.openCourseStatus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Xlog.d("courseOrderUuidcourseOrderUuidcourseOrderUuidcourseOrderUuidcourseOrderUuid" + list.get(position).getUuid());
                sendOrderRequest();
            }

            private void sendOrderRequest()
            {
                String token = CacheUtils.getString(mContext, "token", null);
                String club_uuid = CacheUtils.getString(mContext, "clubuuid", null);
                String uuid = list.get(position).getUuid();
                Map<String, String> map = new HashMap<String, String>();
                map.put("token", token);
                map.put("club_uuid", club_uuid);
                map.put("uuid", uuid);
                MyApplication.volleyPOST(APIUrls.OPEN_COURSES_RESERVE, map, new MyApplication.VolleyCallBack()
                {
                    @Override
                    public void netSuccess(String response)
                    {

                        if (response.contains("20004"))
                        {
                            builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("重复预约");
                            builder.setMessage("您已经预约");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                            { //设置确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    dialog.dismiss(); //关闭dialog
                                    viewHolder.openCourseStatus.setBackgroundResource(R.mipmap.yy_yiyuyue);
                                    viewHolder.openCourseStatus.setText("已预约");
                                    viewHolder.openCourseStatus.setClickable(false);
                                }
                            });
                            builder.setCancelable(false);
                            builder.create();
                            builder.show();
                            Xlog.d(response + "response------------------------------------------");
                        }else if (response.contains("10004"))
                        {
                            builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("非本人预约课程");
                            builder.setMessage("请选择其他课程");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                            { //设置确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    dialog.dismiss(); //关闭dialog
                                    viewHolder.openCourseStatus.setBackgroundResource(R.mipmap.yy_yiyuyue);
                                    viewHolder.openCourseStatus.setText("非本人预约课程");
                                    viewHolder.openCourseStatus.setClickable(false);
                                }
                            });
                            builder.setCancelable(false);
                            builder.create();
                            builder.show();
                            Xlog.d(response + "response------------------------------------------");
                        }else if (response.contains("20006"))
                        {
                            builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("预约已满");
                            builder.setMessage("请选择其他课程");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                            { //设置确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    dialog.dismiss(); //关闭dialog
                                    viewHolder.openCourseStatus.setBackgroundResource(R.mipmap.yy_yiman);
                                    viewHolder.openCourseStatus.setText("已满");
                                    viewHolder.openCourseStatus.setClickable(false);
                                }
                            });
                            builder.setCancelable(false);
                            builder.create();
                            builder.show();
                            Xlog.d(response + "response------------------------------------------");
                        }else
                        builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("预约");
                        builder.setMessage("你已经成功预约");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                        { //设置确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss(); //关闭dialog
                                viewHolder.openCourseStatus.setBackgroundResource(R.mipmap.yy_yiyuyue);
                                viewHolder.openCourseStatus.setText("已预约");
                                viewHolder.openCourseStatus.setClickable(false);

                            }
                        });
                        builder.setCancelable(false);
                        builder.create();
                        builder.show();
                        Xlog.d(response + "response------------------------------------------");

                    }

                    @Override
                    public void netFail(VolleyError error)
                    {
                        CustomToast.showToast(mContext,"网络连接失效，请检查网络连接或稍后再试");
                    }
                });
            }
        });
        return view;
    }



    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'open_course_list_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class OpenCourseViewHolder
    {
        @Bind(R.id.openCourseDate)
        TextView openCourseDate;
        @Bind(R.id.openCourseTime)
        TextView openCourseTime;
        @Bind(R.id.openCourseCurrentAmount)
        TextView openCourseCurrentAmount;
        @Bind(R.id.openCourseStatus)
        TextView openCourseStatus;

        OpenCourseViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
