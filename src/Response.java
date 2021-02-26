import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class Response {

    private static final int BUFFER_SIZE = 1024;

    private Request request;
    private OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        boolean errorFlag = true;
        if(request.getUri()!=null){
            String para = request.getUri();
            Map<String, String> paramMap = new LinkedHashMap<String, String>();
            paramMap = util.getUrlPramNameAndValue(para);
            Integer sum = 0;
            Integer a = Integer.valueOf(paramMap.get("a"));
            Integer b = Integer.valueOf(paramMap.get("b"));
            String type = para.substring(1,para.indexOf("?"));
            System.out.println(type.equals("add"));
            switch (type) {
                case "add":
                    sum = a+b;
                    break;
                case "mult":
                    sum = a*b;
                    break;
            }
            String successMessage = "HTTP/1.1 404  error\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: 23\r\n" +
                    "\r\n" +
                    "<h1>"+sum+"</h1>";
            output.write(successMessage.getBytes());
            errorFlag = false;
        }
        if(errorFlag) {
            // 路径错误
            String errorMessage = "HTTP/1.1 404  error\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: 23\r\n" +
                    "\r\n" +
                    "<h1>请检查参数是否错误</h1>";
            output.write(errorMessage.getBytes());
        }
        if (fis != null) {
            fis.close();
        }
    }
}
