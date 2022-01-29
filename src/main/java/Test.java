import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author fangxu
 * on date:2022/1/7
 */
public class Test {
    File file = new File("D:\\Downloads\\11.txt");
    URL url;

    {
        try {
            url = file.toURI().toURL();
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

//    File var15= new File(***);
//    URL var10=var15.toURI().toURL();
//    urlclassloader var11=new URLClassLoader(new URL[]{var10});
}
