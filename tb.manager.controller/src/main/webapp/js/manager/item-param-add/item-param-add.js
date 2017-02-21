$(function(){
		TB.initItemCat({
			fun:function(node){
			$(".addGroupTr").hide().find(".param").remove();
				//  判断选择的目录是否已经添加过规格
			  /* $.getJSON("/rest/item/param/" + node.id,function(data){
				  if(data){
					  $.messager.alert("提示", "该类目已经添加，请选择其他类目。", undefined, function(){
						 $("#itemParamAddTable .selectItemCat").click();
					  });
					  return ;
				  }
				  $(".addGroupTr").show();
			  }); */
				
				/*
				 * 更具类目id查询商品规格模板
				 * */
			  $.ajax({
				   type: "GET",
				   url: "/ow/item/param/" + node.id,
				   statusCode:{
					 200: function(){
						 $.messager.alert("提示", "该类目已经添加，请选择其他类目。", undefined, function(){
							 $("#itemParamAddTable .selectItemCat").click();
						  }); 
					 },
					 404 :function(){
						 $(".addGroupTr").show();
					 },
					 500 :function(){
						 alert("更具类目查询商品规格模板出错，请重新尝试查询！");
					 } 
				   }			   
				});
			}
		});
		
		$(".addGroup").click(function(){
			  var temple = $(".itemParamAddTemplate li").eq(0).clone();
			  $(this).parent().parent().append(temple);
			  temple.find(".addParam").click(function(){
				  var li = $(".itemParamAddTemplate li").eq(2).clone();
				  li.find(".delParam").click(function(){
					  $(this).parent().remove();
				  });
				  li.appendTo($(this).parentsUntil("ul").parent());
			  });
			  temple.find(".delParam").click(function(){
				  $(this).parent().remove();
			  });
		 });
		
		$("#itemParamAddTable .close").click(function(){
			$(".panel-tool-close").click();
		});
		
		$("#itemParamAddTable .submit").click(function(){
			var params = [];
			var groups = $("#itemParamAddTable [name=group]");
			groups.each(function(i,e){
				var p = $(e).parentsUntil("ul").parent().find("[name=param]");
				var _ps = [];
				p.each(function(_i,_e){
					var _val = $(_e).siblings("input").val();
					if($.trim(_val).length>0){
						_ps.push(_val);						
					}
				});
				var _val = $(e).siblings("input").val();
				if($.trim(_val).length>0 && _ps.length > 0){
					params.push({
						"group":_val,
						"params":_ps
					});					
				}
			});
			var url = "/ow/item/param/addItemParam?itemCatId="+$("#itemParamAddTable [name=cid]").val();
			$.post(url,{"paramData":JSON.stringify(params)},function(data){
				$.messager.alert('提示','新增商品规格成功!',undefined,function(){
					$(".panel-tool-close").click();
   					$("#itemParamList").datagrid("reload");
   				});
			});
		});
	});