<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>按钮添加</title>
</head>
<body style="padding: 4px; overflow: hidden;">
  <form id="mainform">
    <div id="maingrid"  style="margin:2px;"></div> 
    </form> 
  <script type="text/javascript">
      var menuNo = '<%= request.getParameter("menuNo") %>';
      
      
      //验证
      var maingform = $("#mainform");
      $.metadata.setType("attr", "validate");
      LG.validate(maingform, { debig: true }); 
      //这里覆盖了本页面grid的loading效果
      $.extend($.ligerDefaults.Grid, {
          onloading: function ()
          {
              LG.showLoading('正在加载表格数据中...');
          },
          onloaded: function ()
          {
              LG.hideLoading();
          }
      });

      function itemclick(item)
      {
          var editingrow = grid.getEditingRow();
          var id = item.id || item.text;
          switch (id)
          {
              case "fastaddbtn":
                  $.ligerDialog.confirm('确定增加【增加按钮】、【修改按钮】、【删除按钮】、【查看按钮】吗?', function (confirm)
                  {
                      if (!confirm) return;
                      LG.ajax({
                          url: basepath+'systemMenu/addCUDButtons.do',
                          loading: '正在增加按钮中...',
                          data: { menuNo: menuNo },
                          success: function ()
                          {
                              LG.showSuccess('快速增加按钮成功');
                              f_reload();
                          },
                          error: function (message)
                          {
                              LG.showError(message);
                          }
                      });
                  });
                  break;
              case "clearbtns":
                  $.ligerDialog.confirm('确定清空按钮吗?', function (confirm)
                  {
                      if (!confirm) return;
                      LG.ajax({
                          url: basepath+'systemMenu/clearButton.do',
                          loading: '正在清空按钮中...',
                          data: { menuNo: menuNo },
                          success: function ()
                          {
                              LG.showSuccess('清空按钮成功');
                              f_reload();
                          },
                          error: function (message)
                          {
                              LG.showError(message);
                          }
                      });
                  });
                  break;
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
                  if (editingrow == null)
                  {
                      beginEdit();
                  } else
                  {
                      LG.tip('请先提交或取消修改');
                  }
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
              case "del":
                  $.ligerDialog.confirm('确定删除吗?', function (confirm) {
                      if (confirm)
                          f_delete();
                  });
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
              if (!selected.btnid)
              {
                  grid.deleteRow(selected);
                  return;
              }
              LG.ajax({
                  url: basepath+'systemMenu/delButton.do',
                  loading:'正在删除中...',
                  data: { btnID: selected.btnid },
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
      var toolbarOptions = { 
        items: [ 
            { text: '增加',  id:'add', click: itemclick , img:basepath+"resource/icons/silkicons/add.png"}, 
            { line:true },
            { text: '修改', id:'modify',click: itemclick, img: basepath+"resource/icons/miniicons/page_edit.gif" },
            { line: true },
             { text: '保存',id:'save', click: itemclick, img: basepath+"resource/icons/silkicons/page_save.png" },
            { line: true },
             { text: '取消',id:'cancel', click: itemclick, img: basepath+"resource/icons/silkicons/cancel.png" },
            { line: true },
            { text: '删除', id:'del',click: itemclick, img: basepath+"resource/icons/miniicons/page_delete.gif" },
            { line: true },
            { text: '快速增加 【增删改查】', id: 'fastaddbtn', click: itemclick, img: basepath+"resource/icons/miniicons/page_delete.gif" } ,
            { line: true },
            { text: '清空按钮', id: 'clearbtns', click: itemclick, img: basepath+"resource/icons/miniicons/page_cross.gif" } 
            
        ]
    };

    var grid = $("#maingrid").ligerGrid({
        columns: [
                { display: '按钮编号', name: 'btnno', align: 'left', width: 130, minWidth: 60
                , validate: { required: true }
                , editor: { type: 'text' }
                },
                { display: '按钮名称', name: 'btnname', align: 'left', width: 300, minWidth: 60
                , validate: { required: true }
                , editor: { type: 'text' }
                }
                , { display: '图标', name: 'btnicon', align: 'left', width: 230, minWidth: 50
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
                                return rowdata.btnicon;
                            }
                        };
                    }

                }
                , render: function (item)
                {
                    return "<div style='width:100%;height:100%;'><img src='" + basepath+(item.btnicon==null?"":item.btnicon)+ "' /></div>";
                }
                }
                ],  toolbar: toolbarOptions, sortName: 'BtnID',
        width: '98%', height: '100%', heightDiff: -5, checkbox: false, 
        usePager: false, enabledEdit: true, clickToEdit: false,
        fixedCellHeight: true, rowHeight: 25,
        url: basepath+'SystemWeb/getGrid.do?view=sys_button',
        parms: { where: JSON2.stringify({
            op: 'and',
            rules: [{ field: 'menuNo', value:menuNo, op: 'equal'}]
        })
        }
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
        var data = $.extend({ menuNo: menuNo }, e.newdata);
        if (!isAddNew)
            data.btnID = e.record.btnid;
        LG.ajax({
            loading: '正在保存数据中...',
            url: isAddNew ? basepath+'systemMenu/addButton.do' : basepath+'systemMenu/updateButton.do',
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