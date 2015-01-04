var gridUser;
var gridRight;
$(function(){
	//覆盖本页面grid的loading效果
    LG.overrideGridLoading(); 
    var layout = $("#layout").ligerLayout({
        //2分之1的高度
        bottomHeight: 2 * $(window).height() / 5,
        heightDiff: -6,
        onEndResize: updateGridHeight,
        onHeightChanged: updateGridHeight
    });
    var bottomHeader = $(".l-layout-bottom > .l-layout-header:first");
    gridUser = $("#usergrid").ligerGrid({
        columns:
        [
        { display: '帐号', name: 'usercode', align: 'left', width: 200, minWidth: 60 },
        { display: '名称', name: 'username', align: 'left', width: 200, minWidth: 60 },
        { display: '创建时间', name: 'createtime', align: 'left', width: 200, minWidth: 60 }
        ], showToggleColBtn: false, width: '99%', height: 310, rowHeight: 20, fixedCellHeight: true,
        columnWidth: 100, frozen: false,  usePager: true, checkbox: false, rownumbers: true,
        dataAction: 'server',pageSize: 20,heightDiff:-10,
       // url: basepath+'SystemWeb/getGrid.do?view=stb_customer'
        url: basepath+'SystemWeb/getGrid.do?cachename=systemweb/getgrid&resource=getuserlist'
    });
    gridRight = $("#userrolegrid").ligerGrid({
        columns: [
        { display: "序号", name: "userid", width: 180, type: "text", align: "left"
        }, 
        { display: "单位名称", name: "username", width: 180, type: "text", align: "left"
        },
        { display: "角色编号", name: "id", width: 180, type: "text", align: "left"
        },
        { display: "角色名称", name: "name", width: 180, type: "text", align: "left"
        },
        { display: "描述", name: "description", width: 180, type: "textarea", align: "left" }
        ], dataAction: 'server',
       // url: basepath+'SystemWeb/getGrid.do?resource=getUserRole',
        url: basepath+'SystemWeb/getGrid.do?cachename=systemweb/getgrid&resource=getUserRole',
        width: '98%', height: '100%',heightDiff:-10, checkbox: false,enabledEdit: true, clickToEdit: false,usePager:false,delayLoad:true, 
        toolbar:{}
    });
    gridUser.bind('SelectRow', function (rowdata)
    {
    	LG.getSelectData(basepath+"sysUserRole/init.do?usercode="+rowdata.usercode);
        //显示禁止权限列
        bottomHeader.html("用户【" + rowdata.username + "】 拥有角色");
        gridRight.setOptions({ parms: [{ name: 'userid', value: JSON2.stringify(rowdata.userid)}]});
        gridRight.loadData(true);
    });
    //验证
    var maingform = $("#mainform");
    $.metadata.setType("attr", "validate");
    LG.validate(maingform, { debug: true });
    //加载toolbar
    LG.loadToolbar(gridRight, toolbarBtnItemClick);
});

function toolbarBtnItemClick(item)
{
	switch (item.id) {
	 case "add":
		 var userselect = gridUser.getSelectedRow();
		 showDetail({
				user_id: userselect ? userselect.userid : '',
				username: userselect ? userselect.username :'',
				usercode: userselect ? userselect.usercode :'',
				org_id: userselect ? userselect.orgid :''

		    }, true);
		 break;
	 case 'modify':
		// alert("nodify");
		 var roleselect = gridRight.getSelectedRow();
		 var gridUserselect = gridUser.getSelectedRow();
	     if (!roleselect) { LG.tip('请选择需要修改的行!'); return; }
	     showDetail({
	    	 user_id: roleselect ? roleselect.userid : '',
	    	 username: gridUserselect.username ? gridUserselect.username : '',
	         name: roleselect.name,
	         sort: roleselect.sort,
	         role_id:roleselect.roleid,
	         id: roleselect.id,
	         usercode: roleselect.usercode 
	     }, false);
	     
		 break;
	 case 'delete':
		 var row = gridRight.getSelectedRow();
	     if (!row) { 
	    	 LG.tip('请选择需要删除的数据');
	    	 return; 
	     }
	     $.ligerDialog.confirm("您确认要删除用户的角色吗?",function(confirm){
	    	if(confirm){
	    		f_delete();
	    	}
	     });
		 break;
	}
}

