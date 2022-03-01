package br.com.company.sales.security.jwt;

import br.com.company.sales.service.UserSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private UserSystemService userSystemService;
    private JwtService jwtService;

    public JwtAuthFilter(@Autowired UserSystemService userSystemService,
                         @Autowired JwtService jwtService) {
        this.userSystemService = userSystemService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //Armazenando valor do cabeçalho "Autorization" da requisição.
        String headerAuthorization = request.getHeader("Authorization");

        //Validando cabeçalho não nulo e iniciando com "Bearer".
        if(headerAuthorization != null && headerAuthorization.startsWith("Bearer")){
            //Armazenando token contido no cabeçalho.
            String token = headerAuthorization.split(" ")[1];
            //Validando token.
            if(jwtService.tokenIsValid(token)){
                //Armazenando username que vem no token.
                String username = jwtService.getUserName(token);
                //Armazendo userDatails que será colocado no contexto da aplicação.
                UserDetails userDetails = userSystemService.loadUserByUsername(username);
                //Criando usuário que será autenticado, a partir do userDatails e suas autorizações.
                UsernamePasswordAuthenticationToken userAuthenticated =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                //Inserindo detalhes para usuário autenticado, a partir do request.
                userAuthenticated.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //Autenticando usuário no contexto da aplicação.
                SecurityContextHolder.getContext().setAuthentication(userAuthenticated);
            }
        }
        //O processo acima será excutado antes da requisição seguir.
        //Seguindo com a requisição.
        filterChain.doFilter(request, response);
    }
}
