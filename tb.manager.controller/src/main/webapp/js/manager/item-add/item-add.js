	var itemAddEditor ;
	
	$(function(){
		//初始化富文本编辑器，调用父js的方法进行创建
		itemAddEditor = TB.createEditor("#itemAddForm [name=desc]");
		TB.init({fun:function(node){
			$("#cid").val(node.id);
			TB.changeItemParam(node, "itemAddForm");
		}});
	});
	
	function submitForm(){
		if(!$('#itemAddForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		//处理商品的价格的单位，将元转化为分
		//eval（）处理数据类型
		$("#itemAddForm [name=price]").val(eval($("#itemAddForm [name=priceView]").val()) * 100);
		//将编辑器中的内容同步到隐藏多行文本中
		itemAddEditor.sync();
		
		//输入的规格参数数据保存为json
		var paramJson = [];
		$("#itemAddForm .params li").each(function(i,e){
			var trs = $(e).find("tr");
			var group = trs.eq(0).text();
			var ps = [];
			for(var i = 1;i<trs.length;i++){
				var tr = trs.eq(i);
				ps.push({
					"k" : $.trim(tr.find("td").eq(0).find("span").text()),
					"v" : $.trim(tr.find("input").val())
				});
			}
			paramJson.push({
				"group" : group,
				"params": ps
			});
		});
		paramJson = JSON.stringify(paramJson);
		
		$("#itemAddForm [name=itemParams]").val(paramJson);
		
		var itemParams = $("#itemAddForm [name=itemParams]").val();
		
		if(itemParams=="" || itemParams == null){
			$.messager.alert('提示','请详细填写商品规格参数');
			return ;
		}
	
		
		//提交到后台的RESTful,基于响应码判断
		$.ajax({
		   type: "POST",
		   url: "/ow/item",
		   data: $("#itemAddForm").serialize(),		   
		   statusCode: {
			   201: function() {
				   $.messager.alert('提示','新增商品成功!');
				  window.location.reload();
			  },
			  400 : function(){
				  $.messager.alert('提示','提交参数不合法!');   
			  },
			   500: function(){
				  $.messager.alert('提示','新增商品失败!'); 
			  }			   
		}		  		 
		});
	}
	
	function clearForm(){
		/*$('#itemAddForm').form('reset');
		itemAddEditor.html('');*/
		$("input[name='title']").val("");
		$("input[name='sellPoint']").val("");
		$("input[name='priceView']").val("");
		$("input[name='price']").val("");
		$("input[name='num']").val("");
	}