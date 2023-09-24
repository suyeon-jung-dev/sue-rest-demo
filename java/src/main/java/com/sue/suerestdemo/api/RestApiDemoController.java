package com.sue.suerestdemo.api;

import com.sue.suerestdemo.domain.Coffee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Controller 는 @Component 의 stereotype (별칭)으로,
 * application 실행시 스프링 IoC 컨테이너에 의해 생성되고 관리된다.
 */

@RestController // @Controller + @ResponseBody = JSON 데이터 타입을 반환하게 하여 REST API 에 적합
public class RestApiDemoController {
    private List<Coffee> coffees = new ArrayList<>();

    public RestApiDemoController() {
        coffees.addAll(
                List.of(
                        new Coffee("Café Cereza"),
                        new Coffee("Café Ganador"),
                        new Coffee("Café Lareño"),
                        new Coffee("Café Trés Pontas")
                )
        );
    }

    /**
     * spring-web 의 기능으로 Jackson 의존성을 통해 객체를 Json 형식으로 marshalling, unmarshalling 직렬화,역직렬화 한다.
     * @return
     */
//    @RequestMapping(value = "/coffees", method = RequestMethod.GET)
    @GetMapping("/coffees") // 위 코드와 동일. 보일러플레이트 코드를 줄이고 url 경로만 지정 가능하다. 가독성이 훨씬 높은 방법.
    Iterable<Coffee> getCoffees() {
        return coffees;
    }

    /**
     * 요청받은 id 값과 일치하는 항목이 있으면 해당 값이 포함된 Optional<Coffee> 를 반환.
     * 요청받은 id 값과 일치하는 항복이 없으면 비어있는 Optional<Coffee> 를 반환.
     *
     * @param id : URI 변수. @PathVariable 어노테이션이 달린 id 매개변수를 통해 getCoffeeById() 메서드에 전달.
     * @return
     */
    @GetMapping("/coffees/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable String id) {
        for (Coffee c : coffees) {
            if (c.getId().equals(id)) {
                return Optional.of(c);
            }
        }

        return Optional.empty();
    }

    /**
     * POST 로 등록할 리소스의 정보를 JSON 타입으로 제공하고 지정된 URI에 리소스를 생성한다.
     *
     * @param coffee : Spring Boot 의 Jackson 의존성으로 Json 정보를 Coffee 객체로 marshalling 해서 받는다.
     * @return Coffee : 동일하게 객체정보를 Json 형식으로 unmarshalling 해서 응답한다.
     */
    @PostMapping("/coffees")
    Coffee postCoffee(@RequestBody Coffee coffee) {
        coffees.add(coffee);
        return coffee;
    }

    /**
     * IETF 의 http 문서에 의하면,
     * PUT 요청에서 기존 리소스가 있으면 리소스를 업데이트 하고,
     * 기존 리소스가 없으면 리소스를 새로 생성해야 한다.
     *
     * @param id
     * @param coffee
     * @return
     */
    @PutMapping("/coffees/{id}")
    Coffee putcoffee(@PathVariable String id, @RequestBody Coffee coffee) {
        int coffeeIndex = -1;

        for (Coffee c: coffees) {
            if (c.getId().equals(id)) {
                coffeeIndex = coffees.indexOf(c);
                coffees.set(coffeeIndex, coffee);
            }
        }

        return (coffeeIndex == -1) ? postCoffee(coffee) : coffee;   // 기존 리소스가 없으면 postCoffee 메서드 그대로 사용
    }

    @DeleteMapping("/coffees/{id}")
    void deleteCoffee(@PathVariable String id) {
        coffees.removeIf(c -> c.getId().equals(id));
    }
}
