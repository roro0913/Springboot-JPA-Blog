let index = {
	init: function() {

		$("#btn-save").on("click", () => { //funtion(){}대신, ()=>를 사용하는 이유는 this를 바인딩 하기 위해서
			//function함수를 써버리면 this값이 window객체를 가리킨다.
			this.save();
		});

		$("#btn-delete").on("click", () => { //funtion(){}대신, ()=>를 사용하는 이유는 this를 바인딩 하기 위해서
			//function함수를 써버리면 this값이 window객체를 가리킨다.
			this.deleteById();
		});
		
		$("#btn-update").on("click", () => { //funtion(){}대신, ()=>를 사용하는 이유는 this를 바인딩 하기 위해서
			//function함수를 써버리면 this값이 window객체를 가리킨다.
			this.update();
		});
	},

	save: function() {
		alert('board의 save함수 호출됨');
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};

		$.ajax({
			//회원가입 수행을 요청 => 성공 : done실행, 실패 : fail실행
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data), //http body데이터
			contentType: "application/json; charset = utf-8", //body  데이터가 어떤 타입인지(MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 ( 생긴게 json이라면 ) => javascript 오브젝트로 변경해준다.
		}).done(function(resp) { //생긴게 json일 때 응답의 결과가 이 위치에 파라미터로 전달됨.
			console.log(resp);
			alert("글쓰기가 저장되었습니다.");
			location.href = "/";
		}).fail(function() {
			alert(JSON.stringify(error));
		});
	},
	
	
	deleteById: function() {
		let id = $("#id").text();
		alert('board의 delete함수 호출됨');
		$.ajax({
			//회원가입 수행을 요청 => 성공 : done실행, 실패 : fail실행
			type: "DELETE",
			url: "/api/board/"+id,
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 ( 생긴게 json이라면 ) => javascript 오브젝트로 변경해준다.
		}).done(function(resp) { //생긴게 json일 때 응답의 결과가 이 위치에 파라미터로 전달됨.
			console.log(resp);
			alert("삭제가 완료되었습니다.");
			location.href = "/";
		}).fail(function() {
			alert(JSON.stringify(error));
		});

	},
	update: function() {
		alert('board의 update함수 호출됨');
		let id = $("#id").val();
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};

		$.ajax({
			//회원가입 수행을 요청 => 성공 : done실행, 실패 : fail실행
			type: "PUT",
			url: "/api/board/"+id,
			data: JSON.stringify(data), //http body데이터
			contentType: "application/json; charset = utf-8", //body  데이터가 어떤 타입인지(MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 ( 생긴게 json이라면 ) => javascript 오브젝트로 변경해준다.
		}).done(function(resp) { //생긴게 json일 때 응답의 결과가 이 위치에 파라미터로 전달됨.
			console.log(resp);
			alert("수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function() {
			alert(JSON.stringify(error));
		});
	},	
}
index.init();