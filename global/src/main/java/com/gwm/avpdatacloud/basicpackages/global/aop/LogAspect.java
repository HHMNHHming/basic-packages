package com.gwm.avpdatacloud.basicpackages.global.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gwm.avpdatacloud.basicpackages.log.GDCLog;
import com.gwm.avpdatacloud.basicpackages.log.annotation.GDC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
@Aspect
public class LogAspect {

    @Value("${com.gwm.avpdatacloud.basicpackages.log.level:debug}")
    private String level;

    @Pointcut("@annotation(com.gwm.avpdatacloud.basicpackages.log.annotation.GDC) or @target(com.gwm.avpdatacloud.basicpackages.log.annotation.GDC)")
    public void methods(){}

    @Around("methods()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        boolean isLog = true;
        //获取GDC注解
        //注解存在时，进行日志记录
        MethodSignature signature = (MethodSignature)point.getSignature();
        GDC gdc = signature.getMethod().getAnnotation(GDC.class);
        if(null == gdc){
            gdc = point.getTarget().getClass().getAnnotation(GDC.class);
        }

        if(null == gdc){
            isLog = false;
        }
        Api api = point.getTarget().getClass().getAnnotation(Api.class);
        ApiOperation apiOperation = signature.getMethod().getAnnotation(ApiOperation.class);
        String user = getUserName(point);
        String message = api!=null&& apiOperation!=null? Arrays.toString(api.tags())+apiOperation.value()+"("+apiOperation.httpMethod()+")":"";
        String str = "";
        //请求前日志
        if(isLog){
            if(gdc.level()==GDC.Level.DEBUG){
                str = ": "+ parseArgs(point);
            }
            GDCLog.log(user,signature.getName(),message+str,gdc.level());
        }
        Object result;
        try{
            long start = System.currentTimeMillis();
            result = point.proceed();
            long end = System.currentTimeMillis();
            if(isLog){
                if(gdc.level()==GDC.Level.DEBUG){
                    str = "返回("+(end-start)+"ms): "+ JSON.toJSONString(result);
                }
                GDCLog.log(user,signature.getName(),message+str,gdc.level());
            }
        }catch(Throwable e){
            GDCLog.error(user,signature.getName(),message+": "+e.getMessage());
            throw e;
        }

        return result;
    }
    private String parseArgs(JoinPoint joinPoint) {
        StringBuffer sb = new StringBuffer();
        Object[] args = joinPoint.getArgs();
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        if (args != null) {
            for (int i=0;i<args.length;i++) {
                Object arg = args[i];
                String name = argNames[i];
                if (arg instanceof BindingResult ||
                        arg instanceof HttpServletRequest ||
                        arg instanceof HttpServletResponse ||
                        arg instanceof MultipartFile ||
                        arg instanceof MultipartFile[]) {
                    continue;
                }
                sb.append(" "+name+":");
                sb.append(JSONObject.toJSONString(arg));
            }
        }
        return sb.toString();
    }
    private String getUserName(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        for(Object arg:args){
            if(arg instanceof HttpServletRequest){
                HttpServletRequest request = (HttpServletRequest)arg;
                Object userName = request.getAttribute("userName");
                if(userName==null){
                    return null;
                }
                return userName.toString();
            }
        }
        return null;
    }
}
