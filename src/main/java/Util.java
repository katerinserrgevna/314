import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Util {
    public static void main(String[] args) {
        //5ebfeb
        User user = new User(3L, "James", "Brown", (byte)15);

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> forEntity = template.getForEntity("http://91.241.64.178:7081/api/users", String.class);
        forEntity.getHeaders().get("Set-Cookie").stream().forEach(System.out::println);

        String cookies = template.getForEntity("http://91.241.64.178:7081/api/users", String.class)
                .getHeaders().get("Set-Cookie").stream().collect(Collectors.joining(";"));


        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie",cookies);

        HttpEntity<User> entity = new HttpEntity<User>(user, headers);
        restTemplate.exchange("http://91.241.64.178:7081/api/users", HttpMethod.POST, entity, String.class);
        forEntity = restTemplate.postForEntity("http://91.241.64.178:7081/api/users", user, String.class);
        System.out.println(forEntity.getBody());

        user.setName("Thomas");
        user.setLastName("Shelby");

        System.out.println(restTemplate.exchange(
                "http://91.241.64.178:7081/api/users", HttpMethod.PUT, entity, String.class).getBody());

        System.out.println(restTemplate.exchange(
                "http://91.241.64.178:7081/api/users/"+user.getId(), HttpMethod.DELETE, entity, String.class).getBody());
    }
}
