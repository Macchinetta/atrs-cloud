/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.common.boot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.web.servlet.support.RequestDataValueProcessor;
import org.terasoluna.gfw.web.mvc.support.CompositeRequestDataValueProcessor;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenRequestDataValueProcessor;

//FIXME RequestDataValueProcessorが上書きされ、トランザクショントークンが使用できない問題への暫定対処
// ( issue : https://github.com/spring-projects/spring-boot/issues/4676 ) 
public class RequestDataValueProcessorPostProcessor implements
                                                   BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    @Override
    public void postProcessBeanDefinitionRegistry(
            BeanDefinitionRegistry registry) throws BeansException {
        ConstructorArgumentValues cav = new ConstructorArgumentValues();
        List<RequestDataValueProcessor> values = new ArrayList<RequestDataValueProcessor>();
        values.add(new TransactionTokenRequestDataValueProcessor());
        values.add(new CsrfRequestDataValueProcessor());
        cav.addGenericArgumentValue(values);
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(CompositeRequestDataValueProcessor.class, cav, null);

        registry.removeBeanDefinition("requestDataValueProcessor");
        registry.registerBeanDefinition("requestDataValueProcessor",
                rootBeanDefinition);
    }
}
