<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<link href="<%=basePath %>resource/uilib/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<script src="<%=basePath %>resource/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
<script src="<%=basePath %>resource/uilib/js/ligerui.all.js" type="text/javascript"></script>
<link href="<%=basePath %>resource/css/common.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath %>resource/js/common.js" type="text/javascript"></script>
<script src="<%=basePath %>resource/js/LG.js" type="text/javascript"></script>
<script src="<%=basePath %>resource/js/ligerui.expand.js" type="text/javascript"></script>
<script src="<%=basePath %>resource/json2.js" type="text/javascript"></script>
<script src="<%=basePath %>resource/js/iconselector.js" type="text/javascript"></script>
<link href="<%=basePath %>resource/uilib/skins/ligerui-icons.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath %>resource/jquery-validation/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="<%=basePath %>resource/jquery-validation/jquery.metadata.js"
	type="text/javascript"></script>
<script src="<%=basePath %>resource/jquery-validation/messages_cn.js"
	type="text/javascript"></script>
<script src="<%=basePath %>resource/jquery.form.js" type="text/javascript"></script>
<script src="<%=basePath %>resource/js/validator.js" type="text/javascript"></script>
<script type="text/javascript">
	var basepath = '<%=basePath%>';
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单维护</title>
<script src="<%=basePath %>resource/js/iconselector.js" type="text/javascript"></script>
<script type="text/javascript">
	var basepath = '<%=basePath %>';
