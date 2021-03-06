<%@ page language="java" contentType="text/html; charset=UTF-16" pageEncoding="UTF-16"%>

<%@ include file="../layout/header.jsp"%>

<form style="margin-left: 10px">
	<input type="hidden"  id="id" value = "${board.id }"/>
	<div class="form-group">
		<label for="title">Title:</label> 
		<input type="text" class="form-control" placeholder="Enter title" id="title" value="${board.title }">
	</div>
	<div class="form-group">
		<label for="content">Content</label>
		<textarea class="form-control summernote" rows="5" id="content" >${board.content }</textarea>
	</div>
</form>

<button id="btn-update" class="btn btn-primary">수정 완료</button>

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