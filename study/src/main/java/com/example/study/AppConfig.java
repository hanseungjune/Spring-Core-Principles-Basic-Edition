package com.example.study;

import com.example.study.discount.DiscountPolicy;
import com.example.study.discount.RateDiscountPolicy;
import com.example.study.member.MemberRepository;
import com.example.study.member.MemberService;
import com.example.study.member.MemberServiceImpl;
import com.example.study.member.MemoryMemberRepository;
import com.example.study.order.OrderService;
import com.example.study.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberService");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.memberRepository");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
//        return null;
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
