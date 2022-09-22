package template.callback;

/**
 * @author fangxu
 * on date:2022/9/22
 */
public class AClass {
    public static void main(String[] args) {
        BClass b = new BClass();
        b.process(new ICallBack() {
            //回调对象
            @Override
            public void methodToCallback() {
                System.out.println("Call back me.");
            }
        });
    }
}
