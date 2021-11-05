/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental.servlet;

import car_rental.dto.CartDTO;
import car_rental.dto.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(name = "RenCarServlet", urlPatterns = {"/RenCarServlet"})
public class RentCarServlet extends HttpServlet {

    private final String LOGIN_PAGE = "Login.jsp";

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
        String url = LOGIN_PAGE;
        String button = request.getParameter("btnAction");
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                UserDTO user = (UserDTO) session.getAttribute("USER");
                if (user != null) {
                    if (button.equals("Rent Car")) {
                        if (user.getRole().equals("User")) {
                            int quantity = Integer.parseInt(request.getParameter("txtQuantity"));
                            int carID = Integer.parseInt(request.getParameter("txtCarID"));
                            String carName = request.getParameter("txtCarName");
                            String carType = request.getParameter("txtCategory");
                            String txtPrice = request.getParameter("txtPrice");
                            float price = Float.parseFloat(txtPrice);
                            List<CartDTO> listOrder = (List<CartDTO>) session.getAttribute("CART");
//                            System.out.println(carID + " - " + carName + " - " + carType + " - " + price);
                            boolean found = false;
                            int amount;
                            float totalPrice = 0;
//                            totalPrice = (float) session.getAttribute("TOTAL");

                            if (listOrder == null) {
//                                System.out.println("null");
                                listOrder = new ArrayList<>();
                            }
                            if (listOrder.size() > 0) {
                                for (int i = 0; i < listOrder.size(); i++) {
                                    if (listOrder.get(i).getCarID() == carID) {
                                        amount = listOrder.get(i).getAmount() + 1;
                                        if (amount <= quantity) {
                                            listOrder.get(i).setAmount(amount);
                                            listOrder.get(i).setTotal(amount * listOrder.get(i).getPrice());
                                            found = true;
                                            break;
                                        } else {
                                            request.setAttribute("ERROR", "Number amount of " + carName + " is Out of Stock !!");
                                            found = true;
                                            break;
                                        }
                                    }
                                }
                            }
//                            for (int i = 0; i < listOrder.size(); i++) {
//                                totalPrice += listOrder.get(i).getPrice();
//                            }
                            if (!found) {
                                CartDTO dto = new CartDTO(carID, carName, carType, 1, price);
                                listOrder.add(dto);
                            }
                            for (int i = 0; i < listOrder.size(); i++) {
//                                System.out.println(i + " - " + listOrder.get(i).getCarName()
//                                        + " - " + listOrder.get(i).getAmount()
//                                        + " - " + listOrder.get(i).getTotal());
                                totalPrice = totalPrice + listOrder.get(i).getTotal();
                            }
//                            System.out.println(totalprice);
//                            System.out.println("--------------------------");
                            session.setAttribute("TOTAL", totalPrice);
                            session.setAttribute("CART", listOrder);
                            url = "SearchServlet?txtSearchCarName="
                                    + "&cbCategory="
                                    + "&txtRentalData="
                                    + "&txtReturnDate="
                                    + "&txtAmount="
                                    + "&btnAction=Search";
                        } else {
                            request.setAttribute("ERROR", "Must be login as User role to rent car !!");
                            url = "SearchServlet?txtSearchCarName="
                                    + "&cbCategory="
                                    + "&txtRentalData="
                                    + "&txtReturnDate="
                                    + "&txtAmount="
                                    + "&btnAction=Search";
                        }
                    }
                } // end of if user != null 
                else {
                    url = LOGIN_PAGE;
                }
            }
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
