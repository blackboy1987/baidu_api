package com.bootx.service.impl;

import com.bootx.pojo.ModelListPojo;
import com.bootx.service.ModelService;
import com.bootx.util.JsonUtils;
import com.bootx.util.WebUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ModelServiceImpl implements ModelService {


    @Override
    public ModelListPojo list() {
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization","Bearer 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");
        String s = WebUtils.get("https://api.openai.com/v1/models", headers, null);
        return JsonUtils.toObject(s, new TypeReference<ModelListPojo>() {});
    }

    @Override
    public Object detail(String modelId) {
        return null;
    }
}
