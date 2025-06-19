package com.richcorgi.iap.guide.sample.callback;


import com.apple.itunes.storekit.model.JWSTransactionDecodedPayload;
import com.apple.itunes.storekit.model.ResponseBodyV2DecodedPayload;
import com.apple.itunes.storekit.verification.SignedDataVerifier;
import com.fasterxml.jackson.databind.DeserializationFeature;

import com.richcorgi.iap.guide.sample.request.IosNotifyRequest;
import io.swagger.v3.oas.annotations.Hidden;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;



@RestController
@Slf4j
@Tag(name = "iap回调处理", description = "iap回调处理 API")
@RequestMapping("/iap")
public class IapNotifyAdapter {


    @Resource
    private SignedDataVerifier signedDataVerifier;



    @Hidden
    @RequestMapping(
            value = "/payNotify",
            method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String iapPayNotify(@RequestBody IosNotifyRequest request)  {
        String signedPayload = request.getSignedPayload();
        ResponseBodyV2DecodedPayload notificationResponseBody;
        JWSTransactionDecodedPayload transaction;
        try {
             notificationResponseBody = signedDataVerifier.verifyAndDecodeNotification(signedPayload);
            transaction = signedDataVerifier.verifyAndDecodeTransaction(notificationResponseBody
                    .getData().getSignedTransactionInfo());
        }catch (Exception e){
            log.error("iap支付回调解析异常，signedPayload:{}，e:{}",signedPayload,e.getMessage());
            throw new IllegalArgumentException("非法的交易");
        }
        switch (notificationResponseBody.getNotificationType()) {
            case ONE_TIME_CHARGE: {
                log.info("iap支付成功事件");
                //paymentService.iapPay( transaction);
                break;
            }
            case REFUND: {
                log.info("iap退款事件");
               // paymentService.iapPayRefund(transaction);
                break;
            }
            case CONSUMPTION_REQUEST:{
                log.info("iap退款用户信息请求事件");
               // paymentService.consumptionRequest(transaction);
                break;
            }
            default:
            log.info("暂不处理的通知类型type:{}",notificationResponseBody.getNotificationType());
        }

        return "SUCCESS";
    }


}
