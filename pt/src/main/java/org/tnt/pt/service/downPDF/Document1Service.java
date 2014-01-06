package org.tnt.pt.service.downPDF;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.tnt.pt.util.FreeMarkers;

import freemarker.template.Configuration;
import freemarker.template.Template;


public class Document1Service {

	private static final String DEFAULT_ENCODING = "utf-8";

	private static Logger logger = LoggerFactory.getLogger(Document1Service.class);


	private Template template;


	/**
	 * 使用Freemarker生成html格式内容.
	 */
	public  String generateContent(Map<String,Object> context) {
		return FreeMarkers.renderTemplate(template, context);
	}

	/**
	 * 注入Freemarker引擎配置,构造Freemarker 邮件内容模板.
	 */
	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) throws IOException {
		//根据freemarkerConfiguration的templateLoaderPath载入文件.
		template = freemarkerConfiguration.getTemplate("document1.ftl", DEFAULT_ENCODING);
	}
}
