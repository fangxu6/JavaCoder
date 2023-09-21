package com.jse.commons.util;

import com.zhuanqian.commons.log.ZqLogger;
import groovy.lang.GroovyClassLoader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @company jse-zq
 * @author jingji
 * @version SpringBeanUtil.java, v 0.1 2019年7月3日 下午2:32:01
 */
@Component
public class SpringBeanUtil implements BeanFactoryAware {
	/** logger */
	private static final ZqLogger	logger	= new ZqLogger(SpringBeanUtil.class);

	private BeanFactory				beanFactory;

	public boolean containsBeanDefinition(String beanName) {
		if (beanFactory instanceof BeanDefinitionRegistry) {
			return ((BeanDefinitionRegistry) beanFactory).containsBeanDefinition(beanName);
		}
		return false;
	}

	@SuppressWarnings({ "rawtypes", "resource" })
	public boolean addParseBeanBySourceCode(String beanName, String sourceCode) {
		// try {
		// GenericBeanDefinition bd = new GenericBeanDefinition();
		// bd.setBeanClassName(GroovyScriptFactory.class.getName());
		// bd.setAttribute(ScriptFactoryPostProcessor.LANGUAGE_ATTRIBUTE, "groovy");
		// // GroovyScriptFactory 构造器
		// bd.getConstructorArgumentValues().addIndexedArgumentValue(0, "inline:" + sourceCode);
		// // 注册到spring容器
		// if (beanFactory instanceof BeanDefinitionRegistry) {
		// ((BeanDefinitionRegistry) beanFactory).registerBeanDefinition(beanName, bd);
		// logger.info("init load beanName:" + beanName);
		// return true;
		// } else {
		// logger.error("无法实例化  beanName:" + beanName);
		// }
		// } catch (Throwable t) {
		// logger.error(t, "出现异常,无法实例化  beanName:" + beanName);
		// }
		// return false;

		try {
			Class clazz = new GroovyClassLoader().parseClass(sourceCode);
			BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
			BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
			if (beanFactory instanceof DefaultListableBeanFactory) {
				// ((DefaultListableBeanFactory) beanFactory).applyBeanPostProcessorsAfterInitialization(beanDefinition,
				// beanName);
				((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(beanName, beanDefinition);
				// logger.info("init load beanName:" + beanName);
				return true;
			}
		} catch (Throwable t) {
			logger.error(beanName + "register error", t);
		}
		return false;

	}

	/**
	 * 从容器中移除Bean
	 */
	public void removeBeanByBeanName(String beanName) {
		try {
			((BeanDefinitionRegistry) beanFactory).removeBeanDefinition(beanName);
		} catch (Throwable t) {
			logger.error(beanName + "removeBean error", t);
		}
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
