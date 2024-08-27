//自定义手机号格式验证
jQuery.validator.addMethod("isphoneNum", function(value, element) {
	var length = value.length;
	var mobile = /^1\d{10}$/;
	return this.optional(element) || (mobile.test(value));
}, "请正确填写您的手机号码");
//自定义电话号码格式验证
jQuery.validator.addMethod("isdhNum", function(value, element) {
	var mobile = /^((0\d{2,3}-?\d{7,8})|(1\d{10}))$/;
	return this.optional(element) || (mobile.test(value));
}, "请正确填写您的电话号码");
//自定义日期格式
jQuery.validator.addMethod("myDate", function(value, element) {
	var mobile = /^\d{4}-\d{1,2}-\d{1,2}$/;
	return this.optional(element) || (mobile.test(value));
}, "请正确填写日期，日期格式：1980-02-03");

jQuery.validator.addMethod("isHanzi", function(value, element) {
	var mobile = /^[\u4e00-\u9fa5]+$/;
	return this.optional(element) || (mobile.test(value));
}, "请填写中文");

jQuery.validator.addMethod("notEqual", function(value, element, param) {
	return this.optional(element) || value != param;
}, "备用电话号不能和手机号相同");