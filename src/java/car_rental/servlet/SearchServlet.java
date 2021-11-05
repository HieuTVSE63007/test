/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental.servlet;

import car_rental.dao.CarDAO;
import car_rental.dto.CarDTO;
import car_rental.dto.CategoryDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Truong Van Hieu
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private final String LOGIN_PAGE = "Login.jsp";
    private final String SEARCH_CAR_PAGE = "SearchCar.jsp";
    private final String VERIFY_PAGE = "Verify.jsp";

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
        String url = SEARCH_CAR_PAGE;
        String button = request.getParameter("btnAction");
        try {
            if (button.equals("Login")) {
                url = LOGIN_PAGE;
            }
            if (button.equals("Search")) {
                String carName = request.getParameter("txtSearchCarName");
                String category = request.getParameter("cbCategory");
                String rentalDate = request.getParameter("txtRentalData");
                String returnDate = request.getParameter("txtReturnDate");
                String amount = request.getParameter("txtAmount");
                int quantity;
                if(amount.trim().equals("")){
                    quantity = Integer.MAX_VALUE;
                }else{
                    quantity = Integer.parseInt(amount);
                }
                
                CarDAO carDAO = new CarDAO();
                carDAO.searchListCategory();
                carDAO.searchCar(carName, category, rentalDate, returnDate, quantity);
                List<CategoryDTO> listCategory = carDAO.getListCategory();
                List<CarDTO> listCar = carDAO.getListCar();
                request.setAttribute("LISTCAR", listCar);
                request.setAttribute("DATA", listCategory);
                url = SEARCH_CAR_PAGE;
            }
            if(button.equals("Verify")){
                url = VERIFY_PAGE;
            }
        } catch (NamingException ex) {
            log("SearchServlet_NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("SearchServlet_SQLException: " + ex.getMessage());
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
