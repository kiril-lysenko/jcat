package com.self.education.catinfo.config;

import static java.util.concurrent.TimeUnit.MINUTES;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.SneakyThrows;

@Component
public class RequestThrottleFilter implements Filter {

    private final Integer maxRequestsPerMinute;
    private final LoadingCache<String, Integer> requestCountsPerIpAddress;

    public RequestThrottleFilter(@Value("${max.requests.per.minute}") final Integer maxRequestsPerMinute) {
        super();
        this.maxRequestsPerMinute = maxRequestsPerMinute;
        this.requestCountsPerIpAddress =
                CacheBuilder.newBuilder().expireAfterWrite(1, MINUTES).build(new CacheLoader<>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    @Override
    public void init(final FilterConfig filterConfig) {
        //Should not implement
    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
            final FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String clientIpAddress = getClientIP(httpServletRequest);
        if (isMaximumRequestsPerMinuteExceeded(clientIpAddress)) {
            httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpServletResponse.getWriter().write("429 Too Many Requests");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @SneakyThrows
    private boolean isMaximumRequestsPerMinuteExceeded(final String clientIpAddress) {
        Integer requests = requestCountsPerIpAddress.get(clientIpAddress);
        if (requests != null) {
            if (requests > maxRequestsPerMinute) {
                requestCountsPerIpAddress.asMap().remove(clientIpAddress);
                requestCountsPerIpAddress.put(clientIpAddress, requests);
                return true;
            }
        } else {
            requests = 0;
        }
        requests++;
        requestCountsPerIpAddress.put(clientIpAddress, requests);
        return false;
    }

    private String getClientIP(final HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    @Override
    public void destroy() {
        //Should not implement
    }
}
