import java.io.*;
import java.util.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;


/**
 * @author liqi
 * @created on 2017/12/15
 **/
@WebServlet(name = "Servlet")
public class Servlet extends HttpServlet {
    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024*1024;
    private int maxMemSize = 6 * 1024*1024;
    private File file ;
    @Override
    public void init( ){
        // Get the file location where it would be stored.
        filePath = getServletContext().getInitParameter("file-upload");
        //System.out.println(filePath);
    }
    void  doPython(){
        try {
            //需传入的参数
            //设置命令行传入参数
            //String[] arg = new String[]{"python E:\\submit\\web\\WEB-INF\\submit\\submit.py"};
            Process pr = Runtime.getRuntime().exec("python E:\\submit2\\web\\submit\\submit.py");
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String string = null;
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter( );
        HttpSession sess = request.getSession(true);
        if( !isMultipart ){
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>No file uploaded</p>");
            out.println("</body>");
            out.println("</html>");
            return;
        }
        try{
            DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置最大的文件接收大小
        factory.setSizeThreshold(maxMemSize);
        // 超过最大接收时的保存位置
        factory.setRepository(new File("E:\\submit2\\web\\submit\\test data"));
        ServletFileUpload upload = new ServletFileUpload(factory);
        //上传文件
        upload.setSizeMax( maxFileSize );

            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);
            // Process the uploaded file items
            Iterator i = fileItems.iterator();
            while ( i.hasNext () )
            {
                FileItem fi = (FileItem)i.next();
                if ( !fi.isFormField () )
                {
                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();
                    // Write the file
                    if( fileName.lastIndexOf("\\") >= 0 ){
                        file = new File( filePath +
                                fileName.substring( fileName.lastIndexOf("\\"))) ;
                    }else{
                        file = new File( filePath +
                                fileName.substring(fileName.lastIndexOf("\\")+1)) ;
                    }
                    fi.write( file ) ;
                }
            }
            doPython();
            String ss = new String (file.getPath());
            String ipath =ss.substring(ss.lastIndexOf("\\")-11);
            String outpath = ss.substring(ss.lastIndexOf("\\")+1);
            File f = new File("E:\\submit2\\web\\submit\\results_box\\"+outpath);
            if(f.exists()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Result</title>");
                out.println("<style>");
                out.println(".top{border:5px solid black;width:400px;height:100px;margin:0px auto}");
                out.println(".imdiv{float:left;width:200px;height:200px;}");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<center>");
                out.println("<form action=\"Result\" method=\"POST\" Enctype=\"multipart/form-data\">");
                out.println("<div class=\"top\">");
                out.println("<p>Upload successfully Please wait and click \"View\"</p>");
                out.println("</br>");
                out.println("<input type=\"submit\" value=\"View\" class=\"button\"");
                out.println("</div>");
                out.println("</form>");
                out.println("</center>");
                out.println("</body>");
                out.println("</html>");
                //out.flush();
                out.close();
            }
        }catch(Exception ex) {}
    }
}
