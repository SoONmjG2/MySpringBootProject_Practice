package com.rookies4.MySpringbootLab.config;

import lombok.Getter;
import lombok.ToString;
import lombok.Builder;

@Getter
@ToString
@Builder
public class MyEnvironment {
    private final String mode; // 불변
}
