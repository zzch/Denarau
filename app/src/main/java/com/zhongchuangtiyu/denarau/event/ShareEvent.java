package com.zhongchuangtiyu.denarau.event;

import android.app.AlertDialog;

import com.youzan.sdk.model.goods.GoodsShareModel;
import com.youzan.sdk.web.bridge.IBridgeEnv;
import com.youzan.sdk.web.event.ShareDataEvent;

/**
 * ����    : ��ȡ��ǰҳ��ķ�����Ϣ
 *
 * ʹ�ó���: ����ǰ��Ʒҳ����������罻ƽ̨�����ع���
 * ��������: �ڵ�ǰҳ�������ɺ����{@code YouzanBridge.sharePage()}
 * ˵��    : �ص���ֻ���ṩ������Ϣ, ֮��ķ�������Ҫ���������м���
 * ����˵��:
 *
 * {@link com.youzan.sdk.model.goods.GoodsShareModel}
 *
 *      title:  ҳ�����
 *      link:   ��ǰҳ������
 *      desc:   ��Ʒ��ϸ������
 *      imgUrl: ��ƷͼƬ����
 *      imgWidth:   ͼƬ���
 *      imgHeight:  ͼƬ�߶�
 *
 */
public final class ShareEvent extends ShareDataEvent
{

    /**
     * �ش���������, �ٵ���������з���
     *
     * @param env һЩ�����Ļ���
     * @param data ��������
     */
    @Override
    public void call(IBridgeEnv env, GoodsShareModel data) {
        new AlertDialog
                .Builder(env.getActivity())
                .setTitle(data.getTitle())
                .setMessage("��������:\n" + data.getLink() + "\n\nͼƬ����:\n" + data.getImgUrl() + "\n\n����:\n" + data.getDesc())
                .create()
                .show();
    }
}
