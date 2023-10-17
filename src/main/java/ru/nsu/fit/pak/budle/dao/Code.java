package ru.nsu.fit.pak.budle.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_codes")
@Builder
@AllArgsConstructor
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneNumber;
    private String code;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private CodeType type;

    public Code() {

    }
}