var detailWin = null, curentData = null, currentIsAddNew,mainform;
function showDetail(data, isAddNew)
{
	//alert(data);
	curentData = data;
    currentIsAddNew = isAddNew;
   // alert(currentIsAddNew);
    if (detailWin)
    {
        detailWin.show(); 
    }
    else
    {
        //创建表单结构
        mainform = $("#mainform");
        mainform.ligerForm({
            inputWidth: 280,
            fields: [
           { name: "id", type: "hidden" },
           { name: "org_id", type: "hidden" },
           { name: "usercode", type: "hidden" },
           { name: "id", type: "hidden" },
           { name: "user_id", type: "hidden" },
           { display: "用户名称", name: "username", newline: true, labelWidth: 100, width: 220, space: 30, type: "text", validate: { required: true, maxlength: 50} },
           { display: "角色名称", name: "role_id", newline: true, labelWidth: 100, width: 220, space: 30, type: "select",validate: { required: true},
			  comboboxName: "myroleid", options: { valueFieldID: "role_id", url: basepath+"SystemWeb/getSelect.do?view=sys_role&idfield=id&textfield=name" }},
           { display: "排序号", name: "sort", newline: true, labelWidth: 100, width: 220, space: 30, type : "spinner",options:{type:'int'},validate: { required: true}}],
            toJSON: JSON2.stringify
        });
        $("#username").attr("disabled",true);
        if(currentIsAddNew == true){
        	detailWin = $.ligerDialog.open({
            	title:'角色新增',
                target: $("#userroleedit"),
                width: 450, height: 300,top:90,
                buttons: [
                { text: '确定', onclick: function () { save(); } },
                { text: '取消', onclick: function () { detailWin.hide(); } }
                ]
            });
        }
        if(currentIsAddNew == false){
        	detailWin = $.ligerDialog.open({
            	title:'角色修改',
                target: $("#userroleedit"),
                width: 450, height: 300,top:90,
                buttons: [
                { text: '确定', onclick: function () { f_nodify(); } },
                { text: '取消', onclick: function () { detailWin.hide(); } }
                ]
            });	
  
    }

    	
    }
    if (curentData)
    {
    	$("#org_id").val(curentData.org_id);
    	$("#usercode").val(curentData.usercode);
        $("#user_id").val(curentData.user_id);
        $("#username").val(curentData.username);
        $("#id").val(curentData.id);
        $("#role_id").val(curentData.role_id);
        $("#sort").val(curentData.sort);
//    	LG.loadForm(mainform,curentData)
    }
    function save()
    {
    	//LG.getSelectData(basepath+"sysUserRole/init.do?userid=,role_id="+curentData.user_id+curentData.role_id);
        curentData = {};
        curentData.org_id = $("#org_id").val();
        curentData.id = $("#id").val();
        curentData.usercode = $("#usercode").val();
        curentData.role_id = $("#role_id").val();
        curentData.user_id = $("#user_id").val();
        //curentData.sort = $("#sort").val();
         jQuery.metadata.setType("attr", "validate"); 
		 LG.validate(mainform, { debug: true });
		 if(mainform.valid()){
			 LG.ajax({
		            loading: '正在保存数据中...',
		           // url: basepath+'sysUserRole/'+( currentIsAddNew ? "insert.do" : "update.do"),
		            url: basepath+'sysUserRole/save.do',
		            data: curentData,
		            success: function (data,message)
		            {
		                LG.tip(message);
		               // alert(message);
		                gridRight.loadData(true);
		                detailWin.hide();
		            },
		            error: function (message)
		            {
		                LG.tip(message);
		            }
		        });
		 }
    }
    function f_nodify(){
   	 var selected = gridRight.getSelected();
   	 if (selected) {
   		 curentData = {};
   	        curentData.role_id = $("#role_id").val();
   	        curentData.id = $("#id").val();
   	         jQuery.metadata.setType("attr", "validate"); 
   			 LG.validate(mainform, { debug: true });
   	        LG.ajax({
   	            url: basepath+'sysUserRole/nodify.do',
   	            loading: '正在修改中...',
   	            data: curentData,
   	            success: function (data,message) {
   	                LG.showSuccess(message);
   	                f_reload();
   	            },
   	            error: function (message) {
   	                LG.showError(message);
   	            }
   	        });
   	    }else {
   	        LG.tip('请选择行!');
   	    }
   }
}

function f_reload() {
	gridRight.loadData();
}

function f_delete() {
    var selected = gridRight.getSelected();
    if (selected) {
        LG.ajax({
            url: basepath+'sysUserRole/delete.do',
            loading: '正在删除中...',
            data: { usercode: selected.usercode ,roleid: selected.roleid},
            success: function (data,message) {
                LG.showSuccess(message);
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


function updateGridHeight()
{ 
    var topHeight = $("#layout > .l-layout-center").height();
    var bottomHeight = $("#layout > .l-layout-bottom").height();
    if(gridUser)
        gridUser.set('height', topHeight - 65);
    if(gridRight)
        gridRight.set('height', bottomHeight - 35); 
}
updateGridHeight();