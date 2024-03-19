package com.bootx.service;

import com.bootx.pojo.ModelListPojo;

/**
 * @author black
 */
public interface ModelService {

    ModelListPojo list();

    Object detail(String modelId);
}
