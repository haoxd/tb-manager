<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="Nested Panel" data-options="width:'100%',minHeight:500,noheader:true,border:false" style="padding:10px;">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'west',split:false" style="width:250px;padding:5px">
            <ul id="contentCategoryTree" class="easyui-tree" data-options="url:'/ow/content/category',animate: true,method : 'GET'">
            </ul>
        </div>
        <div data-options="region:'center'" style="padding:5px">
            <table class="easyui-datagrid" id="contentList" data-options="toolbar:contentListToolbar,singleSelect:false,collapsible:true,pagination:true,method:'get',pageSize:20,url:'/ow/content',queryParams:{categoryId:0}">
		    <thead>
		        <tr>
		            <th data-options="field:'id',width:30">ID</th>
		            <th data-options="field:'title',width:120">内容标题</th>
		            <th data-options="field:'sub_title',width:100">内容子标题</th>
		            <th data-options="field:'title_desc',width:120">内容描述</th>
		            <th data-options="field:'url',width:60,align:'center',formatter:TB.formatUrl">内容连接</th>
		            <th data-options="field:'pic',width:50,align:'center',formatter:TB.formatUrl">图片</th>
		            <th data-options="field:'pic2',width:50,align:'center',formatter:TB.formatUrl">图片2</th>
		            <th data-options="field:'create_time',width:130,align:'center',formatter:TB.formatDateTime">创建日期</th>
		            <th data-options="field:'update_time',width:130,align:'center',formatter:TB.formatDateTime">更新日期</th>
		        </tr>
		    </thead>
		</table>
        </div>
    </div>
</div>
<script type="text/javascript" src="/js/manager/content/content.js"></script>