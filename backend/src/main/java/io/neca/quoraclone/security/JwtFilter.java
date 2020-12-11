package io.neca.quoraclone.security;

import io.neca.quoraclone.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private InvalidJwtUtil invalidJwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtUtil.getTokenFromRequest(request);
       // String username = jwtUtil.getUsername(token);
       // int userId = getUserId(username);

        try {
            if (token != null && jwtUtil.validateToken(token)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.getUsername(token));
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
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

    /*
    private int getUserId(String username) {
        return repository.findByUsername(username).getId();
    }
    */
}
