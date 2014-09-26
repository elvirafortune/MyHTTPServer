import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Elvira on 26.09.14.
 */
public class HTTPResponse {

    private static final String DEFAULT_FILES_DIR = "D:\\технопарк\\HighLoad\\MyHTTPServer\\src\\main\\static_files";

    HTTPResponse(){}

    public OutputStream getResponse(OutputStream out, InputStream in, Socket socket) throws Throwable {
        try {
            String header = readHeader(in);
            String method = getMethodFromHeader(header);
            String path = getURIFromHeader(header);
            ResponseCodeInfo responseCodeInfo =  new ResponseCodeInfo();

            if (!method.equals("GET") && !method.equals("HEAD")){
                out.write(responseCodeInfo.getHeader(405, "", 0).getBytes());
            }
            if (path.contains("../") && (!new File(path).getCanonicalPath().contains(DEFAULT_FILES_DIR))){
                out.write(responseCodeInfo.getHeader(403, "", 0).getBytes());
            } else {
                path = URLDecoder.decode(path);
                File file = new File(path);
                if (!file.exists()){
                    out.write(responseCodeInfo.getHeader(404, "", 0).getBytes());
                } else {
                    if (path.endsWith("/")){
                        path += "index.html";
                        file = new File(path);
                    }
                    if (!file.exists()){
                        out.write(responseCodeInfo.getHeader(403, "", 0).getBytes());
                        out.flush();
                    } else {
                        String typeParse[] = path.split("\\.");
                        String type = typeParse[typeParse.length-1];
                        String mimetype = new MIMEtype().getMIMEtype(type);
                        System.out.println(path.toString());
                        out.write(responseCodeInfo.getHeader(200, mimetype, file.length()).getBytes());
                        if (method.equals("GET")){
                            Path pathForRead = Paths.get(path);
                            byte[] byteArray = Files.readAllBytes(pathForRead);
                            System.out.println(byteArray);
                            out.write(byteArray);
                        }
                        out.flush();
                    }

                }
            }
            socket.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return out;
    }

    private String readHeader(InputStream in) throws Throwable {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder builder = new StringBuilder();
        String ln = null;
        while (true) {
            ln = reader.readLine();
            if (ln == null || ln.isEmpty()) {
                break;
            }
            builder.append(ln + System.getProperty("line.separator"));
        }
        return builder.toString();
    }

    /**
     * Вытаскивает метод из заголовка сообщения от
     * клиента.
     */
    private String getMethodFromHeader(String header) {
        return header.split(" ", 0)[0];
    }

    /**
     * Вытаскивает идентификатор запрашиваемого ресурса из заголовка сообщения от
     * клиента.
     */
    private String getURIFromHeader(String header) {
        String uri = header.split(" ", 0)[1];
        int paramIndex = uri.indexOf("?");
        if (paramIndex != -1) {
            uri = uri.substring(0, paramIndex);
        }
        return DEFAULT_FILES_DIR + uri;
    }

    /**
     * Возвращает http заголовок ответа.
     */

}