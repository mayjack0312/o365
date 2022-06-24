package hqr.o365.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter  {
	
	private static final String ggToken = "ggtoken";
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		UsernamePasswordAuthenticationToken authRequest = getAuth(request);
        setDetails(request, authRequest);
        return getAuthenticationManager().authenticate(authRequest);
	}

	private UsernamePasswordAuthenticationToken getAuth(final HttpServletRequest request) {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String customToken = obtainCustomToken(request);

        String usernameDomain = username.trim() + "|" + customToken;

        return new UsernamePasswordAuthenticationToken(usernameDomain, password);
    }

    @Nullable
    protected String obtainCustomToken(HttpServletRequest request) {
        return request.getParameter(ggToken);
    }
	
}
