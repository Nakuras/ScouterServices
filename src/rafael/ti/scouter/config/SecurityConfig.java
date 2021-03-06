package rafael.ti.scouter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.authorizeRequests()
				//permiss�o de usuario
				.antMatchers(HttpMethod.POST, "/rest/auth/**").permitAll()
				.antMatchers(HttpMethod.GET, "/usuario/**").permitAll()
				.antMatchers(HttpMethod.PUT, "/usuario/**").permitAll()
				.antMatchers(HttpMethod.DELETE, "/usuario/**").permitAll()
				//permiss�o de cliente
				/*.antMatchers(HttpMethod.GET, "/cliente/**").permitAll()
				.antMatchers(HttpMethod.POST, "/cliente/**").permitAll()
				.antMatchers(HttpMethod.PUT, "/cliente/**").permitAll()*/
				.anyRequest().permitAll()
				
		.and()
			.csrf().disable()
			.cors();
				
	}
	
}
