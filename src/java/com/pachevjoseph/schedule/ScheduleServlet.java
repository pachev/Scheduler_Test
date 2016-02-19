/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pachevjoseph.schedule;

import com.opencsv.CSVWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author drawven
 */
@WebServlet(name = "ScheduleServlet", urlPatterns = {"/ScheduleServlet"})
public class ScheduleServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ScheduleServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ScheduleServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        
        String title = request.getParameter("title");
        int starttime = Integer.parseInt(request.getParameter("starttime"));
        int endtime = Integer.parseInt(request.getParameter("endtime"));
        int credit = Integer.parseInt(request.getParameter("credit"));
        String[] days = request.getParameterValues("day");

        SchoolSchedule schedule = (SchoolSchedule) request.getSession(true).getAttribute("schoolschedule");
        if (schedule == null) {
            schedule = new SchoolSchedule();
        }

        if (days != null) {
            for (int i = 0; i < days.length; i++) {
                String dayString = days[i];
                int day;
                if (dayString.equalsIgnoreCase("SUN")) {
                    day = 0;
                } else if (dayString.equalsIgnoreCase("MON")) {
                    day = 1;
                } else if (dayString.equalsIgnoreCase("tue")) {
                    day = 2;
                } else if (dayString.equalsIgnoreCase("wed")) {
                    day = 3;
                } else if (dayString.equalsIgnoreCase("thu")) {
                    day = 4;
                } else if (dayString.equalsIgnoreCase("fri")) {
                    day = 5;
                } else {
                    day = 6;
                }

                SchoolClass course = new SchoolClass(title, starttime, endtime, day, credit);
                schedule.addClass(course);

            }
        }

        PrintWriter writer = new PrintWriter("usertime.txt"); 
        if (endtime > 21) {  //making sure I account for late classes when I go to calculate sleep
            writer.println(endtime);
        } else {
            writer.println(21);
        }
        writer.close();
        writer = new PrintWriter(new BufferedWriter(new FileWriter("credit.txt", true)));
        writer.println(credit); //keeps track of credit hours so I can calculate study time
        writer.println(" ");
        writer.close();
        
        
        CSVWriter clist = new CSVWriter(new FileWriter("clist.csv")); //using a custom library to save arrayList to csv.
        
        for (SchoolClass string : schedule.getAClasses()) {
          String[] allSchedule = string.toString().split(",");
          clist.writeNext(allSchedule);
                    
        }
        
	clist.close();
        

        request.getSession().setAttribute("schoolschedule", schedule);

        getServletContext().getRequestDispatcher("/Schedule.jsp").forward(request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "ScheduleServlet";
    }// </editor-fold>

}
