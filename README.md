# ODG AppCenter Authentication Server
Odg AppCenter Authentication Server provides **Resource owner credentials grant**, details informations you may reads 
* [An Introduction to OAuth2](
https://www.digitalocean.com/community/tutorials/an-introduction-to-oauth-2)
* [A Guide To OAuth 2.0 Grants](https://alexbilbie.com/guide-to-oauth-2-grants/)
* extra readings
https://aaronparecki.com/oauth-2-simplified/


## Examples to configurate Source Server to access authorization server
http://www.baeldung.com/rest-api-spring-oauth2-angularjs

## Testing Scripts
curl acme:acmesecret@localhost:9999/uaa/oauth/token -d grant_type=password -d username=gary.qin@osterhoutgroup.com -d password=123 -d redirect_uri=OdgAppCenterUrl

curl -X POST -H "Authorization: Bearer ACCESS_TOKEN""Odg App Center Services" 

## Resource Server setup for Oauth
* pom.xml
<!-- oauth -->
		<dependency>
			<groupId>org.springframework.security.oauth</groupId>
			<artifactId>spring-security-oauth2</artifactId>
		</dependency>
* edit application.properties file, for example
odg.auth.end_point_url=http://localhost:9999/oauth/check_token
odg.auth.client_id=acme
odg.auth.client_secret=acmesecret

* add OAuth2ResourceServerConfig.java file
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${odg.auth.end_point_url}")
    private String endPointUrl;

    @Value("${odg.auth.client_id}")
    private String clientId;

    @Value("${odg.auth.client_secret}")
    private String clientSecret;

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
            .authorizeRequests()
            .anyRequest().authenticated();
        // @formatter:on
    }

    @Override
    public void configure(final ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());
    }

    // Remote token service
    @Primary
    @Bean
    public RemoteTokenServices tokenServices() {
        final RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl(endPointUrl);
        tokenService.setClientId(clientId);
        tokenService.setClientSecret(clientSecret);
        return tokenService;
    }
}
