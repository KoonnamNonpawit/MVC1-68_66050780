package com.jobfair.controller;

import com.jobfair.model.Candidate;
import com.jobfair.model.DataAccess;
import com.jobfair.model.Job;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/")
public class JobServlet extends HttpServlet {
    private DataAccess dataAccess;

    public void init() {
        dataAccess = new DataAccess();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "apply":
                showApplyForm(request, response);
                break;
            case "login":
                showLoginForm(request, response);
                break;
            case "logout":
                logout(request, response);
                break;
            default:
                listOpenJobs(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        switch (action) {
            case "submitApplication":
                submitApplication(request, response);
                break;
            case "login":
                login(request, response);
                break;
            default:
                listOpenJobs(request, response);
                break;
        }
    }

    private void listOpenJobs(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Job> openJobs = dataAccess.getOpenJobs();
        request.setAttribute("jobs", openJobs);
        request.getRequestDispatcher("jobs.jsp").forward(request, response);
    }

    private void showApplyForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //-- ตรวจสอบก่อนว่า Login แล้วหรือยัง --
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/?action=login");
            return;
        }
        //-- สิ้นสุดการตรวจสอบ --

        String jobId = request.getParameter("jobId");
        Job job = dataAccess.getJobById(jobId);
        request.setAttribute("job", job);
        request.getRequestDispatcher("apply.jsp").forward(request, response);
    }

    private void submitApplication(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        //-- ตรวจสอบก่อนว่า Login แล้วหรือยัง --
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/?action=login");
            return;
        }

        //-- ดึงอีเมลจาก Session แทนการกรอกใหม่ --
        Candidate loggedInUser = (Candidate) session.getAttribute("loggedInUser");
        String email = loggedInUser.getEmail();
        
        String jobId = request.getParameter("jobId");
        String errorMessage = "";

        // 1. ตรวจสอบความถูกต้องของอีเมล
        if (!isValidEmail(email)) {
            errorMessage = "รูปแบบอีเมลไม่ถูกต้อง";
        } else {
            // 2. ตรวจสอบเงื่อนไขการสมัคร (Business Rules)
            String result = dataAccess.applyForJob(jobId, email);
            if (!"SUCCESS".equals(result)) {
                errorMessage = result;
            }
        }

        if (!errorMessage.isEmpty()) {
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("job", dataAccess.getJobById(jobId));
            request.getRequestDispatcher("apply.jsp").forward(request, response);
        } else {
            // 3. เมื่อสมัครเสร็จ กลับไปหน้าตำแหน่งงาน
            response.sendRedirect(request.getContextPath() + "/?status=success");
        }
    }

    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }
    
    // ----- Authentication -----

    private void showLoginForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        Candidate candidate = dataAccess.getCandidateByEmail(email);

        if (candidate != null) {
            // ถ้าเจออีเมลในระบบ ให้สร้าง Session
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", candidate); // เก็บข้อมูลผู้ใช้ใน session
            response.sendRedirect(request.getContextPath() + "/"); // กลับไปหน้าหลัก
        } else {
            // ถ้าไม่เจอ ให้ส่ง Error กลับไปหน้า Login
            request.setAttribute("errorMessage", "ไม่พบอีเมลนี้ในระบบ");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false); // ดึง session มาโดยไม่สร้างใหม่
        if (session != null) {
            session.invalidate(); // ทำลาย session
        }
        response.sendRedirect(request.getContextPath() + "/?action=login"); // กลับไปหน้า login
    }
}