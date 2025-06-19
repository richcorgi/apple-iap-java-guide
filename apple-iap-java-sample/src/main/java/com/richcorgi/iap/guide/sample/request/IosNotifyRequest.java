package com.richcorgi.iap.guide.sample.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IosNotifyRequest {
    @Schema(description = "订单ID", type = "long")
    private String signedPayload;

}
