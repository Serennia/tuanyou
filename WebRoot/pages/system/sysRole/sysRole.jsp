<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<link href="<%=basePath %>resource/uilib/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=basePath %>resource/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
    <script src="<%=basePath %>resource/uilib/js/ligerui.min.js" type="text/javascript"></script>   
    <link href="<%=basePath %>resource/css/common.css" rel="stylesheet" type="text/css" />  
    <script src="<%=basePath %>resource/js/common.js" type="text/javascript"></script>   
    <script src="<%=basePath %>resource/js/LG.js" type="text/javascript"></script>
    <script src="<%=basePath %>resource/js/ligerui.expand.js" type="text/javascript"></script> 
    <script src="<%=basePath %>resource/json2.js" type="text/javascript"></script> 
  <script src="<%=basePath %>resource/jquery-validation/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="<%=basePath %>resource/jquery-validation/jquery.metadata.js"
	type="text/javascript"></script>
<script src="<%=basePath %>resource/jquery-validation/messages_cn.js"
	type="text/javascript"></script>
<script src="<%=basePath %>resource/jquery.form.js" type="text/javascript"></script>
<script src="<%=basePath %>resource/js/validator.js" type="text/javascript"></script>
<script src="<%=basePath %>resource/json2.js" type="text/javascript"></script>
<script type="text/javascript">
	var basepath = '<%=basePath %>';
</script>
<title>系统角色</title>
</head>
<body style="padding: 4px; overflow: hidden;">
<ipnut type="hidden" id="MenuNo" value="<%=request.getParameter("MenuNo") %>" />
  <form id="mainform">
    <div id="maingrid"  style="margin:2px;"></div> 
    </form> 
  <script type="text/javascript">
      //列表结构
      var grid = $("#maingrid").ligerGrid({
         columns: [
          { display: "角色名", name: "name", width: 280, type: "text", align: "left"
                , validate: { required: true }
                , editor: { type: 'text' }
          },
          { display: "描述", name: "description", width: 580, type: "textarea", align: "left", editor: { type: 'text'} }
          ], dataAction: 'server', pageSize: 20, toolbar: {},
          url: basepath +'SystemWeb/getGrid.do?view=sys_role', sortName: 'id', 
          width: '100%', height: '100%',heightDiff:-10, checkbox: false,enabledEdit: true, clickToEdit: false
      });
      //双击事件
      LG.setGridDoubleClick(grid, 'modify');
      //验证
      var maingform = $("#mainform");
      $.metadata.setType("attr", "validate");
      LG.validate(maingform, { debug: true }); 
      //加载toolbar
      LG.loadToolbar(grid, toolbarBtnItemClick);
      //工具条事件
      function toolbarBtnItemClick(item)
      {
          var editingrow = grid.getEditingRow();
          switch (item.id) {
              case "add":
                  if (editingrow == null)
                  {
                      addNewRow();
                  } else
                  {
                      LG.tip('请先提交或取消修改');
                  }
                  break;
              case "modify":
                  var selected = grid.getSelected();
                  if (!selected) { LG.tip('请选择行!'); return; }
                  if (editingrow == null)
                  {
                      beginEdit();
                  } else
                  {
                      LG.tip('请先保存或取消修改');
                  }
                  break;
              case "delete":
                  jQuery.ligerDialog.confirm('确定删除吗?', function (confirm) {
                      if (confirm)
                          f_delete();
                  });
                  break;
              case "save":
                  if (editingrow != null)
                  {
                      grid.endEdit(editingrow);
                  } else
                  {
                      LG.tip('现在不在编辑状态!');
                  }
                  break;
                  
              case "cancel":
            	  if (editingrow != null)
                  {
                      grid.cancelEdit(editingrow); 
                  } else
                  {
                      LG.tip('现在不在编辑状态!');
                  }
                  break;
          }
      }
      function f_reload() {
          grid.loadData();
      }
      function f_delete() {
          var selected = grid.getSelected();
          if (selected) {
              LG.ajax({
                  url:basepath+"sysRole/delete.do",
                  loading: '正在删除中...',
                  data: { id: selected.id },
                  success: function () {
                      LG.showSuccess('删除成功');
                      f_reload();
                  },
                  error: function (message) {
                      LG.showError(message);
                  }
              });
          }
          else {
              LG.tip('请选择行!');
          }
      }
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
          var data = $.extend(true, {}, e.newdata);
          if (!isAddNew)
              data.id = e.record.id;
          LG.ajax({
              loading: '正在保存数据中...',
              url: basepath+"sysRole/"+(isAddNew ? "insert.do" : "update.do"),
              data: data,
              success: function ()
              { 
                  grid.loadData();
                  LG.tip('保存成功!');
              },
              error: function (message)
              {
                  LG.tip(message);
              }
          });
          return false;
      }); 
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