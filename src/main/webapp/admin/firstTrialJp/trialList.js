//异步加载的URL地址
var url="/admin/firstTrialJp/trialListData.html";
//vue表格数据对象
var _self;
/*  var firstTrial = {
	    		FTdata:[
	    		       {orderNumber:"170808-JP0001",
	    		    	travelDate:"2017-10-22",
	    		    	returnDate:"2017-12-22",
	    		    	state:"不合格",
		    		    people:[
		    		       {name:"宋仲基",passportNo:"G73635124",phone:"15132636399",state:"初审"},
		    		       {name:"马斯洛",passportNo:"G73602220",phone:"15132336388",state:"不合格"},
		    		       {name:"宋仲基",passportNo:"G73635124",phone:"15132636399",state:"初审"}
		    		    ]
	    		       },
	    		],
	    }; */
var firstTrial = {trialJapanData:""};
new Vue({
	el: '#card',
	data: firstTrial,
	created:function(){
		_self=this;
		$.ajax({ 
			url: url,
			dataType:"json",
			type:'post',
			success: function(data){
				_self.trialJapanData = data.trialJapanData;
			}
		});
	},
	methods:{
		/* editClick:function(){//编辑图标  页面跳转
					window.location.href = '${base}/admin/firstTrialJp/edit.html';
				} */
		visaDetail:function(orderid){
			//跳转到签证详情页面
			window.open('/admin/firstTrialJp/trialDetail.html?orderid='+orderid);
			//console.log(message);
			//alert(JSON.stringify(event.target));
		},
		expressFun:function(){//跳转快递弹层页面
    		layer.open({
    		    type: 2,
    		    title: false,
    		    closeBtn:false,
    		    fix: false,
    		    maxmin: false,
    		    shadeClose: false,
    		    scrollbar: false,
    		    area: ['900px', '550px'],
    		    content: '/admin/firstTrialJp/express.html'
    	    });
    	},
    	passportFun:function(){
    		layer.open({
    		    type: 2,
    		    title: false,
    		    closeBtn:false,
    		    fix: false,
    		    maxmin: false,
    		    shadeClose: false,
    		    scrollbar: false,
    		    area: ['900px', '550px'],
    		    content: '/admin/firstTrialJp/passport.html'
    	    });
    	},
    	visaInfoFun:function(){
    		layer.open({
    		    type: 2,
    		    title: false,
    		    closeBtn:false,
    		    fix: false,
    		    maxmin: false,
    		    shadeClose: false,
    		    scrollbar: false,
    		    area: ['900px', '550px'],
    		    content: '/admin/firstTrialJp/visaInfo.html'
    	    });
    	}
	}
});
function search(){
	/* var status = $('#status').val(); */
	var searchStr = $('#searchStr').val();
	$.ajax({ 
		url: url,
		/* data:{status:status,searchStr:searchStr}, */
		data:{searchStr:searchStr},
		dataType:"json",
		type:'post',
		success: function(data){
			_self.trialJapanData = data.trialJapanData;
		}
	});
}