import view.MainFrame;

public class EntryPoint implements Runnable {

    public static void main(String[] args) {
        new EntryPoint().run();
    }

    public void run() {
        new MainFrame();
    }
}
