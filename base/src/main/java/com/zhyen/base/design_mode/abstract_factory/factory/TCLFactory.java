package com.zhyen.base.design_mode.abstract_factory.factory;

import com.zhyen.base.design_mode.abstract_factory.product_air.IAirConditioner;
import com.zhyen.base.design_mode.abstract_factory.product_tv.ITelevision;
import com.zhyen.base.design_mode.abstract_factory.product_air.TCLAirConditioner;
import com.zhyen.base.design_mode.abstract_factory.product_tv.TCLTelevisionImpl;

public class TCLFactory implements AbstractFactory {
    @Override
    public IAirConditioner createAirConditioner() {
        return new TCLAirConditioner();
    }

    @Override
    public ITelevision createTelevision() {
        return new TCLTelevisionImpl();
    }
}
