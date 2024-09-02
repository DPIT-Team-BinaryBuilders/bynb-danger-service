//package com.binarybuilders.bynb_danger_service.config;
//
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.crypto.SecretKey;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    @Value("${security.jwt.secret-key}")
//    private String jwtSecret;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        // 1 obtain header that contains jwt
//
//        String authHeader = request.getHeader("Authorization"); // Bearer jwt
//
//        if(authHeader == null || !authHeader.startsWith("Bearer ")){
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // 2 obtain jwt token
//        String jwt = authHeader.split(" ")[1];
//
//        Claims claims =Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(jwt).getPayload();
//        String username = claims.getSubject();
//        List<String> roles = (List<String>) claims.get("roles");
//        List<SimpleGrantedAuthority> authorities = roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .toList();
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                username, null, authorities
//        );
//        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authToken);
//        SecurityContextHolder.setContext(context);
//        // 5 execute rest of the filters
//        System.out.println(context.getAuthentication());
//        filterChain.doFilter(request, response);
//
//
//    }
//    private SecretKey generateKey(){
//        byte[] secreateAsBytes = Decoders.BASE64.decode(jwtSecret);
//        return Keys.hmacShaKeyFor(secreateAsBytes);
//    }
//}
//
//
