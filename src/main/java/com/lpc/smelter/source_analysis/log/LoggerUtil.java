package com.lpc.smelter.source_analysis.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 日志工具
 *
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2018-06-19 17:56
 */
public class LoggerUtil {

	private static Logger logger = null;

	static {
		try {
			String dir = System.getProperty("user.dir") + "/src/main/java/resources/";
			File file = new File(dir +"log4j2.xml");
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			final ConfigurationSource source = new ConfigurationSource(in);
			Configurator.initialize(null, source);
			logger = LogManager.getLogger("LoggerUtil");
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	public static Logger getLogger(){
		return logger;
	}

	public static void main(String[] args) {
		LoggerUtil.getLogger().info("======开始了======");
	}
}
