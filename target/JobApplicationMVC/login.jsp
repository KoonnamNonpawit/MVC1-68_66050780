<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>เข้าสู่ระบบ</title>
    <style>
        body { font-family: sans-serif; text-align: center; padding-top: 50px; }
        .login-container { border: 1px solid #ccc; padding: 30px; display: inline-block; }
        .error { color: red; }
    </style>
</head>
<body>
    <div class="login-container">
        <h1>เข้าสู่ระบบ Job Fair</h1>
        <p>กรุณากรอกอีเมลที่มีอยู่ในระบบเพื่อเข้าใช้งาน</p>
        <hr>
        <%-- แสดงข้อความ Error ถ้ามี --%>
        <% if (request.getAttribute("errorMessage") != null) { %>
            <p class="error"><%= request.getAttribute("errorMessage") %></p>
        <% } %>

        <form action="${pageContext.request.contextPath}/" method="post">
            <input type="hidden" name="action" value="login">
            <label for="email">อีเมล:</label>
            <input type="email" id="email" name="email" required>
            <br><br>
            <input type="submit" value="เข้าสู่ระบบ">
        </form>
    </div>
</body>
</html>