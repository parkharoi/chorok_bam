package org.delivery.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이 주소를 소유한 유저
    @ManyToOne(fetch = FetchType.LAZY) // 주소(Many) To 유저(One) 관계
    @JoinColumn(name = "user_id") // DB에 user_id 라는 이름의 컬럼으로 외래키 생성
    private User user;

    @Column(nullable = false)
    private String streetAddress; // 도로명 주소

    @Column(nullable = false)
    private String detailAddress; // 상세 주소

    @Column(nullable = false)
    private String zipCode; // 우편번호
}