package com.android.pluginapk.result;

import com.android.pluginapk.entity.Task;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * @author wangduo
 * @description: 任务列表
 * @email: cswangduo@163.com
 * @date: 16/7/10
 */
public class TaskResult extends BaseResult {

    private ArrayList<Task> mList;


    @Override
    protected void parseDetail() throws JSONException {
    }


}
