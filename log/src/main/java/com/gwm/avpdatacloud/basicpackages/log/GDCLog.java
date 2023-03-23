package com.gwm.avpdatacloud.basicpackages.log;

import com.gwm.avpdatacloud.basicpackages.log.annotation.GDC;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志公共类
 *
 * @author 辛野
 */
@Slf4j
public class GDCLog {


    /**
     * INFO
     * @param userName 当前访问用户名称
     * @param method 当前方法全路径
     * @param message 日志主题
     */
    public static void info(String userName,String method,String message){
        log(userName,method,message, GDC.Level.INFO);
    }

    /**
     * WARN
     * @param userName 当前访问用户名称
     * @param method 当前方法全路径
     * @param message 日志主题
     */
    public static void warn(String userName,String method,String message){
        log(userName,method,message, GDC.Level.WARN);
    }

    /**
     * DEBUG
     * @param userName 当前访问用户名称
     * @param method 当前方法全路径
     * @param message 日志主题
     */
    public static void debug(String userName,String method,String message){
        log(userName,method,message, GDC.Level.DEBUG);
    }

    /**
     * ERROR
     * @param userName 当前访问用户名称
     * @param method 当前方法全路径
     * @param message 日志主题
     */
    public static void error(String userName,String method,String message){
        log(userName,method,message, GDC.Level.ERROR);
    }

    /**
     * LOG
     * @param userName 当前访问用户名称
     * @param method 当前方法全路径
     * @param message 当前方法全路径
     * @param level 日志主题
     */
    public static void log(String userName, String method, String message,GDC.Level level){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if(userName!=null){
            sb.append("\"username\":");
            sb.append("\""+userName+"\",");
        }
        if(method!=null){
            sb.append("\"method\":");
            sb.append("\""+method+"\",");
        }
        sb.append("\"message\":");
        sb.append("\""+message+"\"");
        sb.append("}");
        switch (level){
            case INFO:
                log.info(sb.toString());
                break;
            case WARN:
                log.warn(sb.toString());
                break;
            case DEBUG:
                log.debug(sb.toString());
                break;
            case ERROR:
                log.error(sb.toString());
                break;
            default:
                log.trace(sb.toString());

        }
    }
}
