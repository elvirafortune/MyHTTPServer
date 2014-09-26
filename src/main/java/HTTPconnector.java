import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Elvira on 26.09.14.
 */
public class HTTPconnector implements Runnable{

        private Socket socket;
        private InputStream in;
        private OutputStream out;
        private static final String DEFAULT_FILES_DIR = "D:\\технопарк\\HighLoad\\try\\static_files";

        HTTPconnector(Socket socket) throws Throwable {
            this.socket = socket;
            this.in = socket.getInputStream();
            this.out = socket.getOutputStream();
        }

        public void run() {
            try {
                out = new HTTPResponse().getResponse(out, in , socket);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            System.err.println("Client processing finished");
        }

        /**
         * Считывает заголовок сообщения от клиента.
         */

  }
