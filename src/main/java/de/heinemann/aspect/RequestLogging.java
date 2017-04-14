package de.heinemann.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.heinemann.repository.RequestLogRepository;
import de.heinemann.service.CalendarService;

@Component
@Aspect
public class RequestLogging {

	@Autowired
	private RequestLogRepository requestLogRepository;
	
	@Autowired
	private CalendarService calendarService;
	
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void beanAnnotatedWithRestController() {}

	@Pointcut("execution(public * *(..))")
	public void publicMethod() {}

	@Pointcut("publicMethod() && beanAnnotatedWithRestController()")
	public void publicRestControllerMethod() {}
	
//	@Around("publicRestControllerMethod()")
//	public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
//		RequestLog requestLog = new RequestLog();
//		requestLog.setRequested(calendarService.now());
//		
//		
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//		
//		try {
//			 Object result = joinPoint.proceed();
//			 return result;
//		} catch (Throwable throwable) {
//			//requestLog.setException(ExceptionUtils.getRootCause(throwable).toString());
//			throw throwable;
//		} finally {
//			stopWatch.stop();	
//			requestLog.setDuration(stopWatch.getTotalTimeMillis());
//			requestLogRepository.save(requestLog);
//			// TODO Exception handling
//		}		
//	}	
	
}
