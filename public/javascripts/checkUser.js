$(document).ready(function(){
	
  $("#acountName1").blur(function(){
	if($("#acountName1").val()
		.match(/^([u4e00-u9fa5]|[ufe30-uffa0]|[a-za-z0-9_]){3,12}$/)){
		$("#h12").load("@{AjaxCheck.checkUser()}",{acountName1:$("#acountName1").val()}, function(responseTxt){
      		 if (responseTxt=="0")$("#check").text("账户不可用");
      		 	if (responseTxt=="1")$("#check").text("账户可用");
    	});
	}
	else $("#check").text("账户仅可以是英文大小写数字或者下划线，长度3-12");
    
  });
});