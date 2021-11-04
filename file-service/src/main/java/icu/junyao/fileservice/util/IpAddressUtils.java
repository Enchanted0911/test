package icu.junyao.fileservice.util;

import cn.hutool.core.util.StrUtil;
import icu.junyao.fileservice.constant.CommonConstants;
import icu.junyao.fileservice.constant.IpConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author johnson
 * @since 2021-11-04
 */
public class IpAddressUtils {
    public static String getIpAddress(HttpServletRequest request) {
        String xIp = request.getHeader(IpConstants.X_REAL_IP);
        String xFor = request.getHeader(IpConstants.X_FORWARDED_FOR);
        if (StrUtil.isNotEmpty(xFor) && !IpConstants.UNKNOWN.equalsIgnoreCase(xFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = xFor.indexOf(CommonConstants.COMMA);
            if (index != -1) {
                return xFor.substring(0, index);
            } else {
                return xFor;
            }
        }
        xFor = xIp;
        if (StrUtil.isNotEmpty(xFor) && !IpConstants.UNKNOWN.equalsIgnoreCase(xFor)) {
            return xFor;
        }
        if (StrUtil.isBlank(xFor) || IpConstants.UNKNOWN.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader(IpConstants.PROXY_CLIENT_IP);
        }
        if (StrUtil.isBlank(xFor) || IpConstants.UNKNOWN.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader(IpConstants.WL_PROXY_CLIENT_IP);
        }
        if (StrUtil.isBlank(xFor) || IpConstants.UNKNOWN.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader(IpConstants.HTTP_CLIENT_IP);
        }
        if (StrUtil.isBlank(xFor) || IpConstants.UNKNOWN.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader(IpConstants.HTTP_X_FORWARDED_FOR);
        }
        if (StrUtil.isBlank(xFor) || IpConstants.UNKNOWN.equalsIgnoreCase(xFor)) {
            xFor = request.getRemoteAddr();
        }
        return xFor;
    }

}
