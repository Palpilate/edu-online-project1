package com.eduonline.messagesdk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eduonline.messagesdk.mapper.MqMessageHistoryMapper;
import com.eduonline.messagesdk.model.po.MqMessageHistory;
import com.eduonline.messagesdk.service.MqMessageHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anesthesia
 */
@Slf4j
@Service
public class MqMessageHistoryServiceImpl extends ServiceImpl<MqMessageHistoryMapper, MqMessageHistory> implements MqMessageHistoryService {

}
