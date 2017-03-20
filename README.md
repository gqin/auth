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

