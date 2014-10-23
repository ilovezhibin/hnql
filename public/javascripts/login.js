/**
 * 登录界面
 */
$(document).ready(function(){
  $(".loginBox").hide();

  
  $(".social .btn").click(function(){
	  $(".logo").hide();
	  $("#footer-content").hide();
	  $(".loginBox").show();
	});
  
});