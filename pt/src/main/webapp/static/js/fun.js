//导航栏选择状态
$(function(){
	var $menu_li = $('dd');
	$menu_li.click(function(){
		$(this).children().addClass('selected');
		$(this).siblings().children().removeClass('selected');
		$(this).parent().siblings().children().children().removeClass('selected');
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
	$btn_show_hide.click(function(){
		 var framecols = parent.parent.document.getElementById('middleFrame').getAttribute('cols');					   
		 if (framecols == "220,*") {
             parent.parent.document.getElementById('middleFrame').cols = "0,*";
             $btn_show_hide.text("Show");
         } else {
             parent.parent.document.getElementById('middleFrame').cols = "220,*";
             $btn_show_hide.text("Hide");
         }
		
	});
});



//动态加载展开折叠
$(function(){ 
	$('.menu_main a').click(function(){
		$('.menu_sub').load($(this).attr('id'));
		$('.east_inner').html('');
		$('.breadcrumbs').hide();
		$('.east').css({background:'#fff url(../images/welcome.gif) no-repeat',top:'100px'});
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










