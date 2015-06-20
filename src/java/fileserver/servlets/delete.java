package fileserver.servlets;

import fileserver.beans.DataAccess;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "delete", urlPatterns = {"/delete"})
public class delete extends HttpServlet {
    
@EJB DataAccess dataAccess;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
    
             String file_name = request.getParameter("file_name");
             Integer id = Integer.parseInt(request.getParameter("id"));
             
             File del_file = new File(getServletContext().getRealPath("/resources/files/"+file_name));
             
             
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet delete</title>");            
//            out.println("</head>");
//            out.println("<body>"); 
             
             if (del_file.exists())
             { 
                 
                 del_file.delete();  // удаляем сам файл
           //  out.println("Файл удален: <br>" + del_file.getPath() +" ");
              
                // удаляем файл в БД
             dataAccess.deleteFileById(id);
             response.sendRedirect(request.getContextPath());
             }
             else{ 
                 
             out.println("<h2>Файл не найден </h2>"); 
             out.println("<br/>"+del_file.getPath());
             
             }
            
        
            out.println("<p/><a href='./'><<На главную</a>");
            out.println("</body>");
            out.println("</html>");

            
            
        }
    }

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
   
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
