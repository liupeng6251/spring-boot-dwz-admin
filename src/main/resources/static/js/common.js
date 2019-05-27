
function gotoUrl(url){
	window.location.href=url;
}

function ajaxUtil(url,param,cb){
	$.post(url,param,function(data){
		if(data.resultCode == 0){
			cb(data.data);
		}else if(data.resultCode == 1){
			alert(data.message);
			window.location.href="/";//js中无法获取部署的contextpath，暂时线重定向到根
			//window.open("/");//打开新页面登录
		}else if(data.resultCode == 5){
			alert(data.message);
		}else{
			alert("服务器返回错误resultCode");
		}
	},"json");
}
//手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
	var length = value.length;
	var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
	return this.optional(element) || (length == 11 && mobile.test(value));
}, "请正确填写您的手机号码");

//验证值小数位数不能超过两位
jQuery.validator.addMethod("decimal", function(value, element) {
var decimal = /^-?\d+(\.\d{1,2})?$/;
return this.optional(element) || (decimal.test(value));
}, $.validator.format("小数位数不能超过两位!"));

jQuery.validator.addMethod("ip", function(value, element) {
	var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
	return this.optional(element) || (ip.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));
	}, "Ip地址格式错误");


//页面重置
$(function($) {
	$(window).resize(function() {
		MScreen.resize();
	});
	MScreen.resize();
});
var MScreen= {
		resize:function(){
			var response_h = MScreen.realheight(".navbar-fixed-top");
			$("body").css({"margin-top":response_h});
		},
		realheight:function(element){
			var h=0;
			var o = $(element);
			var mh = parseFloat(o.css("margin-top")||0)+parseFloat(o.css("margin-bottom")||0);
			var ph = parseFloat(o.css("padding-top")||0)+parseFloat(o.css("padding-bottom")||0);
			var bh = parseFloat(o.css("border-top")||0)+parseFloat(o.css("border-bottom")||0);
			h = o.height()+mh+ph+bh;
			return h;
		} 
}