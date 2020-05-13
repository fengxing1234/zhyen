package com.zhyen.base.design_mode;

import com.zhyen.base.design_mode.abstract_factory.factory.HaierFactory;
import com.zhyen.base.design_mode.abstract_factory.factory.TCLFactory;
import com.zhyen.base.design_mode.abstract_factory.product_air.IAirConditioner;
import com.zhyen.base.design_mode.abstract_factory.product_tv.ITelevision;
import com.zhyen.base.design_mode.builder.Computer;
import com.zhyen.base.design_mode.builder.Computer2;
import com.zhyen.base.design_mode.builder.Director;
import com.zhyen.base.design_mode.builder.MoviesBuilder;
import com.zhyen.base.design_mode.builder.PlayGameBuilder;
import com.zhyen.base.design_mode.chain_of_responsibility.UML.ConcreteHandlerA;
import com.zhyen.base.design_mode.chain_of_responsibility.UML.ConcreteHandlerB;
import com.zhyen.base.design_mode.chain_of_responsibility.demo.ClassAdviser;
import com.zhyen.base.design_mode.chain_of_responsibility.demo.Dean;
import com.zhyen.base.design_mode.chain_of_responsibility.demo.DepartmentHead;
import com.zhyen.base.design_mode.chain_of_responsibility.okhttp_demo.BridgeInterceptor;
import com.zhyen.base.design_mode.chain_of_responsibility.okhttp_demo.CacheInterceptor;
import com.zhyen.base.design_mode.chain_of_responsibility.okhttp_demo.CallServerInterceptor;
import com.zhyen.base.design_mode.chain_of_responsibility.okhttp_demo.ClientInterceptor;
import com.zhyen.base.design_mode.chain_of_responsibility.okhttp_demo.ConnectInterceptor;
import com.zhyen.base.design_mode.chain_of_responsibility.okhttp_demo.Interceptor;
import com.zhyen.base.design_mode.chain_of_responsibility.okhttp_demo.NetworkInterceptor;
import com.zhyen.base.design_mode.chain_of_responsibility.okhttp_demo.RealInterceptorChain;
import com.zhyen.base.design_mode.chain_of_responsibility.okhttp_demo.RetryInterceptor;
import com.zhyen.base.design_mode.chain_of_responsibility.okhttp_demo.TestRequest;
import com.zhyen.base.design_mode.chain_of_responsibility.okhttp_demo.TestResponse;
import com.zhyen.base.design_mode.command_mode.UML.ChangHenCommand;
import com.zhyen.base.design_mode.command_mode.UML.ConcreteCommandA;
import com.zhyen.base.design_mode.command_mode.UML.ConcreteCommandB;
import com.zhyen.base.design_mode.command_mode.UML.HeFenCommand;
import com.zhyen.base.design_mode.command_mode.UML.HunTunCommand;
import com.zhyen.base.design_mode.command_mode.UML.Invoke;
import com.zhyen.base.design_mode.command_mode.UML.Waiter;
import com.zhyen.base.design_mode.factory_method.DatabaseFactory;
import com.zhyen.base.design_mode.factory_method.FileLogFactory;
import com.zhyen.base.design_mode.factory_method.ILog;
import com.zhyen.base.design_mode.mediator_mode.AbstractMediator;
import com.zhyen.base.design_mode.mediator_mode.ConcreteColleagueA;
import com.zhyen.base.design_mode.mediator_mode.ConcreteColleagueB;
import com.zhyen.base.design_mode.mediator_mode.ConcreteMediator;
import com.zhyen.base.design_mode.observer.ConcreteSubject;
import com.zhyen.base.design_mode.observer.ObserverA;
import com.zhyen.base.design_mode.observer.ObserverB;
import com.zhyen.base.design_mode.prototype.ConcretePrototype;
import com.zhyen.base.design_mode.simple_factory.IPlasticProduct;
import com.zhyen.base.design_mode.simple_factory.PlasticFactory;
import com.zhyen.base.design_mode.strategy.UML.ConcreteStrategyA;
import com.zhyen.base.design_mode.strategy.UML.ConcreteStrategyB;
import com.zhyen.base.design_mode.strategy.UML.StrategyContext;
import com.zhyen.base.design_mode.template_method.ConcreteSort;
import com.zhyen.base.design_mode.visitor_mode.ConcreteElementA;
import com.zhyen.base.design_mode.visitor_mode.ConcreteElementB;
import com.zhyen.base.design_mode.visitor_mode.ConcreteVisitorA;
import com.zhyen.base.design_mode.visitor_mode.ConcreteVisitorB;
import com.zhyen.base.design_mode.visitor_mode.IVisitor;
import com.zhyen.base.design_mode.visitor_mode.ObjectStructure;

