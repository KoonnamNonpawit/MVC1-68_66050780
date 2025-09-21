<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>ตำแหน่งงานที่เปิดรับสมัคร</title>
</head>
<body>
    <div style="text-align: right; padding: 10px;">
        <%-- ตรวจสอบว่ามี session ของผู้ใช้หรือไม่ --%>
        <c:choose>
            <c:when test="${not empty sessionScope.loggedInUser}">
                สวัสดี, ${sessionScope.loggedInUser.firstName}! 
                <a href="?action=logout">ออกจากระบบ</a>
            </c:when>
            <c:otherwise>
                <a href="?action=login">เข้าสู่ระบบ</a>
            </c:otherwise>
        </c:choose>
    </div>
    
    <h1>ตำแหน่งงานที่เปิดรับสมัคร</h1>
    <c:if test="${param.status == 'success'}">
        <p style="color:green;">สมัครงานสำเร็จ!</p>
    </c:if>
    <table border="1" cellpadding="5" cellspacing="0">
        <thead>
            <tr>
                <th>ชื่องาน</th>
                <th>บริษัท</th>
                <th>ประเภท</th>
                <th>ปิดรับสมัคร</th>
                <th>การดำเนินการ</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="job" items="${jobs}">
                <tr>
                    <td>${job.jobTitle}</td>
                    <td>${job.companyName}</td>
                    <td>${job.jobType}</td>
                    <td>${job.lastApplicationDate}</td>
                    <td><a href="?action=apply&jobId=${job.jobId}">สมัคร</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>