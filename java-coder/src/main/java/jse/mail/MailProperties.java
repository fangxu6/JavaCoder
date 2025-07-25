package jse.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * className: MailProperties
 * package: jse.mail
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2024/4/3 11:18
 */
@Validated
@Component
@ConfigurationProperties(prefix = "mail")
public class MailProperties {
    private String domain;
    private String from;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
