-- สร้างฐานข้อมูลหากยังไม่มี
CREATE DATABASE IF NOT EXISTS job_fair_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- เลือกใช้ฐานข้อมูล
USE job_fair_db;

-- ตารางบริษัท (Companies)
CREATE TABLE Companies (
    company_id CHAR(8) PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    contact_email VARCHAR(255) NOT NULL,
    location VARCHAR(255)
);

-- ตารางตำแหน่งงาน (Jobs)
CREATE TABLE Jobs (
    job_id CHAR(8) PRIMARY KEY,
    job_title VARCHAR(255) NOT NULL,
    job_description TEXT,
    company_id CHAR(8),
    last_application_date DATE,
    status ENUM('เปิด', 'ปิด') NOT NULL,
    job_type ENUM('งานปกติ', 'สหกิจศึกษา') NOT NULL,
    FOREIGN KEY (company_id) REFERENCES Companies(company_id)
);

-- ตารางผู้สมัคร (Candidate)
CREATE TABLE Candidate (
    candidate_id CHAR(8) PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    status ENUM('กำลังศึกษา', 'จบแล้ว') NOT NULL
);

-- ข้อมูลตัวอย่าง (Sample Data)
-- บริษัท
INSERT INTO Companies VALUES
('12345678', 'บริษัท เทค อินโนเวชั่นส์ จำกัด', 'contact@techinnovations.com', 'กรุงเทพมหานคร'),
('87654321', 'บริษัท ฟิวเจอร์ โซลูชั่นส์', 'hr@futuresolutions.com', 'เชียงใหม่');

-- ตำแหน่งงาน
INSERT INTO Jobs VALUES
('10000001', 'Software Engineer', 'Develop and maintain web applications.', '12345678', '2025-10-31', 'เปิด', 'งานปกติ'),
('10000002', 'Data Analyst Intern', 'Analyze data and generate reports.', '12345678', '2025-10-15', 'เปิด', 'สหกิจศึกษา'),
('10000003', 'Frontend Developer', 'Create user-facing features.', '87654321', '2025-11-05', 'เปิด', 'งานปกติ'),
('10000004', 'Backend Developer', 'Work on server-side logic.', '87654321', '2025-11-10', 'ปิด', 'งานปกติ'),
('10000005', 'IT Support', 'Provide technical assistance.', '12345678', '2025-10-20', 'เปิด', 'งานปกติ'),
('10000006', 'HR Intern', 'Assist in the hiring process.', '87654321', '2025-10-25', 'เปิด', 'สหกิจศึกษา'),
('10000007', 'Graphic Designer', 'Design promotional materials.', '12345678', '2025-11-01', 'เปิด', 'งานปกติ'),
('10000008', 'Marketing Specialist', 'Develop marketing campaigns.', '87654321', '2025-11-15', 'เปิด', 'งานปกติ'),
('10000009', 'DevOps Engineer', 'Manage deployment pipelines.', '12345678', '2025-11-20', 'เปิด', 'งานปกติ'),
('10000010', 'UX/UI Designer Intern', 'Design user interfaces.', '87654321', '2025-10-30', 'เปิด', 'สหกิจศึกษา');

-- ผู้สมัคร
INSERT INTO Candidate VALUES
('20000001', 'สมชาย', 'รักดี', 'somchai.r@example.com', 'จบแล้ว'),
('20000002', 'สมศรี', 'ใจงาม', 'somsri.j@example.com', 'กำลังศึกษา'),
('20000003', 'มานะ', 'พากเพียร', 'mana.p@example.com', 'จบแล้ว'),
('20000004', 'ชูใจ', 'กล้าหาญ', 'chujai.k@example.com', 'กำลังศึกษา'),
('20000005', 'อาทิตย์', 'แจ่มใส', 'arthit.j@example.com', 'จบแล้ว'),
('20000006', 'มานี', 'มีนา', 'manee.m@example.com', 'กำลังศึกษา'),
('20000007', 'ปิติ', 'ยินดี', 'piti.y@example.com', 'จบแล้ว'),
('20000008', 'วีระ', 'อดทน', 'veera.o@example.com', 'กำลังศึกษา'),
('20000009', 'เพชร', 'แข็งแกร่ง', 'petch.k@example.com', 'จบแล้ว'),
('20000010', 'แก้วตา', 'ดวงใจ', 'kaewta.d@example.com', 'กำลังศึกษา');