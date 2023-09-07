package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppCommandRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(AppCommandRunner.class);
    @Override
    public void run(String... args)  {
        logger.info("pkslow commandLine runner");

    }
}
