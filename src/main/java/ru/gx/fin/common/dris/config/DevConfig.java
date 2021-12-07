package ru.gx.fin.common.dris.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig extends CommonConfig {
}
