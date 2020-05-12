package com.zhyen.base.design_mode.abstract_factory.factory;

import com.zhyen.base.design_mode.abstract_factory.product_air.IAirConditioner;
import com.zhyen.base.design_mode.abstract_factory.product_tv.ITelevision;

/**
 * 抽象工厂类
 */
public interface AbstractFactory {
    /**
     * 创建空调
     *
     * @return
     */
    IAirConditioner createAirConditioner();

    /**
     * 创建电视
     *
     * @return
     */
    ITelevision createTelevision();
}