import java.util.ArrayList;
import java.util.List;

public class DesignMode {

    public static void main(String[] arg) {
        //工厂方法
        //factoryMethod();
        //简单工厂
        //simpleFactory();
        //抽象工厂
        //abstractFactory();
        //建造者
        //builder();
        //原型模式
        //prototype();
        //模版方法
        //templateMethod();
        //中介者模式
        //mediatorMode();
        //观察者模式
        //observerMode();
        //visitorMode();
        //命令模式
        //commandMode();
        //commandDemo();
        //责任链模式
        //chainOfResponsibility();
        //chainOfResponsibilityDemo();
        //okHttpInterceptor();
        strategyUML();
    }

    private static void strategyUML() {
        StrategyContext strategy = new StrategyContext();
        strategy.setStrategy(new ConcreteStrategyA());
        strategy.strategy();
        System.out.println("---------");
        strategy.setStrategy(new ConcreteStrategyB());
        strategy.strategy();
    }

    private static void okHttpInterceptor() {
        List<Interceptor> list = new ArrayList<>();
        list.add(new ClientInterceptor());
        list.add(new RetryInterceptor());
        list.add(new BridgeInterceptor());
        list.add(new CacheInterceptor());
        list.add(new CallServerInterceptor());
        list.add(new ConnectInterceptor());
        list.add(new NetworkInterceptor());
        TestRequest testRequest = new TestRequest();
        RealInterceptorChain interceptorChain = new RealInterceptorChain(list, 0, testRequest);
        TestResponse response = interceptorChain.proceed(testRequest);
        System.out.println("request = " + testRequest.des);
        System.out.println("response = " + response.des);

    }

    private static void chainOfResponsibilityDemo() {
        ClassAdviser classAdviser = new ClassAdviser();
        DepartmentHead departmentHead = new DepartmentHead();
        Dean dean = new Dean();
        classAdviser.setNext(departmentHead);
        departmentHead.setNext(dean);
        classAdviser.handlerRequest(11);

    }

    private static void chainOfResponsibility() {
        ConcreteHandlerA handlerA = new ConcreteHandlerA();
        ConcreteHandlerB handlerB = new ConcreteHandlerB();
        handlerA.setNext(handlerB);
        String request = handlerA.handlerRequest("request");
        System.out.println(request);
    }

    private static void commandDemo() {
        System.out.println("过来服务员");
        Waiter waiter = new Waiter();
        System.out.println("给我来份肠粉");
        waiter.setChangFenCommand(new ChangHenCommand());
        waiter.chooseChangFen();
        System.out.println("给我来份河粉");
        waiter.setHeFenCommand(new HeFenCommand());
        waiter.chooseHeFen();
        System.out.println("听说你家馄饨不错 来份尝尝");
        waiter.setHunTunCommand(new HunTunCommand());
        waiter.chooseHunTun();

    }

    private static void commandMode() {
        ConcreteCommandA commandA = new ConcreteCommandA();
        Invoke invoke = new Invoke(commandA);
        invoke.cell();
        System.out.println("--------------");
        ConcreteCommandB commandB = new ConcreteCommandB();
        invoke.setCommand(commandB);
        invoke.cell();
    }

    public static void visitorMode() {
        ObjectStructure structure = new ObjectStructure();
        structure.add(new ConcreteElementA());
        structure.add(new ConcreteElementB());
        IVisitor concreteVisitorA = new ConcreteVisitorA();
        structure.accept(concreteVisitorA);
        System.out.println("------------------------");
        ConcreteVisitorB visitorB = new ConcreteVisitorB();
        structure.accept(visitorB);
    }

    public static void observerMode() {
        ConcreteSubject subject = new ConcreteSubject();
        ObserverA observerA = new ObserverA();
        ObserverB observerB = new ObserverB();
        subject.attach(observerA);
        subject.attach(observerB);
        subject.doSomething();
        System.out.println("-------------");
        subject.detach(observerA);
        subject.detach(observerB);
        subject.doSomething();
    }

