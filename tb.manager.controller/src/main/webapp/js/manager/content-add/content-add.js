var contentAddEditor ;
	$(function(){
		contentAddEditor = TT.createEditor("#contentAddForm [name=content]");
		TT.initOnePicUpload();
		$("#contentAddForm [name=categoryId]").val($("#contentCategoryTree").tree("getSelected").id);
	});
	
	var contentAddPage  = {
			submitForm : function (){
				if(!$('#contentAddForm').form('validate')){
					$.messager.alert('提示','表单还未填写完成!');
					return ;
				}
				contentAddEditor.sync();
				
				
				//提交到后台的RESTful
				$.ajax({
				   type: "POST",
				   url: "/ow/content",
				   data: $("#contentAddForm").serialize(),
				   statusCode: {
					   201: function() {
						   $.messager.alert('提示','新增内容成功!');
	   						$("#contentList").datagrid("reload");
	   						TT.closeCurrentWindow();     					   				  
					  },
					  400 : function(){
						  $.messager.alert('提示','提交参数不合法!');   
					  },
					   500: function(){
						  $.messager.alert('提示','新增商品失败!'); 
					  }			   
				}		
				  
				});
			},
			clearForm : function(){
				$('#contentAddForm').form('reset');
				contentAddEditor.html('');
			}
	};