package com.yunmu.geek;

import com.yunmu.geek.inbound.HttpInboundServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;

public class GeekApplication {
    private static Logger logger = LoggerFactory.getLogger(GeekApplication.class);

    /** 网关名称 */
    public final static String GATEWAY_NAME = "yunmuGateway";

    /** 网关版本号 */
    public final static String GATEWAY_VERSION = "1.0.0";

    /** 代理端口 Key */
    public final static String PROXY_PORT_KEY = "proxyPort";

    /** 代理端口 Def */
    public final static String PROXY_PORT_DEF = "8888";

    /** 代理服务 Key */
    public final static String PROXY_SERVERS_KEY = "proxyServers";

    /** 代理服务 Def */
    public final static String PROXY_SERVERS_DEF = "http://localhost:8801,http://localhost:8802";

    public static void main(String[] args) {
        /** 从系统环境或 *.properties 等配置文件中读取 key 对应的值，
         * 当 key 值为 NULL 时, 返回 def 的值； 当 key 值不为 NULL 时, 返回 key 的值 */
        int proxyPort = Integer.parseInt(System.getProperty(PROXY_PORT_KEY, PROXY_PORT_DEF));

        /** 模拟多后端 url，使用随机路由 */
        String proxyServers = System.getProperty(PROXY_SERVERS_KEY, PROXY_SERVERS_DEF);

        logger.info(GATEWAY_NAME + "_" + GATEWAY_VERSION +" started at http://localhost:{} for server:{}", proxyPort, proxyServers);
        HttpInboundServer server = new HttpInboundServer(proxyPort, Arrays.asList(proxyServers.split(",")));
        try {
            server.run();
        }catch (Throwable throwable){
            logger.error("Gateway exception!", throwable);
        }
    }
}
