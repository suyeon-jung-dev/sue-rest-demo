package com.sue.suerestdemo.domain;

import java.util.UUID;

public class Coffee {

    private final String id;
    private String name;

    public Coffee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Coffee 인스턴스 생성시 id 매개변수 없는 경우, randomUUID 를 제공
     * @param name
     */
    public Coffee(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public String getId() {
        return id;
    }

    // 접근자 메서드 accessor method
    public String getName() {
        return name;
    }

    // 변경자 메서드 mutator method
    public void setName(String name) {
        this.name = name;
    }
}
