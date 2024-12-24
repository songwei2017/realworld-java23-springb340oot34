package com.world.filter;


import com.world.util.JwtUtil;
import com.world.vo.AuthUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {



        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK); // 设置状态码为200
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");
        String token = null;


        if (authorizationHeader != null && authorizationHeader.startsWith("Token ")) {
            token = authorizationHeader.substring(6);
        }
        if (token != null) {
            try {
                AuthUser authUser = new AuthUser();
                Long id = JwtUtil.getUserIdFromToken(token);
                if (id == null){
                    // 如果没有设置安全上下文，说明没有认证成功，返回401
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Unauthorized");
                    return;
                }

                authUser.setUsername(String.valueOf(id));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        authUser, null, authUser.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                response.setStatus(401);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}