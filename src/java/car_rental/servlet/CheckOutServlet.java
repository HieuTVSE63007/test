/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental.servlet;

import car_rental.dao.CarDAO;
import car_rental.dto.CartDTO;
import car_rental.dto.UserDTO;
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
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    private final String SEARCH_CAR_PAGE = "SearchCar.jsp";
    private final String VIEW_CART_PAGE = "ViewCart.jsp";

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
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                List<CartDTO> listOrder = (List<CartDTO>) session.getAttribute("CART");
                if (listOrder != null) {
                    UserDTO user = (UserDTO) session.getAttribute("USER");
                    float totalPrice = (float) session.getAttribute("TOTAL");
                    CarDAO dao = new CarDAO();
                    String discount = request.getParameter("txtDiscount");

                    if (discount.trim().equals("")) {
                        boolean result = dao.checkOut(user.getUserID(), totalPrice, "none");
                        if (result) {
                            int id = dao.getHistoryID();
                            for (int i = 0; i < listOrder.size(); i++) {
                                System.out.println(listOrder.get(i).getCarID()
                                        + listOrder.get(i).getCarName()
                                        + user.getUserID()
                                        + listOrder.get(i).getAmount()
                                        + listOrder.get(i).getTotal());
                                boolean resultDetail = dao.checkOutDetail(
                                        listOrder.get(i).getCarID(),
                                        listOrder.get(i).getCarName(),
                                        id,
                                        listOrder.get(i).getAmount(),
                                        listOrder.get(i).getTotal());
                                if (!resultDetail) {
                                    break;
                                }
                            }
                            session.removeAttribute("CART");
                            url = SEARCH_CAR_PAGE;
                        }
                    }
                    if (!discount.trim().equals("")) {
                        String check = dao.getDiscount(discount);
                        if (check == null) {
                            request.setAttribute("ERROR", "Discount code is expiry !!");
                            url = VIEW_CART_PAGE;
                        } else{
                            boolean result = dao.checkOut(user.getUserID(), totalPrice, check);
                            if (result) {
                                int id = dao.getHistoryID();
                                for (int i = 0; i < listOrder.size(); i++) {
                                    System.out.println(listOrder.get(i).getCarID()
                                            + listOrder.get(i).getCarName()
                                            + user.getUserID()
                                            + listOrder.get(i).getAmount()
                                            + listOrder.get(i).getTotal());
                                    boolean resultDetail = dao.checkOutDetail(
                                            listOrder.get(i).getCarID(),
                                            listOrder.get(i).getCarName(),
                                            id,
                                            listOrder.get(i).getAmount(),
                                            listOrder.get(i).getTotal());
                                    if (!resultDetail) {
                                        break;
                                    }
                                }
                                session.removeAttribute("CART");
                                url = SEARCH_CAR_PAGE;
                            }
                        }
                    }
                    String check = dao.getDiscount(discount);
                    if (check == null) {
                        request.setAttribute("ERROR", "Discount code is expiry !!");
                        url = VIEW_CART_PAGE;
                    } else if (check != null) {
                        boolean result = dao.checkOut(user.getUserID(), totalPrice, check);
                        if (result) {
                            int id = dao.getHistoryID();
                            for (int i = 0; i < listOrder.size(); i++) {
                                System.out.println(listOrder.get(i).getCarID()
                                        + listOrder.get(i).getCarName()
                                        + user.getUserID()
                                        + listOrder.get(i).getAmount()
                                        + listOrder.get(i).getTotal());
                                boolean resultDetail = dao.checkOutDetail(
                                        listOrder.get(i).getCarID(),
                                        listOrder.get(i).getCarName(),
                                        id,
                                        listOrder.get(i).getAmount(),
                                        listOrder.get(i).getTotal());
                                if (!resultDetail) {
                                    break;
                                }
                            }
                            session.removeAttribute("CART");
                            url = SEARCH_CAR_PAGE;
                        }
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
            log("SaveOrderServlet_NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("SaveOrderServlet_SQLException: " + ex.getMessage());
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
