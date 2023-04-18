package com.eduonline.content.feignclient;

import com.eduonline.content.config.MultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Anesthesia
 * @version 1.0
 * @description 远程调用媒资服务的接口
 * @date 2023/3/30 8:26
 */
// 1、使用fallback定义降级的类 是无法拿到熔断的异常信息的
// 2、使用fallbackfactory定义降级的类 则不然
@FeignClient(value = "media-api", configuration = {MultipartSupportConfig.class}, fallbackFactory = MediaServiceClientFallbackFactory.class)
public interface MediaServiceClient {

    @RequestMapping(value = "/media/upload/coursefile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("filedata") MultipartFile filedata,
                                      @RequestParam(value = "objectName",required = false) String objectName) throws IOException;
}
