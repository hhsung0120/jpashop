package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class MemberForm {

    @NotEmpty(message = "이름 없음")
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
