package com.zhyen.base.design_mode.abstract_factory.factory;

import com.zhyen.base.design_mode.abstract_factory.product_air.HaierAirConditionerImpl;
import com.zhyen.base.design_mode.abstract_factory.product_tv.HaierTelevisionImpl;
import com.zhyen.base.design_mode.abstract_factory.product_air.IAirConditioner;
import com.zhyen.base.design_mode.abstract_factory.product_tv.ITelevision;

public class HaierFactory implements AbstractFactory {
    @Override
    public IAirConditioner createAirConditioner() {
        return new HaierAirConditionerImpl();
    }

    @Override
    public ITelevision createTelevision() {
        return new HaierTelevisionImpl();
    }
}
