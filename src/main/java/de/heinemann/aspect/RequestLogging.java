package de.heinemann.aspect;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.heinemann.domain.RequestLog;
import de.heinemann.repository.RequestLogRepository;
import de.heinemann.service.CalendarService;
import de.heinemann.service.PrincipalService;

@Component
@Aspect
public class RequestLogging {

	@Autowired
	private RequestLogRepository requestLogRepository;
	
	@Autowired
	private CalendarService calendarService;
	
	@Autowired
	private PrincipalService principalService;
	
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void beanAnnotatedWithRestController() {}

	@Pointcut("execution(public * *(..))")
	public void publicMethod() {}

	@Pointcut("publicMethod() && beanAnnotatedWithRestController()")
	public void publicRestControllerMethod() {}
	
	@Around("publicRestControllerMethod()")
	public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		RequestLog requestLog = new RequestLog();
		requestLog.setMethod(request.getMethod());
		requestLog.setPath(request.getPathInfo());
		requestLog.setUsername(getUsername());
		requestLog.setContent(StringUtils.substring(getContent(request), 0, 255));
		
		try {
			requestLog.setRequested(calendarService.now());
			Object result = joinPoint.proceed();
			requestLog.setResponse(StringUtils.substring(json(result), 0, 255));
			return result;
		} catch (Throwable throwable) {
			//requestLog.setException(ExceptionUtils.getRootCause(throwable).toString());
			throw throwable;
		} finally {	
			long duration = calendarService.now().getTimeInMillis() - requestLog.getRequested().getTimeInMillis();

			requestLog.setDuration(duration);
			requestLogRepository.save(requestLog);
			// TODO Exception handling
		}		
	}
	
	private String json(Object value) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(value);
	}
	
	private String getUsername() {
		try {
			return principalService.getUsername();
		} catch (Exception exception) {
			return "";
		}
	}

	private String getContent(HttpServletRequest request) {
		try {
			return IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8.name());
		} catch (Exception exception) {
			return "";
		}
	}
	
}