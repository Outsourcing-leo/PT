/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: ZH (Chinese, 中文 (Zhōngwén), 汉语, 漢語)
 */
jQuery.extend(jQuery.validator.messages, {
        required: "required",
		remote: "plese correct the field",
		email: "Please check the format and enter a correct e-mail address",
		url: "Please check the format and enter a correct url",
		date: "Please enter a legal date",
		dateISO: "Please enter a legal date (ISO).",
		number: "Please enter a legal number",
		digits: "只能输入整数",
		creditcard: "请输入合法的信用卡号",
		equalTo: "please enter a same value again",
		accept: "请输入拥有合法后缀名的字符串",
		maxlength: jQuery.validator.format("请输入一个长度最多是 {0} 的字符串"),
		minlength: jQuery.validator.format("请输入一个长度最少是 {0} 的字符串"),
		rangelength: jQuery.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
		range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
		max: jQuery.validator.format("请输入一个最大为 {0} 的值"),
		min: jQuery.validator.format("请输入一个最小为 {0} 的值")
});

jQuery.extend(jQuery.validator.defaults, {
    errorElement: "span"
});