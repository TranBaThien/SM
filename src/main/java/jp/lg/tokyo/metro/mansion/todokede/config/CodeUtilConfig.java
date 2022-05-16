/*
 * @(#)CodeUtilConfig.java 2019/11/30
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 * 
 * Description: Configuration code list when start app
 *
 * Create Date: 2019/11/30
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.commons.digester3.Digester;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ServletContextAware;

import jp.lg.tokyo.metro.mansion.todokede.common.utils.AbstractCodeXMLReadImpl;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;

@Configuration
public class CodeUtilConfig extends AbstractCodeXMLReadImpl implements InitializingBean, ServletContextAware  {

	/** commonロガー */
	protected static final Logger LOG = LogManager.getLogger(CodeUtilConfig.class);

	
	@Value("classpath:code.xml")
	private Resource resources;

	/** variable servlet context for prepare data */
	private ServletContext servletContext;


	/* Set servlet context */
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/* Method set  properties*/
	@Override
	public void afterPropertiesSet() throws Exception {
		File file = resources.getFile();

		try (InputStream is = new FileInputStream(file)) {

			CodeUtil.CodeMap map = new CodeUtil.CodeMap();
			Digester digester = constructDigester(map);
 
			digester.parse(is); 
		
			// ServletContextからも参照できるようにする
			servletContext.setAttribute(CodeUtil.CODEMAP, map);
 
			if (LOG.isInfoEnabled()) {
				LOG.debug(resources.getFilename() + "のコードを初期化しました。");
			} 

		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

}
