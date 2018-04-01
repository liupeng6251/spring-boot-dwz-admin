package org.qvit.caterpillar;

import org.qvit.caterpillar.config.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

/**
 * Created by liupeng on 2018/4/1.
 */
@Controller
@EnableAutoConfiguration
public class AppApplication {

    private static Logger logger = LoggerFactory.getLogger(AppApplication.class);

    @RequestMapping("/")
    public String home() {
        if(SystemConfig.isInit()){
            return "index";
        }else{
            return "init";
        }
    }

    public static void main(String[] args) throws Exception {
        if(!SystemConfig.isInit()){
            logger.info("");
            logger.info("++++++++"+SystemConfig.generAuthCode()+"+++++++++++");
            logger.info("");
        }
        ConfigurableApplicationContext context= SpringApplication.run(AppApplication.class, args);
        //context.get
    }

    /**
     * 打开默认浏览器访问页面
     */
    public static void openDefaultBrowser(String url,String port) {
        //启用系统默认浏览器来打开网址。
        try {
            URI uri = new URI("http://"+url+":"+port);
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取ip地址
    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
