<%@ page language="java" contentType="text/html; charset=UTF-16" pageEncoding="UTF-16"%>

<%@ include file="../layout/header.jsp"%>

<button class="btn btn-secondary" onclick="history.back();">돌아가기</button>
<c:if test="${board.user.id == principal.user.id}">
<a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
<!-- <button id ="btn-update" class="btn btn-warning" ></button> -->
<button id ="btn-delete" class="btn btn-danger" >삭제</button>
</c:if>
<br /><br />
<div>
	글 번호 : <span id= "id"><i>${board.id} </i></span>
	작성자 : <span ><i>${board.user.username} </i></span>
</div>
<br />
<div class="form-group">
	<h3>${board.title }</h3>
</div>
<hr />

<div class="form-group">
	<div>${board.content }</div>
</div>
<hr />
<div class="card">
		<div class = "card-body"><textarea class = " form-control"rows="1" ></textarea></div>
		<div class = "card-footer"><button class="btn btn-primary">등록</button></div>
</div>
<div class="card">
		<div class = "card-header">댓글리스트</div>
</div>

<div class="container"></div>
<script>
	$('.summernote').summernote({
		placeholder : '작성해주세요.',
		tabsize : 2,
		height : 300
	});
</script>

<script src="/js/board.js"></script>

<%@ include file="../layout/footer.jsp"%>