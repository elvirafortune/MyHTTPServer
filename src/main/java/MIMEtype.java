/**
 * Created by Elvira on 26.09.14.
 */
public class MIMEtype {
    MIMEtype(){}

    public String getMIMEtype(String type){
        if (type.equals("html")){
           return type = "text/html";
        } else if (type.equals("css")){
            return type = "text/css";
        } else if (type.equals("js")){
            return type = "text/javascript";
        } else if (type.equals("jpg") || type.equals("jpeg")){
            return type = "image/jpeg";
        } else if (type.equals("png")){
            return type = "image/png";
        } else if (type.equals("gif")){
            return  type = "image/gif";
        } else if (type.equals("swf")){
            return type = "application/x-shockwave-flash";
        } else {
            return type = "text/plain";
        }
    }
}
