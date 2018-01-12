import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author liqi
 * @created on 2017/12/19
 **/
@WebServlet(name = "Result")
public class Result extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        File f = new File("E:\\submit2\\web\\submit\\test");
        String []image=f.list();
        HttpSession sess = request.getSession(true);
        String impath = image[0];
        response.setContentType("image/jpg");
        String path = new String("E:\\submit2\\web\\submit\\results_box\\"+impath);
        BufferedImage bi = ImageIO.read(new File(path));
        ImageIO.write(bi, "jpg", response.getOutputStream());
        /** try {
         PrintWriter out = response.getWriter();
         out.println("<!DOCTYPE html>");
         out.println("<html>");
         out.println("<head>");
         out.println("<title>Result</title>");
         out.println("<style>");
         out.println(".top{border:0px solid black;width:600px;height:400px;margin:0px auto}");
         out.println(".imdiv{float:left;width:200px;height:200px;}");
         out.println("</style>");
         out.println("</head>");
         out.println("<body>");
         out.println("<div class=\"top\">");
         out.println("<div class=\"imdiv\">");
         out.println("<img src=\"submit/test/" +impath + "\"" + "width=200px" + "/>");
         out.println("</div>");
         out.println("<div class=\"imdiv\">");
         out.println("<img src=\"submit/results_box/" +impath+ "\"" + "width=200px" + "/>");
         out.println("</div>");
         out.println("</div>");
         out.println("</body>");
         out.println("</html>");
         out.flush();
         out.close();
         }catch (Exception e){}**/
        File file = new File("E:\\submit2\\web\\submit\\test\\"+impath);
        if(file.exists()){
            file.delete();
        }
        File file1 = new File("E:\\submit2\\web\\submit\\results_box\\"+impath);
        if(file1.exists()){
            file1.delete();
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
