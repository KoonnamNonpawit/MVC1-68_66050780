package com.jobfair.model;

import com.jobfair.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * คลาสนี้ทำหน้าที่จัดการการเข้าถึงข้อมูลในฐานข้อมูล (Data Access Object - DAO)
 * รับผิดชอบการ query และการจัดการข้อมูลที่เกี่ยวข้องกับ Jobs, Companies และ Candidates
 */
public class DataAccess {

    /**
     * ดึงข้อมูลตำแหน่งงานทั้งหมดที่มีสถานะ 'เปิด'
     * @return List ของอ็อบเจกต์ Job
     */
    public List<Job> getOpenJobs() {
        List<Job> jobs = new ArrayList<>();
        // ใช้ SQL JOIN เพื่อดึงชื่อบริษัทมาพร้อมกับข้อมูลงาน
        String sql = "SELECT j.*, c.company_name FROM Jobs j " +
                     "JOIN Companies c ON j.company_id = c.company_id " +
                     "WHERE j.status = 'เปิด' ORDER BY j.last_application_date DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Job job = new Job();
                job.setJobId(rs.getString("job_id"));
                job.setJobTitle(rs.getString("job_title"));
                job.setJobDescription(rs.getString("job_description"));
                job.setCompanyName(rs.getString("company_name")); // มาจากตาราง Companies
                job.setLastApplicationDate(rs.getDate("last_application_date"));
                job.setJobType(rs.getString("job_type"));
                job.setStatus(rs.getString("status"));
                jobs.add(job);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // ในการใช้งานจริงควรจัดการ Exception ให้ดีกว่านี้
        }
        return jobs;
    }

    /**
     * ดึงข้อมูลตำแหน่งงาน 1 ชิ้นจาก Job ID
     * @param jobId รหัสของตำแหน่งงาน
     * @return อ็อบเจกต์ Job หรือ null หากไม่พบ
     */
    public Job getJobById(String jobId) {
        Job job = null;
        String sql = "SELECT j.*, c.company_name FROM Jobs j " +
                     "JOIN Companies c ON j.company_id = c.company_id " +
                     "WHERE j.job_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, jobId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    job = new Job();
                    job.setJobId(rs.getString("job_id"));
                    job.setJobTitle(rs.getString("job_title"));
                    job.setCompanyName(rs.getString("company_name"));
                    job.setJobType(rs.getString("job_type"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return job;
    }

    /**
     * ดึงข้อมูลผู้สมัครจาก Email
     * @param email อีเมลของผู้สมัคร
     * @return อ็อบเจกต์ Candidate หรือ null หากไม่พบ
     */
    public Candidate getCandidateByEmail(String email) {
        Candidate candidate = null;
        String sql = "SELECT * FROM Candidate WHERE email = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    candidate = new Candidate();
                    candidate.setCandidateId(rs.getString("candidate_id"));
                    candidate.setFirstName(rs.getString("first_name"));
                    candidate.setLastName(rs.getString("last_name"));
                    candidate.setEmail(rs.getString("email"));
                    candidate.setStatus(rs.getString("status")); // สถานะ "กำลังศึกษา" หรือ "จบแล้ว"
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidate;
    }

    /**
     * ตรวจสอบเงื่อนไขและดำเนินการสมัครงาน
     * @param jobId รหัสงานที่สมัคร
     * @param candidateEmail อีเมลของผู้สมัคร
     * @return ข้อความ "SUCCESS" หากสำเร็จ หรือข้อความ Error หากไม่สำเร็จ
     */
    public String applyForJob(String jobId, String candidateEmail) {
        // 1. ดึงข้อมูลงานและผู้สมัคร
        Job job = getJobById(jobId);
        if (job == null) {
            return "เกิดข้อผิดพลาด: ไม่พบตำแหน่งงานนี้";
        }

        Candidate candidate = getCandidateByEmail(candidateEmail);
        if (candidate == null) {
            return "ไม่พบอีเมลนี้ในระบบผู้สมัคร";
        }

        // 2. ตรวจสอบเงื่อนไขการสมัคร (Business Rules) ตามโจทย์
        // Model สำหรับการสมัครตำแหน่งสหกิจศึกษา จะรับสมัครเฉพาะผู้สมัครที่มีสถานะกำลังศึกษาเท่านั้น
        if ("สหกิจศึกษา".equals(job.getJobType()) && !"กำลังศึกษา".equals(candidate.getStatus())) {
            return "ตำแหน่งสหกิจศึกษารับสมัครเฉพาะผู้ที่ 'กำลังศึกษา' เท่านั้น";
        }

        // Model สำหรับการสมัครตำแหน่งปกติ จะรับสมัครเฉพาะผู้สมัครที่มีสถานะจบแล้วเท่านั้น
        if ("งานปกติ".equals(job.getJobType()) && !"จบแล้ว".equals(candidate.getStatus())) {
            return "ตำแหน่งงานปกติรับสมัครเฉพาะผู้ที่ 'จบแล้ว' เท่านั้น";
        }

        // หากทุกอย่างถูกต้อง สามารถเพิ่มโค้ดบันทึกการสมัครลง DB ได้ที่นี่ (ถ้ามีตาราง Application)
        // แต่ตามโจทย์ข้อ 2 ไม่ได้บังคับ จึงคืนค่าว่าสำเร็จได้เลย

        return "SUCCESS";
    }
}