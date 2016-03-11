package com.zhongchuangtiyu.denarau.event;

import com.youzan.sdk.model.trade.WxPayModel;
import com.youzan.sdk.web.bridge.IBridgeEnv;
import com.youzan.sdk.web.event.WXAppPayEvent;


/**
 * ����   : ����΢��APP֧��������(�߽�ʵ��)
 *
 * ʹ�ó���: ���й��ںŲ���ͨ΢��֧��
 * ��������: ��ҳҳ�津��, �������޺�̨������΢��APP֧��
 * ˵��    : ΢��APP֧�����ɱȽ��鷳, �Ƽ�ʹ��΢��WAP֧��(Ĭ�Ͽ���)
 *           �ӿ�ֻ�᷵��΢��֧���Ķ�������, ֮�����ת֧����Ҫ���������м���
 *           �ο�΢��SDK�ĵ� http://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419319167&token=&lang=zh_CN
 * ����˵��:
 *
 * {@link com.youzan.sdk.model.trade.WxPayModel}��ͬ��{@link com.tencent.mm.sdk.modelpay.PayReq}
 *
 *
 */
public final class AppPayEvent extends WXAppPayEvent
{

    @Override
    public void call(IBridgeEnv env, WxPayModel item) {
        if (item != null) {
            //invoke wechat sdk pay for money
        }
    }

}
