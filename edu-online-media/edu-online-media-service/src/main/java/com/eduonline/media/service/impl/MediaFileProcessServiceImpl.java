package com.eduonline.media.service.impl;

import com.eduonline.media.mapper.MediaProcessMapper;
import com.eduonline.media.model.po.MediaProcess;
import com.eduonline.media.service.MediaFileProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anesthesia
 * @version 1.0
 * @description MediaFileProcess的接口实现
 * @date 2023/3/23 14:27
 */
@Slf4j
@Service
public class MediaFileProcessServiceImpl implements MediaFileProcessService {
    @Autowired
    MediaProcessMapper mediaProcessMapper;

    @Override
    public List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count) {
        List<MediaProcess> mediaProcesses = mediaProcessMapper.selectListByShardIndex(shardTotal, shardIndex, count);
        return mediaProcesses;
    }
}
