<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>授权页面</title>
 <style type="text/css">
    .cell-label{ width:80px;}
    #tabcontainer .l-tab-links{border-top:1px solid #D0D0D0;border-left:1px solid #D0D0D0;border-right:1px solid #D0D0D0;}
    
    .projectgrid .l-selected .l-grid-row-cell,.projectgrid .l-selected {background: none;}
    
    .access-icon{ background:url(<%=basePath%>resource/uilib/skins/Aqua/images/controls/checkbox.gif) 0px 0px; height:13px; line-height:13px; width:13px; margin:4px 20px; display:block; cursor:pointer;}
    .access-icon-selected{ background-position:0px -13px;} 
    .l-panel td.l-grid-row-cell-editing { padding-bottom: 2px;padding-top: 2px;}
    </style>
    
    

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
</head>
<body style=" overflow:hidden;"> 
<ipnut type="hidden" id="MenuNo" value="<%=request.getParameter("MenuNo") %>" />
    <div id="layout" style="margin:2px; margin-right:3px;">
         <div position="center" id="mainmenu" title="用户角色"> 
              <div id="tabcontainer" style="margin:2px;">
	              	<div title="角色" tabid="rolelist">
	              		 <form id="mainform">
	                   		 <div id="rolegrid" style="margin:2px auto;"></div>
	                    </form>
	                 </div>
               </div>
          </div>
          <div position="bottom" title="权限控制">
                <div id="rightgrid" style="margin:2px auto;"></div>
          </div>
    </div>

  
    <script type="text/javascript">

        //覆盖本页面grid的loading效果
        LG.overrideGridLoading(); 

        var layout = $("#layout").ligerLayout({
            bottomHeight: 3 * $(window).height() / 5,
            heightDiff: -6,
            onEndResize: updateGridHeight,
            onHeightChanged: updateGridHeight
        });
        var bottomHeader = $(".l-layout-bottom > .l-layout-header:first");

       
        
        
        
        

        var selectedUserID, selectedRoleID;
        // { field: 'id', value: '', op: 'notequal' },
        //屏蔽系统管理员
        var gridUserFilter = {
            op: 'and',
            rules: [
                    {op:"equal",field:"type",value:1},
                    {op:"equal",field:"enable",value:0}
                ]
        };
        //屏蔽系统管理员角色
        var gridRoleFilter = {
            op: 'and',
            rules: [
                    { field: 'id', value: '<%="0" %>', op: 'notequal' }
                ]
        };

        var userlistLoaded = false;
        var gridRole, gridUser;

        gridRole = $("#rolegrid").ligerGrid({
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
        //验证
        var maingform = $("#mainform");
        $.metadata.setType("attr", "validate");
        LG.validate(maingform, { debug: true }); 
        
        //加载toolbar
        LG.loadToolbar(gridRole, toolbarBtnItemClick);
        
        function toolbarBtnItemClick(item){
        	var editingrow = gridRole.getEditingRow();
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
                    var selected = gridRole.getSelected();
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
                    	gridRole.endEdit(editingrow);
                    } else
                    {
                        LG.tip('现在不在编辑状态!');
                    }
                    break;
                    
                case "cancel":
              	  if (editingrow != null)
                    {
              		gridRole.cancelEdit(editingrow); 
                    } else
                    {
                        LG.tip('现在不在编辑状态!');
                    }
                    break;
            }
        }
        
        
        function f_reload() {
        	gridRole.loadData();
        }
        function f_delete() {
            var selected = gridRole.getSelected();
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
        gridRole.bind('beforeSubmitEdit', function (e)
        {
            if (!LG.validator.form())
            {
                LG.showInvalid();
                return false;
            }
            return true;
        });
        gridRole.bind('afterSubmitEdit', function (e)
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
                	gridRole.loadData();
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
            var row = gridRole.getSelectedRow();
            if (!row) { LG.tip('请选择行'); return; }
            gridRole.beginEdit(row);
        }
        function addNewRow()
        {
        	gridRole.addEditRow();
        } 
        
        
        
        gridRole.bind('SelectRow', function (rowdata)
        {
            selectedUserID = null;
            selectedRoleID = rowdata.id;
            //隐藏禁止权限列
            gridRight.toggleCol('Forbid', false);

            bottomHeader.html("设置角色【" + rowdata.name + "】的权限");

            LG.ajax({
                loading: '正在加载角色权限中...',
                url:basepath+"/sysAcl/getRolePermission.do",
                data: { roleid: selectedRoleID },
                success: function (data)
                {
                    var rows = gridRight.rows;
                    for (var i = 0, l = rows.length; i < l; i++)
                    {
                        rows[i].Permit = checkPermit(rows[i], data);
                    }
                    gridRight.reRender();
                }
            });

            //判断是否有权限
            function checkPermit(rowdata, data)
            {
                if (!data || !data.length) return false;
                var isButton = rowdata.BtnID != null && rowdata.BtnID != 0;
                for (var i = 0, l = data.length; i < l; i++)
                {
                    if (isButton && data[i].BtnID == rowdata.BtnID)
                        return true;
                    if (!isButton && data[i].MenuID == rowdata.MenuID)
                        return true;
                }
                return false;
            }
        });

      
        //权限 保存按钮
        var toolbarOptions = {
            items: [
            { text: '保存', click: f_save, img: basepath+"resource/icons/silkicons/page_save.png" }
        ]
        };

        
        
        
        //加载 菜单-按钮数据
        LG.ajax({
            loading: '正在加载菜单按钮中...',
            url:basepath+"sysAcl/getAccess.do",
            success: function (data)
            { 
                gridRight.set('data', { Rows: data });
            }
        });
        
        //权限
        var gridRight = $("#rightgrid").ligerGrid({
            columns:
                [
                { display: '分配权限' , name:'Permit',align: 'left', width: 60, minWidth: 60,isAllowHide :false, render: function (rowdata)
                {
                    var iconHtml = '<div class="access-icon access-permit';
                    if (rowdata.Permit) {iconHtml += " access-icon-selected";}
                    iconHtml += '"';
                    iconHtml += ' rowid = "' + rowdata['__id'] + '"';
                    iconHtml += '></div>';
                    return iconHtml;
                }
                },
                { display: '禁止权限', name: 'Forbid', align: 'left', width: 60, minWidth: 60, isAllowHide: false, render: function (rowdata)
                {
                    var iconHtml = '<div class="access-icon access-forbid';
                    if (rowdata.Forbid) iconHtml += " access-icon-selected";
                    iconHtml += '"';
                    iconHtml += ' rowid = "' + rowdata['__id'] + '"';
                    iconHtml += '></div>';
                    return iconHtml;
                }
                },
                { display: '菜单-按钮', name: 'AccessName', align: 'left', width: 200, minWidth: 60 },
                { display: '编码', name: 'AccessNo', align: 'left', width: 200, minWidth: 60 },
                { display: '图标', name: 'AccessIcon', align: 'left', width: 200, minWidth: 60, render: function (item)
                {
                    return "<div style='width:100%;height:100%;'><img src='" +basepath+(item.AccessIcon==null?"":item.AccessIcon) + "' /></div>";
                } }
                ], showToggleColBtn: false, width: '99%', height: 200, rowHeight: 20, fixedCellHeight: true,
            columnWidth: 100, frozen: false, usePager: false, checkbox: false, rownumbers: true, toolbar: toolbarOptions,
            tree: { columnName: 'AccessName' }, 
            data: []
        });

        //分配权限、禁止权限按钮的事件
        $("div.access-icon").live('click', function ()
        {
            var selected = !$(this).hasClass("access-icon-selected");
            var ispermit = $(this).hasClass("access-permit");
            var rowid = $(this).attr("rowid");
            var rowdata = gridRight.records[rowid];
            if (ispermit)
            {
                f_Permit(rowdata, selected); 
            }
            else
            {
                f_Forbid(rowdata, selected); 
            }
        });

        //为当前选择记录 分配权限
        //1,同时分配给下级记录 
        function f_Permit(rowdata, selected)
        {
            selected = selected ? true : false;
            rowdata.Permit = rowdata.Permit ? true : false;
            if (rowdata.Permit == selected) return;
            rowdata.Permit = selected;
            var children = gridRight.getChildren(rowdata);
            if (children)
            {
                for (var i = 0, l = children.length; i < l; i++)
                {
                    f_Permit(children[i], selected);
                }
            }
            gridRight.reRender({ rowdata: rowdata });
        }
        //为当前选择记录 禁止权限 
        //1,同时对下级记录做禁止权限操作 
        function f_Forbid(rowdata, selected)
        {
            selected = selected ? true : false;
            rowdata.Forbid = rowdata.Forbid ? true : false;
            if (rowdata.Forbid == selected) return;
            rowdata.Forbid = selected;   
            if (selected)
            { 
                var children = gridRight.getChildren(rowdata);
                if (children)
                {
                    for (var i = 0, l = children.length; i < l; i++)
                    {
                        f_Forbid(children[i], selected);
                    }
                }
            }
            gridRight.reRender({ rowdata: rowdata });
        }


       

        

        function f_save()
        {
            if (!selectedRoleID && !selectedUserID) return;
            var data = [];
            for (var i = 0, l = gridRight.rows.length; i < l; i++)
            {
                var o = $.extend({}, gridRight.rows[i]);
                o.Permit = o.Permit ? true : false;
                o.Forbid = o.Forbid ? true : false;
                o.BtnID = o.BtnID || 0;
                o.MenuID = o.MenuID || 0;
                o = gridRight.formatRecord(o, true);
                if ('children' in o)
                    delete o.children;
                data.push(o);
            }
            LG.ajax({
                loading: '正在保存权限设置中...',
                url: basepath+'sysAcl/'+(selectedRoleID==null ? 'saveUserPermission.do' : 'saveRolePermission.do'),
                data: {
                    DataJSON: JSON2.stringify(data),
                    RoleID: selectedRoleID,
                    UserID: selectedUserID
                },
                success: function (data,message)
                {
                    LG.showSuccess(message);
                },
                error: function (message)
                {
                    LG.showError(message);
                }
            });
        }

        function updateGridHeight()
        { 
            var topHeight = $("#layout > .l-layout-center").height();
            var bottomHeight = $("#layout > .l-layout-bottom").height();
            if(gridUser)
                gridUser.set('height', topHeight - 65);
            if (gridRole)
                gridRole.set('height', topHeight - 65);
            if(gridRight)
                gridRight.set('height', bottomHeight - 35); 
        }
        updateGridHeight();
    </script>
</html>