    public static void mediatorMode() {
        //创建具体中介者
        AbstractMediator concreteMediator = new ConcreteMediator();
        //创建具体同事类A
        ConcreteColleagueA colleagueA = new ConcreteColleagueA();
        //创建具体同事类A
        ConcreteColleagueB colleagueB = new ConcreteColleagueB();
        concreteMediator.register(colleagueA);
        concreteMediator.register(colleagueB);
        System.out.println("--------------------");
        colleagueA.send();
        System.out.println("--------------------");
        colleagueB.send();
    }

    private static void templateMethod() {
        int[] a = {10, 32, 1, 9, 5, 7, 12, 0, 4, 3}; // 预设数据数组
        ConcreteSort concreteSort = new ConcreteSort();
        concreteSort.printSort(a);
    }

    private static void prototype() {
        ConcretePrototype concretePrototype = new ConcretePrototype();
        System.out.println("concretePrototype = " + concretePrototype);
        concretePrototype.age = 18;
        concretePrototype.name = "fengxing";
        try {
            ConcretePrototype clone = concretePrototype.clone();
            System.out.println("ConcretePrototype  clone= " + clone);
            String cloneName = clone.name;
            System.out.println("String cloneName = " + cloneName);
        } catch (CloneNotSupportedException e) {
            System.out.println("克隆抱错");
            e.printStackTrace();
        }
    }

    private static void builder() {
        Director director = new Director();
        PlayGameBuilder builder = new PlayGameBuilder();
        director.setBuilder(builder);
        Computer playGameComputer = director.createComputer("游戏专属CPU", "游戏专属硬盘", "游戏专属主板", "游戏专属内存", "游戏专属GPU");
        String show = playGameComputer.show();
        System.out.println(show);

        MoviesBuilder moviesBuilder = new MoviesBuilder();
        director.setBuilder(moviesBuilder);
        Computer moviesComputer = director.createComputer("看电影专属CPU", "看电影专属硬盘", "看电影专属主板", "看电影专属内存", "看电影专属GPU");
        System.out.println(moviesComputer.show());

        System.out.println(playGameComputer + " ::: " + moviesComputer);

        Computer2 build = new Computer2.ComputerBuilder()
                .setCpu("写博客的CPU")
                .setMainBoard("不太需要主板")
                .setComputerCase("也不需要机箱")
                .setMousePad("有个鼠标垫就可以写博客了")
                .build();
        String show1 = build.show();
        System.out.println(show1);
    }

    private static void abstractFactory() {
        //海尔工厂类
        HaierFactory haierFactory = new HaierFactory();
        //通过海尔工厂类创建空调产品类
        IAirConditioner airConditioner = haierFactory.createAirConditioner();
        //调用海尔空调
        airConditioner.airConditioner();
        //通过海尔工厂类创建电视产品类
        ITelevision television = haierFactory.createTelevision();
        //调用海尔电视
        television.television();

        TCLFactory tclFactory = new TCLFactory();
        IAirConditioner TclAirConditioner = tclFactory.createAirConditioner();
        TclAirConditioner.airConditioner();
        ITelevision TCLTelevision = tclFactory.createTelevision();
        TCLTelevision.television();
    }

    /**
     * 简单工厂类
     */
    private static void simpleFactory() {
        IPlasticProduct a = PlasticFactory.factory("A");
        if (a != null) {
            a.show();
        }

        IPlasticProduct b = PlasticFactory.factory("B");
        if (b != null) {
            b.show();
        }
    }

    /**
     * 工厂方法
     * 某系统日志记录器要求支持多种日志记录方式，
     * 如文件记录、数据库记录等，且用户可以根据要求动态选择日志记录方式，
     * 现使用工厂方法模式设计该系统。
     */
    private static void factoryMethod() {
        FileLogFactory fileLogFactory = new FileLogFactory();
        ILog fileLog = fileLogFactory.createLog();
        fileLog.writeLog("把日志写进文件");
        DatabaseFactory databaseFactory = new DatabaseFactory();
        ILog databaseLog = databaseFactory.createLog();
        databaseLog.writeLog("把日记写进数据库");
    }
}
