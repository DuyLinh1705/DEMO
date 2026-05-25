/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controllers;

import entity.Patient;
import DAO.PatientDAO;
        
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author admin
 */

public class PatientController extends HttpServlet {
    
    private PatientDAO patientDAO;

    public void init() {
        patientDAO = new PatientDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Giúp không lỗi font tiếng Việt khi submit form
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertPatient(request, response);
                    break;
                case "/delete":
                    deletePatient(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updatePatient(request, response);
                    break;
                default:
                    listPatient(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listPatient(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Patient> listPatient = patientDAO.selectAllPatients();
        request.setAttribute("listPatient", listPatient);
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/patient_list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/patient_form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Patient existingPatient = patientDAO.selectPatient(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/patient_form.jsp");
        request.setAttribute("patient", existingPatient);
        dispatcher.forward(request, response);
    }

    private void insertPatient(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Patient newPatient = new Patient(
            request.getParameter("username"),
            request.getParameter("password"),
            request.getParameter("fullname"),
            request.getParameter("gender"),
            request.getParameter("phone"),
            request.getParameter("email"),
            Date.valueOf(request.getParameter("dob"))
        );
        patientDAO.insertPatient(newPatient);
        response.sendRedirect("list");
    }

    private void updatePatient(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Patient patient = new Patient(
            Integer.parseInt(request.getParameter("id")),
            request.getParameter("username"),
            request.getParameter("password"),
            request.getParameter("fullname"),
            request.getParameter("gender"),
            request.getParameter("phone"),
            request.getParameter("email"),
            Date.valueOf(request.getParameter("dob"))
        );
        patientDAO.updatePatient(patient);
        response.sendRedirect("list");
    }

    private void deletePatient(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        patientDAO.deletePatient(id);
        response.sendRedirect("list");
    }
}

