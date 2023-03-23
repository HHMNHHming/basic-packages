package com.gwm.avpdatacloud.basicpackages.global.filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwm.avpdatacloud.basicpackages.log.GDCLog;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;


/**
 * 在这里进行请求拦截，位于gateway后面，相较于在gateway中进行类似处理，在此处也可以拦截各个服务之间通过feign的调用。
 * @author 辛野
 */
public class WebFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(!StringUtils.isEmpty(authorization)){
            //解析jwt 获取user name 以及role name
            String jwt = authorization.replace("Bearer ","");
            String[] args = jwt.replace("\r\n", "").split("\\.");
            try{
                if(args.length==3){
                    JSONObject payload = JSONObject.parseObject(new String(Base64.getUrlDecoder().decode(args[1].getBytes())));
                    //获取用户名
                    request.setAttribute("userName",payload.get("user_name"));
                    //获取roleName列表
                    if(payload.containsKey("authorities")){
                        JSONArray authorities = payload.getJSONArray("authorities");
                        String[] roles = new String[authorities.size()];
                        authorities.toArray(roles);
                        request.setAttribute("roleNames",roles);
                    }
                    //获取roleId列表
                    if(payload.containsKey("roleIds")){
                        JSONArray roleIds = payload.getJSONArray("roleIds");
                        Long[] rIds = new Long[roleIds.size()];
                        roleIds.toArray(rIds);
                        request.setAttribute("roleIds",roleIds);
                    }
                }else{
                    GDCLog.error("System","com.gdc.avp.components.filter.WebFilter.doFilter","Token不合法（长度小于3）："+jwt);
                }
            }catch (Exception e){
                GDCLog.error("System","com.gdc.avp.components.filter.WebFilter.doFilter",e.getMessage());
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
