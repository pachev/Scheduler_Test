/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pachevjoseph.schedule;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author drawven
 */
public class GenServlet extends HttpServlet {

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
            out.println("<title>Servlet GenServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GenServlet at " + request.getContextPath() + "</h1>");
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

        double studyFormula = 2;
        Random random = new Random();

        FileReader fr = new FileReader("usertime.txt");  // reading in the times that i've saved in a text file
        FileReader fr2 = new FileReader("credit.txt");   // reading in total credit hours in order to calculate study time
        Scanner scan = new Scanner(fr);
        int sleepStart = scan.nextInt();
        fr.close();
        scan = new Scanner(fr2);

        int total_credit = 0;

        while (scan.hasNext()) {

            total_credit += scan.nextInt();
        }
        fr2.close();
        PrintWriter writer = new PrintWriter("credit.txt");  //clearing the credits for next use
        writer.print("");
        writer.close();

        CSVReader reader = new CSVReader(new FileReader("clist.csv"));

        List<String[]> myEntries = reader.readAll();
        ArrayList<SchoolClass> allClasses = new ArrayList<>();

        for (String[] entries : myEntries) { //read in arrayList from csv and convert to new ArrayList
            allClasses.add(new SchoolClass(entries[0], Integer.parseInt(entries[1]), Integer.parseInt(entries[2]), Integer.parseInt(entries[3]), Integer.parseInt(entries[4])));
        }
        reader.close();

        int studyHours = (int) Math.round((total_credit * studyFormula) / 7); //Dividees up studytime by 7 days; based on credits(minimum is 3)
        if (studyHours < 2) {
            studyHours = 2;
        }

        int sleepEnd = 5;
        if (sleepStart > 21) {
            sleepEnd += (sleepStart - 21);
        }

        SchoolSchedule helpschedule = (SchoolSchedule) request.getSession(true).getAttribute("helpschedule");
        if (helpschedule == null) {
            helpschedule = new SchoolSchedule();
        }

        int studyStart = random.nextInt(18 - 6 + 1) + 6;
        int studyEnd = studyStart + 1;

        for (int i = 0; i < 7; i++) {

            SchoolClass sleep = new SchoolClass("Sleep", 0, sleepEnd, i, 0);
            helpschedule.addClass(sleep);

        }
        for (int i = 0; i < 7; i++) {

            SchoolClass sleep = new SchoolClass("Sleep", sleepStart, 24, i, 0);
            helpschedule.addClass(sleep);

        }

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < studyHours; j++) {
                for (SchoolClass sc : allClasses) {

                    if (sc.getDay() == i && sc.getStartTime() == studyStart) {
                        studyStart++;
                        studyEnd++;

                        if (sc.getEndTime() == studyEnd) {
                            studyStart++;
                            studyEnd++;
                        }

                    }

                }
                SchoolClass study = new SchoolClass("Study", studyStart, studyEnd, i, 0);
                helpschedule.addClass(study);
                studyStart++;
                studyEnd++;
            }

            studyStart = random.nextInt(18 - 6 + 1) + 6;
            studyEnd = studyStart + 1;

        }

        request.getSession().setAttribute("helpschedule", helpschedule);
        getServletContext().getRequestDispatcher("/Schedule.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "GenServlet";
    }// </editor-fold>

}
