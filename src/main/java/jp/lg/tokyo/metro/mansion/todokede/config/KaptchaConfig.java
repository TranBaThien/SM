/*
 * @(#) KaptchaConfig.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.config;

import java.util.Properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import jp.lg.tokyo.metro.mansion.todokede.common.KaptchaProperties;

@Configuration
@ConditionalOnClass(DefaultKaptcha.class)
@ConditionalOnWebApplication
@EnableConfigurationProperties(KaptchaProperties.class)
public class KaptchaConfig {

    @Bean
    @ConditionalOnMissingBean(Producer.class)
    public DefaultKaptcha kaptcha(KaptchaProperties kaptchaProperties) {
        Properties properties = new Properties();
        properties.put("kaptcha.border", kaptchaProperties.getBorder());
        properties.put("kaptcha.textproducer.font.color", kaptchaProperties.getTextproducerFontColor());
        properties.put("kaptcha.textproducer.char.space", kaptchaProperties.getTextproducerCharSpace());
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
