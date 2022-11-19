package ij;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    // for EDT exceptions
    public void handle(Throwable thrown) {
        handleException(Thread.currentThread().getName(), thrown);
    }

    // for other uncaught exceptions
    public void uncaughtException(Thread thread, Throwable thrown) {
        handleException(thread.getName(), thrown);
    }

    protected void handleException(String tname, Throwable e) {
        if (Macro.MACRO_CANCELED.equals(e.getMessage()))
            return;
        CharArrayWriter caw = new CharArrayWriter();
        PrintWriter pw = new PrintWriter(caw);
        e.printStackTrace(pw);
        String s = caw.toString();
        if (s!=null && s.contains("ij.")) {
            if (IJ.getInstance()!=null)
                s = IJ.getInstance().getInfo()+"\n"+s;
            IJ.log(s);
        }
    }
}
