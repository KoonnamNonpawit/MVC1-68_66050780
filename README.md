# MVC1-68_66050780
ผมได้ทำข้อสอบ Exit Exam โดยเลือกทำ ข้อที่ 2 ครับ โปรเจกต์ทั้งหมดได้ Commit ขึ้น GitHub เรียบร้อยแล้วตามลิงก์ที่ส่งไปครับ

a. ไฟล์ใดทำหน้าที่อะไรใน MVC และทำงานร่วมกันอย่างไร
โปรเจกต์นี้สร้างขึ้นตามสถาปัตยกรรม MVC โดยแต่ละส่วนมีหน้าที่ดังนี้ครับ:
Model:
  Job.java, Candidate.java, Company.java: เป็นคลาส Java ที่ใช้จำลองโครงสร้างข้อมูลจากตารางในฐานข้อมูล ทำหน้าที่เก็บข้อมูลที่ดึงออกมาหรือเตรียมส่งเข้าไปในฐานข้อมูล
  DataAccess.java: ทำหน้าที่เป็น Data Access Object (DAO) จัดการ Logic ในการเชื่อมต่อและสั่ง Query กับฐานข้อมูลทั้งหมด เช่น การดึงข้อมูลตำแหน่งงาน, การตรวจสอบข้อมูลผู้สมัคร และการบังคับใช้ Business Rules (เช่น นักศึกษาฝึกงานต้องกำลังศึกษา)
  DBUtil.java: เป็น Utility Class ที่จัดการการสร้าง Connection ไปยังฐานข้อมูล MySQL
View:
  login.jsp: หน้าสำหรับให้ผู้ใช้กรอกอีเมลเพื่อเข้าสู่ระบบ
  jobs.jsp: หน้าหลักที่แสดงรายการตำแหน่งงานที่เปิดรับสมัคร (status = 'เปิด') และแสดงข้อมูลผู้ใช้ที่ Login อยู่ พร้อมปุ่ม Logout
  apply.jsp: หน้าสำหรับยืนยันการสมัครงานหลังจากที่ผู้ใช้คลิก "สมัคร" จากหน้า jobs.jsp
Controller:
  JobServlet.java: เป็น Servlet หลักที่ทำหน้าที่เป็น Controller ทั้งหมด รับ Request จากผู้ใช้ผ่าน URL (เช่น ?action=login), ติดต่อกับ Model (DataAccess) เพื่อดึงหรือตรวจสอบข้อมูล และส่งข้อมูลที่ได้ไปยัง View (ไฟล์ JSP) ที่เหมาะสมเพื่อแสดงผลให้ผู้ใช้เห็น
การทำงานร่วมกัน: เมื่อผู้ใช้ส่ง Request มา, Controller (JobServlet) จะรับเรื่องแล้วเรียกใช้ Model (DataAccess) เพื่อจัดการกับข้อมูล เมื่อได้ข้อมูลกลับมาแล้ว Controller จะส่งข้อมูลนั้นไปให้ View (.jsp) เพื่อสร้างหน้าเว็บ HTML ส่งกลับไปให้ผู้ใช้

b. สรุป Routes/Actions หลัก และหน้าจอ View สำคัญ
GET / หรือ GET /?action=list:
  Action: แสดงหน้าหลักของโปรแกรม
  View: jobs.jsp (แสดงรายการตำแหน่งงาน)
GET /?action=login:
  Action: แสดงหน้าฟอร์มสำหรับ Login
  View: login.jsp
POST /?action=login:
  Action: ประมวลผลการ Login โดยตรวจสอบอีเมลกับฐานข้อมูลและสร้าง Session
  View: Redirect ไปที่หน้า jobs.jsp (ถ้าสำเร็จ) หรือ login.jsp (ถ้าล้มเหลว)
GET /?action=logout:
  Action: ทำลาย Session ของผู้ใช้
  View: Redirect ไปที่หน้า login.jsp
GET /?action=apply:
  Action: แสดงหน้ารายละเอียดเพื่อยืนยันการสมัครงาน (ต้อง Login ก่อน)
  View: apply.jsp
POST /?action=submitApplication:
  Action: ประมวลผลและตรวจสอบเงื่อนไขการสมัครงาน
  View: Redirect ไปที่หน้า jobs.jsp (ถ้าสำเร็จ) หรือ apply.jsp (ถ้าล้มเหลว)
