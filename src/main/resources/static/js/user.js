let index = {
	init: function() {

		$("#btn-save").on("click", () => { 
			//funtion(){}대신, ()=>를 사용하는 이유는 this를 바인딩 하기 위해서
			//function함수를 써버리면 this값이 window객체를 가리킨다.
			this.save();
		});
		
		/*$("#btn-login").on("click", () => { //funtion(){}대신, ()=>를 사용하는 이유는 this를 바인딩 하기 위해서
			//function함수를 써버리면 this값이 window객체를 가리킨다.
			this.login();
		});*/
		
		$("#btn-update").on("click", () => { //funtion(){}대신, ()=>를 사용하는 이유는 this를 바인딩 하기 위해서
			//function함수를 써버리면 this값이 window객체를 가리킨다.
			this.update();
		});
	},

	save: function() {
		alert('user의 login함수 호출됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};


		// ajax호출시 default가 비동기 호출이다. (즉 ajax 실행하고 다른 프로그램 실행하다가 ajax완료시 다시 호출됨.)
		//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청!
		//ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해줌
		$.ajax({
			//회원가입 수행을 요청 => 성공 : done실행, 실패 : fail실행
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), //http body데이터
			contentType: "application/json; charset = utf-8", //body  데이터가 어떤 타입인지(MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 ( 생긴게 json이라면 ) => javascript 오브젝트로 변경해준다.
		}).done(function(resp) { //생긴게 json일 때 응답의 결과가 이 위치에 파라미터로 전달됨.
			console.log(resp);
			alert("회원가입이 완료되었습니다.");
			location.href = "/";
		}).fail(function() {
			alert(JSON.stringify(error));
		});

	},
	
/*	login: function() {
		alert('user의 login함수 호출됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val()
		};


		// ajax호출시 default가 비동기 호출이다. (즉 ajax 실행하고 다른 프로그램 실행하다가 ajax완료시 다시 호출됨.)
		//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청!
		//ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해줌
		$.ajax({
			//회원가입 수행을 요청 => 성공 : done실행, 실패 : fail실행
			type: "POST",
			url: "/api/user/login",
			data: JSON.stringify(data), //http body데이터
			contentType: "application/json; charset = utf-8", //body  데이터가 어떤 타입인지(MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 ( 생긴게 json이라면 ) => javascript 오브젝트로 변경해준다.
		}).done(function(resp) { //생긴게 json일 때 응답의 결과가 이 위치에 파라미터로 전달됨.
			console.log(resp);
			alert("로그인 되었습니다.");
			location.href = "/";
		}).fail(function() {
			alert(JSON.stringify(error));
		});

	}*/
	
	
	update: function() {
		alert('user의 update함수 호출됨');
		let data = {
			id :$("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};

		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data), //http body데이터
			contentType: "application/json; charset = utf-8", //body  데이터가 어떤 타입인지(MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 ( 생긴게 json이라면 ) => javascript 오브젝트로 변경해준다.
		}).done(function(resp) { //생긴게 json일 때 응답의 결과가 이 위치에 파라미터로 전달됨.
			console.log(resp);
			alert("회원수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function() {
			alert(JSON.stringify(error));
		});

	}
}
index.init();