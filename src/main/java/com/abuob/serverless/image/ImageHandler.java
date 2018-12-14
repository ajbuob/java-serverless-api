package com.abuob.serverless.image;

import com.abuob.serverless.ApiGatewayResponse;
import com.abuob.serverless.Handler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.util.IOUtils;
import com.google.common.collect.ImmutableMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

public class ImageHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Logger LOG = LogManager.getLogger(Handler.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        LOG.info("received: {}", input);


        InputStream is = ImageHandler.class.getClassLoader().getResourceAsStream("images/tux.png");
        byte[] imageBytes = new byte[0];
        try {
            imageBytes = IOUtils.toByteArray(is);
        } catch (IOException e) {
            LOG.error("Unable to covert InputStream to byte[]");
            e.printStackTrace();
        }

        LOG.info("imageBytes:" + imageBytes + " length:" + imageBytes.length);

        //String imageStr = new String(imageBytes, StandardCharsets.UTF_8);
       // LOG.info("imageStr:" + imageStr);

        ImmutableMap<String,String> headersMap = ImmutableMap.<String, String>builder()
                .put("X-Powered-By", "AWS Lambda & serverless")
                .put("Content-Type", "image/png")
                .build();

        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setBinaryBody(imageBytes)
                .setHeaders(headersMap)
                .build();
    }
}
