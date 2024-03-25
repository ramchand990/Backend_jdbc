package com.healspan.claim.config;

import com.healspan.claim.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/authentication/login"
                        , "/authentication/welcome"
                        , "/healspan/claim/hospitaluserdashboarddata/*"
                        , "/healspan/claim/healspanuserdashboarddata/*"
                        , "/healspan/claim/retrieveclaim/*"
                        , "/healspan/claim/createorupdateclaimandpatientinfo"
                        , "/healspan/claim/createorupdatemedicalinfo"
                        , "/healspan/claim/createorupdateinsuranceinfo"
                        , "/healspan/claim/savequestionnairesanddocument"
                        , "/healspan/claim/updateclaimstatus"
                        , "/healspan/claim/updatestage"
                        , "/healspan/claim/comment"
                        , "/healspan/claim/pushclaimdatatorpa"
                        , "/healspan/claim/otherdiagnosisdocument/*"
                        , "/healspan/claim/getdocument"
                        , "/healspan/claim/getusernotification/*"
                        , "/healspan/claim/updateusernotification"
                        , "/healspan/claim/upload"
                        , "/healspan/claim/download/*"
                        , "/healspan/claim/downloadZip/*"
                        , "/healspan/claim/delete-documents/*"
                        , "/healspan/claim/preview-document/*"
                        , "/tpa/claim/tparesponse"
                        , "/healspan/claim/admin/masters"
                        , "/healspan/claim/admin/masters/name/*"
                        , "/healspan/claim/reviewer-claims/*"
                        , "/healspan/claim/admin/masters/insert"
                        , "/healspan/claim/admin/masters/update"
                        , "/healspan/claim/get-tpawise-master-details/*"
                        , "/healspan/claim/get-master-details/*"
                        , "/configuration/ui"
                        , "/swagger-resources/**"
                        , "/configuration/security"
                        , "/swagger-ui.html"
                        , "/webjars/**"
                        , "/v2/api-docs/**"

                ).permitAll().
                anyRequest().authenticated().and().
                exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

}