package by.bsuir.lab3.token;

import by.bsuir.lab3.exception.InvalidTokenException;
import by.bsuir.lab3.service.EmployeeService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final EmployeeService employeeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = tokenProvider.resolveToken(request);
        try {
            if (token != null && tokenProvider.isValidToken(token))
                setAuthentication(getAuthentication(token));
        } catch (InvalidTokenException e) {
            SecurityContextHolder.clearContext();
            putErrorIntoResponse(response, e);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(Authentication authentication) {
        if (authentication != null)
            SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Authentication getAuthentication(String token) {
        UserDetails userDetails = employeeService.loadUserByUsername(tokenProvider.getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private void putErrorIntoResponse(HttpServletResponse response, InvalidTokenException ex) {
        response.setHeader("error", ex.getMessage());
        response.setStatus(ex.getStatus().value());
        response.setContentType("application/json");
    }
}
