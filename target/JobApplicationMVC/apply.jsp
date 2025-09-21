<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>สมัครงาน: ${job.jobTitle}</title>
</head>
<body>
    <h1>ยืนยันการสมัครงาน: ${job.jobTitle}</h1>
    <p>บริษัท: ${job.companyName}</p>
    <p>ประเภท: ${job.jobType}</p>
    <p>ผู้สมัคร: ${sessionScope.loggedInUser.firstName} ${sessionScope.loggedInUser.lastName}</p>
    <hr>
    <c:if test="${not empty errorMessage}">
        <p style="color:red;">${errorMessage}</p>
    </c:if>

    <%-- ฟอร์มยืนยันการสมัคร ไม่ต้องกรอกอีเมลแล้ว --%>
    <form action="${pageContext.request.contextPath}/" method="post">
        <input type="hidden" name="action" value="submitApplication">
        <input type="hidden" name="jobId" value="${job.jobId}">
        <input type="submit" value="ยืนยันการสมัคร">
    </form>
    <br>
    <a href="${pageContext.request.contextPath}/">กลับสู่หน้ารายการ</a>
</body>
</html>