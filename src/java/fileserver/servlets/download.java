package fileserver.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@WebServlet(name = "download", urlPatterns = {"/download"})
public class download extends HttpServlet {

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
       
        try (PrintWriter out = response.getWriter()) {
 
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet test</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>File name: " + request.getParameter("file_name") + "</h1><br/>");
//            out.println("<br/>Encoding request: " + request.getCharacterEncoding() );
//            out.println("<br/>Request locale: " + request.getLocale() );
//            out.println("<br/>Encoding response: " + response.getCharacterEncoding() );
//            out.println("<br/>Response locale: " + response.getLocale() );
//            
//            out.println("</body>");
//            out.println("</html>");
            
            String file_name = request.getParameter("file_name");
            
            String file_name_encoded = URLEncoder.encode(file_name, "UTF-8");  // кодируем имя файла в RFC2231  чтобы НТТП заголовок Content-Disposition понимал русские буквы
         //   String file_name2 = "file343.xlsx";
        //  response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment;filename="+file_name_encoded);
        request.getRequestDispatcher("/resources/files/" + file_name).forward(request, response);
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
