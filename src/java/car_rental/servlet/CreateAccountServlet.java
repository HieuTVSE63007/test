/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import car_rental.dao.UserDAO;
import car_rental.dto.CreateNewAccountErrorDTO;

/**
 *
 * @author Truong Van Hieu
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {

    private final String LOGIN_PAGE = "Login.jsp";
    private final String CREATE_PAGE = "SignUp.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = CREATE_PAGE;
        try {
            String email = request.getParameter("txtEmail");
            String name = request.getParameter("txtName");
            String phone = request.getParameter("txtPhone");
            String address = request.getParameter("txtAddress");
            String password = request.getParameter("txtPassword");
            String confirmPassword = request.getParameter("txtConfirmPassword");

            UserDAO checkExist = new UserDAO();
            String isExistUser = checkExist.getUserName(email);
            CreateNewAccountErrorDTO error = new CreateNewAccountErrorDTO();
            boolean foundError = false;
            if (!email.trim().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
                error.setErrorEmail("Please input Email !");
                foundError = true;
            } 
//            if(email.trim().equals("")){
//                error.setErrorEmail("Please input Email !");
//                foundError = true;
//            }
            if (name.trim().equals("")) {
                error.setErrorName("Please input Name !");
                foundError = true;
            }
            if (!phone.trim().matches("([0-9])\\d+")) {
                error.setErrorPhone("Please input Password requires 10 number !!");
                foundError = true;
            } else if (phone.trim().length() != 10) {
                error.setErrorPhone("Please input Password requires 10 number !!");
                foundError = true;
            }
            if (address.trim().equals("")) {
                error.setErrorAddress("Address cannot null !!");
                foundError = true;
            }
            if (password.trim().equals("")) {
                error.setErrorPassword("Please input Password !");
                foundError = true;
            }
            if (!password.trim().equals(confirmPassword)) {
                error.setErrorConfirmPassword("Confirm Password much match Password !");
                foundError = true;
            }
            if (isExistUser != null) {
                error.setIsExistAccount("Account is exist !");
                foundError = true;
            }
            if (foundError) {
                request.setAttribute("ERROR", error);
                url = CREATE_PAGE;
            } else {
                UserDAO dao = new UserDAO();
                boolean result = dao.createNewAccount(email, password, name);
                if (result) {
                    boolean createDetail = dao.createNewAccountDetail(email, name, phone, address);
                    if (createDetail) {
                        url = LOGIN_PAGE;
                    } else {
                        url = CREATE_PAGE;
                    }
                } else {
                    url = CREATE_PAGE;
                }
            }

        } catch (NamingException ex) {
            log("CreateAccountServlet_NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("CreateAccountServlet_SQLException: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
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
