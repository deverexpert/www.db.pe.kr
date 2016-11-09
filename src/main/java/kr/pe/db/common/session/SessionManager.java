package kr.pe.db.common.session;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {
	
	private static final String LOGIN_SESSION_NAME = "LOGIN_SESSION_NAME";

	@Autowired
	private HttpServletRequest request;

	public SessionVO getMemberSession() {

		SessionVO memberSession = (SessionVO) this.request.getSession().getAttribute(LOGIN_SESSION_NAME);
		if (memberSession == null) {
			memberSession = new SessionVO();
		}

		return memberSession;
	}

	public void setMemberSession(final SessionVO sessionVO) {
		this.request.getSession().setAttribute(LOGIN_SESSION_NAME, sessionVO);
	}

	public boolean sessionCheck() {
		return (this.getMemberSession().getMemberId() != null) ? true : false;
	}
	

	class SessionVO {
		private String memberId;
		
		private String loginId;
		
		private String loginNm;

		
		public String getMemberId() {
			return memberId;
		}

		public void setMemberId(String memberId) {
			this.memberId = memberId;
		}

		public String getLoginId() {
			return loginId;
		}

		public void setLoginId(String loginId) {
			this.loginId = loginId;
		}

		public String getLoginNm() {
			return loginNm;
		}

		public void setLoginNm(String loginNm) {
			this.loginNm = loginNm;
		}
		
		
	}
}
