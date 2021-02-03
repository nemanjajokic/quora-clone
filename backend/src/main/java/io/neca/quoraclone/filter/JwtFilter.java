package io.neca.quoraclone.filter;

import io.neca.quoraclone.exception.CustomException;
import io.neca.quoraclone.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtUtil.getTokenFromRequest(request);

        try {
            if (token != null && jwtUtil.validateToken(token, userDetailsService.loadUserByUsername(jwtUtil.getUsername(token)))) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetailsService.loadUserByUsername(jwtUtil.getUsername(token)),
                        null,
                        userDetailsService.loadUserByUsername(jwtUtil.getUsername(token)).getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (CustomException ex) {
            // Only a guarantee that user is not authenticated
            SecurityContextHolder.clearContext();
            throw new CustomException("Token filtering failed, user is not authenticated");
        }

        filterChain.doFilter(request, response);
    }

}
