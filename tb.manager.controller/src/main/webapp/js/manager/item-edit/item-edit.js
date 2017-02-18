var itemEditEditor ;
	$(function(){
		//实例化编辑器
		itemEditEditor = TB.createEditor("#itemeEditForm [name=desc]");
	});
	
	function submitForm(){
		if(!$('#itemeEditForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		$("#itemeEditForm [name=price]").val(eval($("#itemeEditForm [name=priceView]").val()) * 100);
		itemEditEditor.sync();
		
		var paramJson = [];
		$("#itemeEditForm .params li").each(function(i,e){
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
		
		$("#itemeEditForm [name=itemParams]").val(paramJson);
		
		//提交到后台的RESTful
		$.ajax({
		   type: "PUT",
		   url: "/ow/item",
		   data: $("#itemeEditForm").serialize(),
		   statusCode :{
			 204:function(){
				 $.messager.alert('提示','修改商品成功!','info',function(){
						$("#itemEditWindow").window('close');
						$("#itemList").datagrid("reload");
					}); 
			 },
			 500:function(){
				  $.messager.alert('提示','修改商品失败!');
			 },
			 400:function(){
				  $.messager.alert('提示',"参数有误，请检查参数");
			 }
		   }
		});
	}