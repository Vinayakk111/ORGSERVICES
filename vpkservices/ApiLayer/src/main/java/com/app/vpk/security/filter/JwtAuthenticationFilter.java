package com.app.vpk.security.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.app.vpk.security.JwtTokenProvider;
import com.app.vpk.services.CustomUserDetailsService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	private final CustomUserDetailsService userDetailsService;

	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService userDetailsService) {

		this.jwtTokenProvider = jwtTokenProvider;

		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = resolveToken(request);

		try {
			if (token != null && jwtTokenProvider.validateToken(token)
					&& SecurityContextHolder.getContext().getAuthentication() == null) {
				String username = jwtTokenProvider.getUsername(token);

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}

		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {

		String header = request.getHeader("Authorization");

		if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {

			return header.substring(7);
		}

		return null;
	}
}