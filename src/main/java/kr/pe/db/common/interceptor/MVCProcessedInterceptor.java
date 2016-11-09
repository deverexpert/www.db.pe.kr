/*******************************************************************************
 * Copyright(c) 2014 deverexpert. All rights reserved.
 * This software is the proprietary information of deverexpert.
 *******************************************************************************/
package kr.pe.db.common.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.pe.db.common.session.SessionManager;

/**
 * 
 * @author <a href=mailto:deverexpert@gmail.com>Lee Jaehyeong</a>
 * @since 2016. 5. 5.
 */
public class MVCProcessedInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MVCProcessedInterceptor.class);
    
	private final List<String> excludeMappingUrl = new ArrayList<String>() {
		/**
		 *
		 */
		private static final long serialVersionUID = 2002936573537632056L;

		{
			this.add("/");
		}
	};
    
    @Autowired
    SessionManager sessionManager;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO Auto-generated method stub
        
        //현재 시간을 모델에 넣음.
        request.setAttribute("requestTime", System.currentTimeMillis());
        
        try {
			if (!this.excludeMappingUrl.contains(request.getRequestURI())) {
				if ( !this.sessionManager.sessionCheck() ) {
					response.sendRedirect(request.getContextPath() + "/");
					return false;
				} else {
					request.setAttribute("sessionManager", sessionManager.getMemberSession());
				}
			}
        } catch ( Exception e ) {
        	logger.error("", e);
        }
        
        return true;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        super.postHandle(request, response, handler, modelAndView);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // TODO Auto-generated method stub
        
        long processedTime = System.currentTimeMillis() - (Long)request.getAttribute("requestTime");
        
        logger.info("Request Processed URL : " + request.getRequestURI() + "[" + processedTime + "msec]");
        
        super.afterCompletion(request, response, handler, ex);
    }
}
