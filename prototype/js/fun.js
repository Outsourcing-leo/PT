//导航栏选择状态
$(function(){
	var $menu_li = $('.menu_main li ');
	$menu_li.click(function(){
		$(this).addClass('selected').siblings().removeClass('selected');
	});
});

//导航栏选择状态
$(function(){
	$('input .cls-button').click(function(){
		$(this).addClass('cls-button-mouseover');
	}).mouseout(function(){
		$(this).removeClass('cls-button-mouseover');
	});
});

//显示隐藏菜单
$(function(){
	var $btn_show_hide = $('.btn_show_hide');
	$btn_show_hide.toggle(function(){
		$(this).text('Show');					   
		$('.west').hide();
		$('.east').css({left:'0'});	
		$('.breadcrumbs').css({left:'0'});
	},function(){	
		$(this).text('Hide');
		$('.west').show();
		$('.east').css({left:'220px'});	
		$('.breadcrumbs').css({left:'220px'});
	});
});

//展开折叠按钮
$(function(){ 
	$('.btn_all_show').click(function(){
		$('.menu_sub dd').show();
		$('.menu_sub dt').css({background:'url(images/icon.png) no-repeat  10px -70px'});
	});
	$('.btn_all_hide').click(function(){
		$('.menu_sub dd').hide();
		$('.menu_sub dt').css({background:'url(images/icon.png) no-repeat  10px -44px'});
	});
});

//动态加载展开折叠
$(function(){ 
	$('.menu_main a').click(function(){
		$('.menu_sub').load($(this).attr('id'));
		$('.east_inner').html('');
		$('.breadcrumbs').hide();
		$('.east').css({background:'#fff url(images/welcome.gif) no-repeat',top:'100px'});
	});
});

//展开折叠菜单
function addMenuSub(obj){
	$(obj).toggleClass('icon');  
	$(obj).nextAll().toggle();
}

//动态加载内容区域
function addContent(obj){ 
	$('.east_inner').load($(obj).attr('id'));
	$('.east').css({background:'#fff',top:'125px'});
}

//动态加载内容区域并显示面包屑内容
function addContent_bc(obj){ 
	$('.east_inner').load($(obj).attr('id'));
	$('.east').css({background:'#fff',top:'125px'});
	$('.breadcrumbs').show();
	$('.breadcrumbs li:eq(2)').text($(obj).text());
	$('.breadcrumbs li:eq(1)').text($(obj).parent().parent().children("dt").text());
}









