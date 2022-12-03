import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Calendar;
import java.util.Date;

///**
// * @author fangxu
// * on date:2022/1/7
// */
//public class Test {
//    File file = new File("D:\\Downloads\\11.txt");
//    URL url;
//
//    {
//        try {
//            url = file.toURI().toURL();
//            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//    }
//
////    File var15= new File(***);
////    URL var10=var15.toURI().toURL();
////    urlclassloader var11=new URLClassLoader(new URL[]{var10});
//}

class Test {
    public static void main(String[] args) {
        File file = new File("D:\\Downloads\\11.txt");
        URL url;
        try {
            url = file.toURI().toURL();
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

//        InputStream inputStream = new ByteArrayInputStream();
//        inputStream.read()
//        ByteArrayInputStream byteArrayInputStream = new
    }

    int calculateDaysBetweenDates(Date startDate, Date endDate) {
        int daysBetween = 0;
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        return 0;

    }

    // find all images without alternate text
    // and give them a red border
    void process() {
        String[] images = getImages();
        for (int i = 0; i < images.length; i++) {
            String alt = getAltText(images[i]);
            if (alt == null || alt.equals("")) {
                addRedBorder(images[i]);
            }
        }
    }

    private void addRedBorder(String image) {
    }

    private String getAltText(String image) {
        return null;
    }

    private String[] getImages() {
        return null;
    }
}
