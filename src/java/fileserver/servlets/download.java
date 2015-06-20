package fileserver.servlets;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "download", urlPatterns = {"/download"})
public class download extends HttpServlet {

      protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Error</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Ошибка. Пустой параметр</h1>");
//            out.println("</body>");
//            out.println("</html>"); 
//        }

    }

 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
            response.setContentType("text/html;charset=UTF-8");
  
    String file_name = request.getParameter("file_name");
   // response.reset();
  //  response.setContentType("application/x-download");
    response.setHeader("Content-Disposition", "attachment;filename="+file_name);
    request.getRequestDispatcher("/resources/files/"+file_name).forward(request, response);
      
    }

 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
