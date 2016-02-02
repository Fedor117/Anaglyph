import view.MainFrame;

/**
 * Created by Fedor on 02.02.2016.
 */
public class EntryPoint implements Runnable {

    public static void main(String[] args) {
        new EntryPoint().run();
    }

    public void run() {
        new MainFrame();
    }
}
