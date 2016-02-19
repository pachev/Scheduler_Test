<%-- 
    Document   : Schedule.jsp
    Created on : Nov 27, 2014, 9:23:04 AM
    Author     : drawven
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="stylesheet.css">
        <title>Student Scheduler</title>
    </head>

    <body>
        <form method="post" action="/Scheduler_Test/ScheduleServlet" >
            Course Name: <input type="text" name="title" size="35"> 
            Credit Hours: <input type="text" name="credit" size="5" value="0"> 
            <br><b>When you are finished, click submit at the bottom of the page to generate study and sleep times</b>
            <br>
            Course Time: 
            Sun <input type="checkbox" name="day" value="sun" />
            Mon <input type="checkbox" name="day" value="mon" />
            Tue <input type="checkbox" name="day" value="tue" />
            Wed <input type="checkbox" name="day" value="wed" />
            Thu <input type="checkbox" name="day" value="thu" />
            Fri <input type="checkbox" name="day" value="fri" />
            Sat <input type="checkbox" name="day" value="sat" />

            <select name="starttime">
                <OPTION value="7">7:00am</OPTION>
                <OPTION value="8">8:00am</OPTION>
                <OPTION value="9">9:00am</OPTION>
                <OPTION value="10">10:00am</OPTION>
                <OPTION value="11">11:00am</OPTION>
                <OPTION value="12">12:00pm</OPTION>
                <OPTION value="13">1:00pm</OPTION>
                <OPTION value="14">2:00pm</OPTION>
                <OPTION value="15">3:00pm</OPTION>
                <OPTION value="16">4:00pm</OPTION>
                <OPTION value="17">5:00pm</OPTION>
                <OPTION value="18">6:00pm</OPTION>
                <OPTION value="19">7:00pm</OPTION>
                <OPTION value="20">8:00pm</OPTION>
                <OPTION value="21">9:00pm</OPTION>
            </select>
            to
            <SELECT name="endtime">
                <OPTION value="8">8:00am</OPTION>
                <OPTION value="9">9:00am</OPTION>
                <OPTION value="10">10:00am</OPTION>
                <OPTION value="11">11:00am</OPTION>
                <OPTION value="12">12:00pm</OPTION>
                <OPTION value="13">1:00pm</OPTION>
                <OPTION value="14">2:00pm</OPTION>
                <OPTION value="15">3:00pm</OPTION>
                <OPTION value="16">4:00pm</OPTION>
                <OPTION value="17">5:00pm</OPTION>
                <OPTION value="18">6:00pm</OPTION>
                <OPTION value="19">7:00pm</OPTION>
                <OPTION value="20">8:00pm</OPTION>
                <OPTION value="21">9:00pm</OPTION>
                <OPTION value="22">10:00pm</OPTION>
            </SELECT>
            <br>
            <input type="submit" value="Add Course" name="submit" />
        </form>
        <TABLE border="1" cellspacing="0">
            <TBODY>
                <TR>
                    <TH align="center" valign="middle" width="80"></TH>
                    <TH align="center" valign="middle" width="100">Sunday</TH>
                    <TH align="center" valign="middle">Monday</TH>
                    <TH align="center" valign="middle">Tuesday</TH>
                    <TH align="center" valign="middle">Wednesday</TH>
                    <TH align="center" valign="middle">Thursday</TH>
                    <TH align="center" valign="middle">Friday</TH>
                    <TH align="center" valign="middle">Saturday</TH>
                </TR>
                <c:forEach begin="0" end="23" step="1" var="time">
                    <TR>
                        <TD align="center" valign="middle" width="80">
                            <c:choose>
                                <c:when test="${time == 12}">
                                    <c:out value="${time}" />:00pm
                                </c:when>
                                <c:when test="${time > 12}">
                                    <c:out value="${time - 12}" />:00pm
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${time}" />:00am
                                </c:otherwise>
                            </c:choose></TD>
                            <c:forEach begin="0" end="6" step="1" var="day">
                            <TD align="center" valign="middle" width="100">
                                <c:forEach items="${schoolschedule.classes}" var="course">
                                    <c:if test="${course.startTime <= time 
                                                  && course.endTime > time 
                                                  && course.day == day}">
                                        <c:out value="${course.title}" />
                                    </c:if>
                                </c:forEach>
                                <div  style="background-color: rgb(102, 204, 204); ">
                                    <span style="color:whitesmoke">
                                        <c:forEach items="${helpschedule.classes}" var="sleep">
                                            <c:if test="${sleep.startTime <= time 
                                                          && sleep.endTime > time 
                                                          && sleep.day == day}">
                                                <c:out value="${sleep.title}"  />
                                            </span>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </TD>
                        </c:forEach>
                    </TR>
                </c:forEach>
            </TBODY>
        </TABLE>
        <form action="/Scheduler_Test/GenServlet" method="POST">
            <input type="submit" name="finished"/>
        </form>
    </body>
</html>