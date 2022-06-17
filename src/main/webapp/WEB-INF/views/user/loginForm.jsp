<%@ page language="java" contentType="text/html; charset=UTF-16" pageEncoding="UTF-16"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="/auth/loginProc" method="post">
		<div class="form-group">
			<label for="username">Username:</label> <input type="text" name="username" class="form-control" placeholder="Enter Username" id="username">
		</div>
		<div class="form-group">
			<label for="password">Password:</label> <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href = "https://kauth.kakao.com/oauth/authorize?client_id=8cbf61db5f2d4ba1ebf22575adbcedbc&redirect_uri=http://localhost:7070/auth/kakao/callback&response_type=code" ><img height ="39px"  src= "/image/kakao_login_button.png" ></a>
	</form>
</div>

<!-- <script src="/js/user.js"></script> -->
<%@ include file="../layout/footer.jsp"%>