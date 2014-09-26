import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Elvira on 26.09.14.
 */
public class ResponseCodeInfo {

    ResponseCodeInfo(){}

    public String getHeader(int code, String type, long length) throws Throwable{
        String result = "HTTP/1.1 " + code + getAnswer(code);
        result += "\r\n";
        result += "Date: ";
        Date date = new Date();
        DateFormat dateFormat =
                new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        result += dateFormat.format(date) + "\r\n";
        result += "Server: server\r\n";
        result += "Content-Length: " + length + "\r\n";
        result += "Content-Type: " + type + "\r\n";
        result += "Connection: close\r\n\r\n";
        System.out.println(result);
        return result;
    }

    String getAnswer(int code) {
        switch (code) {
            case 200:
                return " OK";
            case 404:
                return " Not Found";
            case 405:
                return " Method Not Allowed";
            case 403:
                return " Forbidden";
            default:
                return " Internal Server Error";
        }
    }
}
