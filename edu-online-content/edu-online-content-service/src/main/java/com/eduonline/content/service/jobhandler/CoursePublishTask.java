package com.eduonline.content.service.jobhandler;

import com.eduonline.messagesdk.model.po.MqMessage;
import com.eduonline.messagesdk.service.MessageProcessAbstract;
import com.eduonline.messagesdk.service.MqMessageService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Anesthesia
 * @version 1.0
 * @description 课程发布的人物类
 * @date 2023/3/30 9:15
 */
@Slf4j
@Component
public class CoursePublishTask extends MessageProcessAbstract {
    
    // 任务调度入口
    @XxlJob("CoursePublishJobHandler")
    public void coursePublishJobHandler() throws Exception{

        // 分片参数
        int shardIndex = XxlJobHelper.getShardIndex();//执行器的序号，从0开始
        int shardTotal = XxlJobHelper.getShardTotal();//执行器总数

        // 调用抽象类的方法执行任务
        process(shardIndex,shardTotal,"course_publish",30,60);
    }

    // 执行课程发布任务的逻辑,若有异常抛出则说明任务失败
    @Override
    public boolean execute(MqMessage mqMessage) {

        // 从mqMessage拿到课程id 当时写的是key1就拿key1，key2就拿key2
        Long courseId = Long.parseLong(mqMessage.getBusinessKey1());

        // 课程静态化上传到minio
        generateCourseHtml(mqMessage,courseId);
        
        // 向elasticsearch写索引数据
        saveCourseIndex(mqMessage,courseId);


        return true;
    }



    // 生成课程静态化页面并上传到文件系统Minio
    private void generateCourseHtml(MqMessage mqMessage,long courseId){

        // 消息id
        Long taskId = mqMessage.getId();
        MqMessageService mqMessageService = this.getMqMessageService();

        // 作任务幂等性处理
        // 查询数据库去除该阶段执行状态
        int stageOne = mqMessageService.getStageOne(taskId);
        if (stageOne > 0){
            log.debug("该课程静态化任务完成，无需处理…");
            return;
        }

        int i = 1/0;// 造个异常看看
        // 开始进行课程静态化

        // .. 任务处理完成写任务状态为完成
        mqMessageService.completedStageOne(taskId);
    }

    // 保存课程索引信息
    private void saveCourseIndex(MqMessage mqMessage, Long courseId) {
        // 任务id
        Long taskId = mqMessage.getId();
        MqMessageService mqMessageService = this.getMqMessageService();

        // 作任务幂等性处理
        // 查询数据库去除该阶段执行状态
        int stageTwo = mqMessageService.getStageTwo(taskId);
        if (stageTwo > 0) {
            log.debug("该课程索引信息已写入，无需处理…");
            return;
        }
        // 查询课程信息，调用搜索服务添加索引……

        // 完成本阶段的任务
        mqMessageService.completedStageTwo(taskId);
    }
}
