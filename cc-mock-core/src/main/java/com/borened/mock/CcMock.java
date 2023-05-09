package com.borened.mock;

import com.borened.mock.config.MockConfig;
import com.borened.mock.strategy.*;

import java.util.*;

/**
 * @author cch
 * @since 2023-04-24
 */
public class CcMock {

    /**
     * 所有策略类集合
     */
    private static final Map<Class<?>, MockStrategy> DATA_STRATEGY_MAP;
    public static final MockConfig MOCK_CONFIG;
    //基本类型的mock
    public static final List<MockStrategy> MOCK_STRATEGIES;

    //java bean的mock
    public static final MockStrategy JAVA_BAEN_STRATEGY;

    static {
        MOCK_CONFIG= new MockConfig();
        MOCK_STRATEGIES= new ArrayList<>();
        JAVA_BAEN_STRATEGY = new JavaBeanMock();
        MOCK_STRATEGIES.add(new IntegerMock());
        MOCK_STRATEGIES.add(new DateMock());
        MOCK_STRATEGIES.add(new BigDecimalMock());
        MOCK_STRATEGIES.add(new StringMock());
        MOCK_STRATEGIES.add(new JavaBeanMock());
    }
    static {
        DATA_STRATEGY_MAP = new HashMap<>();
        for (MockStrategy mockStrategy : MOCK_STRATEGIES) {
            List<Class<?>> classList = mockStrategy.supportTypes();
            if (classList==null) {
                continue;
            }
            for (Class<?> aClass : classList) {
                DATA_STRATEGY_MAP.put(aClass, mockStrategy);
            }
        }
    }

    /**
     * 策略上下文对象委派具体的策略执行算法
     * @return
     */
    public static Object mock(Class<?> clazz) {
        return getExecuteStrategy(clazz).mock(MOCK_CONFIG, clazz);
    }

    /**
     * 获取适用的策略处理类
     */
    private static MockStrategy getExecuteStrategy(Class<?> clazz) {
        //非java包的类型，认为是自定义的类型。使用javaBean 策略进行mock处理。
        MockStrategy strategy = Optional.ofNullable(DATA_STRATEGY_MAP.get(clazz))
                .orElse(clazz.isPrimitive() || clazz.getPackage().getName().contains("java.") ?  null : JAVA_BAEN_STRATEGY);
        if (strategy == null) {
            throw new RuntimeException(String.format("not found available type strategy because strategy type is %s", clazz.getName()));
        }
        return strategy;
    }

/*
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, DynamicDataStrategy> strategyBeans = applicationContext.getBeansOfType(DynamicDataStrategy.class);
        if (strategyBeans == null || strategyBeans.size() == 0) {
            // 无可用的策略
            return;
        }
        dataStrategyMap = new HashMap<>(strategyBeans.size());
        for (DynamicDataStrategy strategy : strategyBeans.values()) {
            dataStrategyMap.put(strategy.applyType().name(), strategy);
            log.info("register data strategy: " + strategy.applyType());
        }
    }*/
}