</script>
</head>
<body style="padding: 4px; overflow: hidden;">
	<div id="layout">
    <div position="left" title="主菜单模块" id="mainmenu">
        <ul id="maintree"></ul>
     </div>
    <div position="center" title="子菜单列表"> 
        <form id="mainform">
        <div id="maingrid"  style="margin:2px;"></div> 
        </form>
    </div>
  </div>
  <ul class="iconlist">
  </ul>
  
  <script type="text/javascript">
  	 	 var maingform = $("#mainform");   
 	  	//验证
 	   	jQuery.metadata.setType("attr", "validate"); 
       	LG.validate(maingform, { debug: true });
      	//覆盖本页面grid的loading效果
       	LG.overrideGridLoading(); 
      
      
      
      		var toolbarOptions = { 
    	        items: [ 
    	            { text: '增加', click: itemclick , img:basepath+"resource/icons/silkicons/add.png"}, 
    	            { line:true },
    	            { text: '修改', click: itemclick, img: basepath+"resource/icons/miniicons/page_edit.gif" },
    	            { line: true },
    	            { text: '保存', click: itemclick, img: basepath+"resource/icons/silkicons/page_save.png" },
    	            { line: true },
    	            { text: '取消', click: itemclick, img: basepath+"resource/icons/silkicons/cancel.png" },
    	            { line: true },
    	            { text: '删除', click: itemclick, img: basepath+"resource/icons/miniicons/page_delete.gif" },
    	            { line: true },
    	            { text: '操作按钮', click: itemclick, img: basepath+"resource/icons/32X32/document_library.gif" } 
    	        ]
    	    };

    	    var currentMenuParentNo;
    	    var treefilter = {
    	        op: 'or',
    	        rules: [
    			{ field:"MenuParentNo","value":"0","op":"equal" },
    	        { field: "MenuParentNo", "op": "isnull" }
    	        ]
    	    };
    	    var tree = $("#maintree").ligerTree({
    	    	url: '../SystemWeb/getTree.do?' +
    	    $.param({
    	        root: '主菜单',
    	        rooticon: '../resource/icons/32X32/category.gif',
    	        view: 'Sys_Menu',
    	        idfield: 'MenuNo',
    	        pidfield: 'MenuParentNo',
    	        textfield: 'MenuName',
    	        iconfield: 'MenuIcon',
    	        iconroot: '../',
    	        where: JSON2.stringify(treefilter)
    	    }),
    	        checkbox: false,
    	        onClick: function (node)
    	        {
    	            if (!node.data.id) return;
    	            var where = {
    	                op: 'and',
    	                rules: [{ field: 'MenuParentNo', value: node.data.id, op: 'equal'}]
    	            };
    	            currentMenuParentNo = node.data.id;
    	            grid.set('parms', { where: JSON2.stringify(where) });
    	            grid.set('url', '../SystemWeb/getGrid.do?view=Sys_Menu');
    	        }
    	    });

    	    var layout = $("#layout").ligerLayout({ leftWidth: 140 });
    	     
    	    var grid = $("#maingrid").ligerGrid({
    	        columns: [
    	                {display: '序号', name: 'menuorder', align: 'left', width: 60, minWidth: 60
    	                , validate: { required: true }
    	                , editor: { type: 'int' }
    	                },
    	                { display: '菜单名', name: 'menuname', align: 'left', width: 180, minWidth: 60
    	                , validate: { required: true }
    	                , editor: { type: 'text' }
    	                },
    	                { display: '菜单编号', name: 'menuno', align: 'left', width: 130, minWidth: 60
    	                , validate: { required: true }
    	                , editor: { type: 'text' }
    	                },
    	                { display: '链接地址', name: 'menuurl', align: 'left', width: 300, minWidth: 60
    	                , validate: { required: true }
    	                , editor: { type: 'text' }
    	                }
    	                , { display: '图标', name: 'menuicon', align: 'left', width: 230, minWidth: 50
    	                , editor: { type: 'select',
    	                    ext:
    	                    function (rowdata)
    	                    {
    	                        return {
    	                            onBeforeOpen: function ()
    	                            {
    	                                currentComboBox = this;
    	                                f_openIconsWin();
    	                                return false;
    	                            },
    	                            render: function ()
    	                            {
    	                                return rowdata.menuicon;
    	                            }
    	                        };
    	                    } }
    	                , render: function (item)
    	                {
    	                    return "<div style='width:100%;height:100%;'><img src='../" + (item.menuicon==null?"":item.menuicon) + "' /></div>";
    	                }
    	                }
    	                ], dataAction: 'server', pageSize: 20, toolbar: toolbarOptions, sortName: 'menuno',
    	        width: '98%', height: '100%', heightDiff: -5, checkbox: false, usePager: false, enabledEdit: true, clickToEdit: false,
    	        fixedCellHeight: true, rowHeight: 25
    	    });


    	    grid.bind('beforeSubmitEdit', function (e)
    	    {
    	        
    	    	if (!LG.validator.form())
    	        {
    	            LG.showInvalid();
    	            return false;
    	        }
    	        return true;
    	    });
    	    grid.bind('afterSubmitEdit', function (e)
    	    {
    	        var isAddNew = e.record['__status'] == "add";
    	        var data = $.extend({ menuParentNo: currentMenuParentNo }, e.newdata);
    	        if (!isAddNew)
    	            data.menuid = e.record.menuid;
    	        	LG.ajax({
    	                loading: '正在保存数据中...',
    	                url: basepath+"systemMenu/"+(isAddNew ?"addMenu.do": "updateMenu.do") ,
    	                data: data,
    	                success: function (data,message)
    	                {
    	                    grid.loadData();
    	                    LG.tip(message);
    	                },
    	                error: function (message)
    	                {
    	                    LG.tip(message);
    	                }
    	            }); 
    	    }); 
      
      

      function itemclick(item)
      {
          var editingrow = grid.getEditingRow();
          switch (item.text)
          {
              case "增加":
                  if (editingrow == null)
                  {
                      addNewRow();
                  } else
                  {
                      LG.tip('请先提交或取消修改');
                  }
                  break;
              case "修改":
                  if (editingrow == null)
                  {
                      beginEdit();
                  } else
                  {
                      LG.tip('请先提交或取消修改');
                  }
                  break;
              case "保存":
                  if (editingrow != null)
                  {
                      grid.endEdit(editingrow);
                  } else
                  {
                      LG.tip('现在不在编辑状态!');
                  }
                  break;
              case "取消":
                  if (editingrow != null)
                  {
                      grid.cancelEdit(editingrow); 
                  } else
                  {
                      LG.tip('现在不在编辑状态!');
                  }
                  break;
              case "删除": 
                  $.ligerDialog.confirm('确定删除吗?', function (confirm) {
                      if (confirm)
                          f_delete();
                  });
                  break;
              case "操作按钮":
                  var selected = grid.getSelected();
                  if (!selected) return;
                  top.f_addTab(null, selected.menuname + ' 操作按钮管理', basepath+'pages/system/sysMenu/button.jsp?menuNo=' + selected.menuno);
                  break;
          }
      }
      function f_reload()
      {
          grid.loadData();
      }
      function f_delete()
      { 
          var selected = grid.getSelected();
          if (selected)
          {
              if (!selected.menuid)
              {
                  grid.deleteRow(selected);
                  return;
              }
              LG.ajax({
                  url: '../systemMenu/delMenu.do',
                  loading:'正在删除中...',
                  data: { menuid: selected.menuid },
                  success: function () { 
                      LG.showSuccess('删除成功');
                      f_reload();
                  },
                  error: function (message)
                  {
                      LG.showError(message);
                  }
              });
          }
          else
          {
              LG.tip('请选择行!');
          }
      }
    

    function beginEdit()
    {
        var row = grid.getSelectedRow();
        if (!row) { LG.tip('请选择行'); return; }
        grid.beginEdit(row); 
    }
    function addNewRow()
    {
        grid.addEditRow();
    }
  </script>
</body>
</html>