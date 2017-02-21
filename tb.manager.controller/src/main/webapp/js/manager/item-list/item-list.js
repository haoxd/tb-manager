

function getSelectionsIds() {

	var itemList = $("#itemList");
	var sels = itemList.datagrid("getSelections");
	var ids = [];
	for ( var i in sels) {
		ids.push(sels[i].id);
	}
	ids = ids.join(",");
	return ids;
}

var toolbar = [ {
	text : '新增',
	iconCls : 'icon-add',
	handler : function() {
		$(".tree-title:contains('新增商品')").parent().click();
	}
}, {
	text : '编辑',
	iconCls : 'icon-edit',
	handler : function() {

		var ids = getSelectionsIds();
		if (ids.length == 0) {
			$.messager.alert('提示', '必须选择一个商品才能编辑!');
			return;
		}
		if (ids.indexOf(',') > 0) {
			$.messager.alert('提示', '只能选择一个商品!');
			return;
		}

		$("#itemEditWindow").window({
			onLoad : function() {//页面加载完成后直接事件
				//回显数据
				var data = $("#itemList").datagrid("getSelections")[0];

				data.priceView = TB.formatPrice(data.price); //添加回显
				$("#itemeEditForm").form("load", data);

				//更具cid获取名称
			
				$.getJSON('/ow/item/cat/queryItemNameByCid?id=' + data.id, function(_data) {
					cName = _data.name;
					data.cName = _data.name;
					initViewInfo(data);
					
				});
				
			
				// 加载商品描述
				$.getJSON('/ow/item/desc/' + data.id, function(_data) {
					itemEditEditor.html(_data.itemDesc);
				});
				
				

				//加载商品规格
				$.getJSON('/ow/item/param/item/query/' + data.id, function(_data) {
					if (_data && _data.status == 200 && _data.data && _data.data.paramData) {
						$("#itemeEditForm .params").show();
						$("#itemeEditForm [name=itemParams]").val(_data.data.paramData);
						$("#itemeEditForm [name=itemParamId]").val(_data.data.id);

						//回显商品规格
						var paramData = JSON.parse(_data.data.paramData);

						var html = "<ul>";
						for ( var i in paramData) {
							var pd = paramData[i];
							html += "<li><table>";
							html += "<tr><td colspan=\"2\" class=\"group\">" + pd.group + "</td></tr>";

							for ( var j in pd.params) {
								var ps = pd.params[j];
								html += "<tr><td class=\"param\"><span>" + ps.k + "</span>: </td><td><input autocomplete=\"off\" type=\"text\" value='" + ps.v + "'/></td></tr>";
							}

							html += "</li></table>";
						}
						html += "</ul>";
						$("#itemeEditForm .params td").eq(1).html(html);
					}
				});
				//用于异步渲染页面数据
				function initViewInfo(data) {
					TB.init({
						"pics" : data.image,
						"cid" : data.cid,
						"cname":data.cName,
						fun : function(node) {
							TB.changeItemParam(node, "itemeEditForm");
						}
					});
				}

				initViewInfo(data);
			}
		}).window("open");
	}
}, {
	text : '删除',
	iconCls : 'icon-cancel',
	handler : function() {
		var id = getSelectionsIds();
		if (id.length == 0) {
			$.messager.alert('提示', '未选中商品!');
			return;
		}		
			$.messager.confirm('确认', '确定删除ID为 ' + id + ' 的商品吗？', function(isOK) {
			var isCan = isOK;
			if (isCan) {				
				$.ajax({
					   type: "POST",
					   url: "/ow/item/delItemById",
					   data:{"Id":id} ,		   
					   statusCode: {
						   200: function() {
							   $.messager.alert('提示', '删除商品成功!', undefined, function() {
									$("#itemList").datagrid("reload");
								});
						  },
						  404 : function(){
							  $.messager.alert('提示','该商品已经不存在');   
						  },
						   500: function(){
							  $.messager.alert('提示','删除商品失败!'); 
						  }			   
					}		  		 
					});
				
			
			}
		});
	}
}, '-', {
	text : '下架',
	iconCls : 'icon-remove',
	handler : function() {
		var id = getSelectionsIds();
		if (id.length == 0) {
			$.messager.alert('提示', '未选中商品!');
			return;
		}
		$.messager.confirm('确认', '确定下架ID为 ' + id + ' 的商品吗？', function(isOK) {
			var isCan = isOK;
			if (isCan) {				
				$.ajax({
					   type: "POST",
					   url: "/ow/item/lowerItemById",
					   data:{"Id":id} ,		   
					   statusCode: {
						   200: function() {
							   $.messager.alert('提示', '下架商品成功!', undefined, function() {
									$("#itemList").datagrid("reload");
								});
						  },
						   500: function(){
							  $.messager.alert('提示','下架商品失败!'); 
						  }			   
					}		  		 
					});
				
			
			}
		
			
		});
	}
}, {
	text : '上架',
	iconCls : 'icon-remove',
	handler : function() {
		var id = getSelectionsIds();
		if (id.length == 0) {
			$.messager.alert('提示', '未选中商品!');
			return;
		}
		$.messager.confirm('确认', '确定上架ID为 ' + id + ' 的商品吗？', function(isOK) {
			var isCan = isOK;
			if (isCan) {				
				$.ajax({
					   type: "POST",
					   url: "/ow/item/upperItemById",
					   data:{"Id":id} ,		   
					   statusCode: {
						   200: function() {
							   $.messager.alert('提示', '上架商品成功!', undefined, function() {
									$("#itemList").datagrid("reload");
								});
						  },						  
						   500: function(){
							  $.messager.alert('提示','上架商品失败!'); 
						  }			   
					}		  		 
					});
				
			
			}
		
		});
	}
} ];
