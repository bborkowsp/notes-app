package com.notesapp.notesapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTOTPCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    String secretKey;

    int verificationCode;

    @ElementCollection(fetch = FetchType.EAGER)
    @ToString.Exclude
    List<Integer> scratchCodes;
}
