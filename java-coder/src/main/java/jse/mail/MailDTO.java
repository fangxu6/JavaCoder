package jse.mail;

import lombok.Data;

import java.util.Date;

/**
 * className: MailDTO
 * package: jse.mail
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2024/4/3 11:19
 */
@Data
public class MailDTO {
    private String from;
    private String replyTo;
    private String[] to;
    private String[] cc;
    private String[] bcc;
    private Date sentDate;
    private String subject;
    private String text;
    private String[] filenames;
}
