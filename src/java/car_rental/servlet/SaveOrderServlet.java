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
@WebServlet(name = "SaveOrderServlet", urlPatterns = {"/SaveOrderServlet"})
public class SaveOrderServlet extends HttpServlet {

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
        String url = VIEW_CART_PAGE;
        String button = request.getParameter("btnAction");
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                UserDTO user = (UserDTO) session.getAttribute("USER");
                if (user != null) {
                    if (button.equals("+")) {
                        String carName = request.getParameter("txtCarName");
                        String carID = request.getParameter("txtCarID");
                        int id = Integer.parseInt(carID);
                        CarDAO dao = new CarDAO();
                        int quantity = dao.checkQuantity(id);
                        List<CartDTO> listOrder = (List<CartDTO>) session.getAttribute("CART");
                        int amount;
                        float totalPrice = 0;
//                    System.out.println(quantity);
                        for (int i = 0; i < listOrder.size(); i++) {
                            if (listOrder.get(i).getCarID() == id) {
                                amount = listOrder.get(i).getAmount() + 1;
                                if (amount <= quantity) {
                                    listOrder.get(i).setAmount(amount);
                                    listOrder.get(i).setTotal(amount * listOrder.get(i).getPrice());
                                } else {
                                    request.setAttribute("ERROR", "Number amount of " + carName + " is out of stock !!");
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < listOrder.size(); i++) {
                            totalPrice = totalPrice + listOrder.get(i).getTotal();
                        }
                        session.setAttribute("TOTAL", totalPrice);
                        session.setAttribute("CART", listOrder);
                        url = VIEW_CART_PAGE;
                    }
                    if (button.equals("-")) {
                        String carID = request.getParameter("txtCarID");
                        int id = Integer.parseInt(carID);
                        List<CartDTO> listOrder = (List<CartDTO>) session.getAttribute("CART");
                        int amount;
                        float totalPrice = 0;
                        for (int i = 0; i < listOrder.size(); i++) {
                            if (listOrder.get(i).getCarID() == id) {
                                amount = listOrder.get(i).getAmount() - 1;
                                if (amount > 0) {
                                    listOrder.get(i).setAmount(amount);
                                    listOrder.get(i).setTotal(amount * listOrder.get(i).getPrice());
                                }
                            }
                        }
                        for (int i = 0; i < listOrder.size(); i++) {
                            totalPrice = totalPrice + listOrder.get(i).getTotal();
                        }
                        session.setAttribute("TOTAL", totalPrice);
                        session.setAttribute("CART", listOrder);
                        url = VIEW_CART_PAGE;
                    }
                    if (button.equals("Delete")) {
                        String carID = request.getParameter("txtCarID");
                        int id = Integer.parseInt(carID);
                        float totalPrice = 0;
                        List<CartDTO> listOrder = (List<CartDTO>) session.getAttribute("CART");
                        for (int i = 0; i < listOrder.size(); i++) {
                            if (listOrder.get(i).getCarID() == id) {
                                listOrder.remove(i);
                            }
                        }
                        for (int i = 0; i < listOrder.size(); i++) {
                            totalPrice = totalPrice + listOrder.get(i).getTotal();
                        }
                        session.setAttribute("TOTAL", totalPrice);
                        session.setAttribute("CART", listOrder);
                        url = VIEW_CART_PAGE;
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
