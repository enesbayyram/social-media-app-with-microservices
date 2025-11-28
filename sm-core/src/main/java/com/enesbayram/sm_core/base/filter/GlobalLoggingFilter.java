package com.enesbayram.sm_core.base.filter;

import com.enesbayram.sm_core.base.enums.HttpMethodType;
import com.enesbayram.sm_core.base.enums.LoggingType;
import com.enesbayram.sm_core.base.model.HttpTraceLog;
import com.enesbayram.sm_core.base.repository.HttpTraceLogRepository;
import com.enesbayram.sm_core.base.utils.JSONUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class GlobalLoggingFilter {

    private final DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    private final HttpTraceLogRepository httpTraceLogRepository;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void allRestControllerMethods() {
    }

    private String[] getMethodParametersName(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return nameDiscoverer.getParameterNames(signature.getMethod());
    }

    private Object[] getMethodParametersValue(JoinPoint joinPoint) {
        return joinPoint.getArgs();
    }

    private Map<String, Object> mappingNameAndValue(JoinPoint joinPoint) {
        Map<String, Object> map = new HashMap<>();
        String[] parametersName = getMethodParametersName(joinPoint);
        Object[] parametersValue = getMethodParametersValue(joinPoint);

        for (int i = 0; i < parametersName.length; i++) {
            Object value = parametersValue[i];

            if (value instanceof MultipartFile multipartFile) {
                Map<String, Object> fileInfo = new HashMap<>();
                fileInfo.put("originalFilename", multipartFile.getOriginalFilename());
                fileInfo.put("size", multipartFile.getSize());
                fileInfo.put("contentType", multipartFile.getContentType());
                map.put(parametersName[i], fileInfo);
            } else {
                map.put(parametersName[i], value);
            }
        }
        return map;
    }

    private HttpMethodType findHttpMethodType() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return null; // WebSocket veya async thread, HTTP yok
        }

        String methodType = httpServletRequest.getMethod();
        for (HttpMethodType httpMethodType : HttpMethodType.values()) {
            if (httpMethodType.name().equals(methodType)) {
                return httpMethodType;
            }
        }
        return null;
    }

    private HttpTraceLog createHttpTraceLogModel(JoinPoint joinPoint, String request, String response, LoggingType loggingType) {
        HttpTraceLog httpTraceLog = new HttpTraceLog();
        httpTraceLog.setCreateTime(LocalDateTime.now());
        httpTraceLog.setCreateUser("ebayram");

        // HTTP request mevcutsa path ve method bilgisi ekle
        if (RequestContextHolder.getRequestAttributes() != null) {
            httpTraceLog.setRequestPath(httpServletRequest.getRequestURI());
            httpTraceLog.setMethodType(findHttpMethodType());
        }

        httpTraceLog.setRequest(request);
        httpTraceLog.setResponse(response);
        httpTraceLog.setStatus(loggingType.equals(LoggingType.SUCCESS) ? 200 : 500);
        return httpTraceLog;
    }

    private void saveHttpTraceLog(JoinPoint joinPoint, Object response, LoggingType loggingType) {
        HttpTraceLog httpTraceLog = createHttpTraceLogModel(
                joinPoint,
                JSONUtils.toJSON(mappingNameAndValue(joinPoint)),
                JSONUtils.toJSON(response),
                loggingType
        );
        httpTraceLogRepository.save(httpTraceLog);
    }

    @AfterReturning(value = "allRestControllerMethods()", returning = "response")
    public void logAfterAllMethodsSuccess(JoinPoint joinPoint, Object response) {
        saveHttpTraceLog(joinPoint, response, LoggingType.SUCCESS);
    }

    @AfterThrowing(value = "allRestControllerMethods()", throwing = "ex")
    public void logAfterAllMethodsThrows(JoinPoint joinPoint, Throwable ex) {
        saveHttpTraceLog(joinPoint, ex.getMessage(), LoggingType.ERROR);
    }
}
