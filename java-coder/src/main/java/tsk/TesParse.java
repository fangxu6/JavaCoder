package tsk;

import java.io.IOException;

/**
 * className: TesParse
 * package: tsk
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2025/7/25 8:59
 */
public class TesParse {
    public static void main(String[] args) throws IOException {
        Tsk tsk = new Tsk("C:\\Users\\fangx\\Desktop\\tsk\\FFY633.1-19");
        tsk.read();
    }
}
