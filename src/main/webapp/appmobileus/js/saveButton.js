$(".savebutton").click(function(){
	var staffid = GetQueryString("staffid");
	var	sessionid=GetQueryString('sessionid');
	var	flag=GetQueryString('flag');
	window.location.href='/appmobileus/USFilming.html?staffid='+ staffid+'&sessionid='+sessionid+'&flag='+flag;
});