package com.eduonline.media.service;

import com.eduonline.media.model.po.MediaProcess;

import java.util.List;

/**
 * @author Anesthesia
 * @version 1.0
 * @description 任务处理
 * @date 2023/3/23 14:24
 */
public interface MediaFileProcessService {

    /**
     * @description 获取待处理任务
     * @param shardIndex    分片序号
     * @param shardTotal    分片总数
     * @param count         获取记录数
     * @return  java.util.List<com.eduonline.media.model.po.MediaProcess>
     * @author Anesthsia
     * @date 2023/3/23 14:26
     */
    public List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count);
}
