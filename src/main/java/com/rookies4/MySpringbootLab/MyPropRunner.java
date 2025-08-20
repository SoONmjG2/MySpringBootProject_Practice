package com.rookies4.MySpringbootLab;

import com.rookies4.MySpringbootLab.config.MyEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@ConditionalOnBean(MyEnvironment.class)
@Component
public class MyPropRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyPropRunner.class);

    private final MyPropProperties props;
    private final MyEnvironment env;

    public MyPropRunner(MyPropProperties props, MyEnvironment env) {
        this.props = props;
        this.env = env;
    }

    @Override
    public void run(String... args) {
        logger.debug("(Props) myprop.username = {}", props.getUsername());
        logger.debug("(Props) myprop.port = {}", props.getPort());
        logger.info("현재 프로필 모드 = {}", env.getMode());
    }
}
