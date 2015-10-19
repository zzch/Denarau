package com.zhongchuangtiyu.denarau.Entities;

import java.util.List;

/**
 * Created by WangMeng on 2015/10/19.
 */
public class Welcome
{

    /**
     * sentences : ["下午好，王先生","欢迎光临北京中创体育高尔夫练习场"]
     */

    private List<String> sentences;

    public void setSentences(List<String> sentences)
    {
        this.sentences = sentences;
    }

    public List<String> getSentences()
    {
        return sentences;
    }
}
