/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental.servlet;

import car_rental.dao.CarDAO;
import car_rental.dto.HistoryDetailDTO;
import car_rental.dto.UserDTO;
import car_rental.dto.historyDTO;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Truong Van Hieu
 */
@WebServlet(name = "HistoryServlet", urlPatterns = {"/HistoryServlet"})
public class HistoryServlet extends HttpServlet {

    private final String VIEW_ORDER_HISTORY = "ViewOrderHistory.jsp";
    private final String FEED_BACK_PAGE = "FeedBackCar.jsp";
    private final String HISTORY_DETAIL_PAGE = "HistoryDetai.jsp";

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
        String url = VIEW_ORDER_HISTORY;
        String button = request.getParameter("btnAction");
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                UserDTO user = (UserDTO) session.getAttribute("USER");
                if (user != null) {
                    if (button.equals("Search")) {
                        String name = request.getParameter("txtName");
                        String orderDate = request.getParameter("txtOrderDate");
                        CarDAO dao = new CarDAO();
                        dao.searchHistory(user.getUserID(), orderDate);
                        List<historyDTO> listHistory = dao.getListHistory();
                        request.setAttribute("HISTORY", listHistory);
                        url = VIEW_ORDER_HISTORY;
                    }
                    if (button.equals("Delete")) {
                        int historyID = Integer.parseInt(request.getParameter("txtHistoryID"));
                        CarDAO dao = new CarDAO();
                        boolean result = dao.deleteHistory(historyID);
                        if (result) {
                            url = "HistoryServlet?txtName="
                                    + "&txtOrderDate="
                                    + "&btnAction=Search";
                        }
                    }
                    if(button.equals("Detail")){
                        int historyID = Integer.parseInt(request.getParameter("txtHistoryID"));
                        CarDAO dao = new CarDAO();
                        dao.getHistoryDetail(historyID);
                        List<HistoryDetailDTO> listHistoryDetail = dao.getListHisDetail();
                        request.setAttribute("DETAIL", listHistoryDetail);
                        url = HISTORY_DETAIL_PAGE;
                    }
                    if(button.equals("Feedback")){
                        url = FEED_BACK_PAGE;
                    }
                } else {
                    url = "SearchServlet?txtSearchCarName="
                            + "&cbCategory="
                            + "&txtRentalData="
                            + "&txtReturnDate="
                            + "&txtAmount="
                            + "&btnAction=Search";
                }
            }

        } catch (NamingException ex) {
            log("HistoryServlet_NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("HistoryServlet_SQLException: " + ex.getMessage());
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
