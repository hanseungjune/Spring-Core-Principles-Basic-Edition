package com.example.study.beanfind;

import com.example.study.member.MemberRepository;
import com.example.study.member.MemberService;
import com.example.study.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다")
    void findBeanByTypeDuplicate() {
        assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다")
    void findBeanByNameDuplicate() {
        MemberRepository memberRepository = ac.getBean("memberRepository1",
                MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanByType() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("출력: " + key + " : " + beansOfType.get(key));
        }
        System.out.println("beansOfType: " + beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2);
    }

//    @Test
//    @DisplayName("이름 없이 타입만으로 조회")
//    void findBeanByType() {
//        MemberService memberService = ac.getBean(MemberService.class);
//        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
//    }

//    @Test
//    @DisplayName("구체 타입으로 조회")
//    void findBeanByName2() {
//        MemberServiceImpl memberService = ac.getBean("memberService",
//                MemberServiceImpl.class);
//        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
//    }

    @Test
    @DisplayName("빈 이름으로 조회X")
    void findBeanByNameX() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("xxxxx", MemberService.class));
    }

    @Configuration
    static class SameBeanConfig {

        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
}
