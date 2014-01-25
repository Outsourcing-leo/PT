package org.tnt.pt;

import org.eclipse.jetty.server.Server;
import org.springside.modules.test.jetty.JettyFactory;

/**
 * 使用Jetty运行调试Web应用, 在Console输入回车快速重新加载应用.
 * 
 * @author calvin
 */
public class InfoServiceServer {

	public static final int PORT = 8090;
	public static final String CONTEXT = "/pt";
	public static final String BASE_URL = "http://localhost:8090/pt";
	public static final String[] TLD_JAR_NAMES = new String[] { "spring-webmvc", "shiro-web",
			"springside-core","wabacus" };

	public static void main(String[] args) throws Exception {
		// 设定Spring的profile
		System.setProperty("spring.profiles.active", "development");
		// 启动Jetty
		Server server = JettyFactory.createServerInSource(PORT, CONTEXT);
		JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);
		
		try {
			server.start();
			System.out.println("Server running at " + BASE_URL);
			System.out.println("Hit Enter to reload the application");
			// 等待用户输入回车重载应用.
			while (true) {
				char c = (char) System.in.read();
				if (c == '\n') {
					JettyFactory.reloadContext(server);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
}
