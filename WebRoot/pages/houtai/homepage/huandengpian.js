$(function(){
	//列表结构
	var grid = $("#maingrid")
			.ligerGrid(
					{
						columns : [ {
							display : "标题",
							name : "label",
							width : 300,
							type : "text",
							id: 'labelName',
							align : "left"
						}, {
							display : "是否启用",
							name : "status",
							width : 100,
							type : "text",
							align : "left"
						}, {
							display : "排序",
							name : "sort",
							width : 150,
							type : "text",
							align : "left"
						},
						 {
							display : "创建时间",
							name : "cttime",
							width : 150,
							type : "text",
							align : "left"
						}],
						dataAction : 'server',
						pageSize : 20,
						toolbar : {},
						url : basepath	+ 'SystemWeb/getGrid.do?view=tuanyou_homepage_hdp',
						sortName : 'id',
						width : '98%',
						height : '100%',
						heightDiff : -10,
						checkbox : true
	 });
	 $("#formsearch").ligerForm({
		   fields:[
		           {display:"标题",name:"title",newline:false,labelWidth:100,width:220,space:30,type:"text",cssClass:"field",attr: { op: "like" }}],
		   toJSON: JSON2.stringify
	  });
	  //增加搜索按钮,并创建事件
	 LG.appendSearchButtons("#formsearch", grid); 
	//加载toolbar
		LG.loadToolbar(grid, toolbarBtnItemClick);
		
		//工具条事件
		function toolbarBtnItemClick(item) {
			switch (item.id) {
			case "add":
				var row = grid.getCheckedRows();
				var selected = row[0];
				showDetail({
					title : '',
					img :"",
					id:"",
					url:'',
					sort:'',
					status:''
				}, true);
				break;
			case "modify":
				var row = grid.getCheckedRows();
				var selected = row[0];
				if (!selected) {
					LG.tip('请选择行!');
					return
				}
				var parent = grid.getParent(selected);
				showDetail(
						{   
							title : selected ? selected.title: "",
							img :selected.img?selected.img:"",
							url : selected.url?selected.url:"",
							sort :  selected.sort?selected.sort:"",
							status :selected.status?selected.status:"",
						    id:selected.id?selected.id:""
						}, false);
				break;
			case "delete":
				jQuery.ligerDialog.confirm('确定删除吗?', function(confirm) {
					if (confirm)
						f_delete();
				});
				break;
			}
			
		}
		function f_reload() {
			grid.loadData();
		}
		function f_delete() {
			var row = grid.getCheckedRows();
			var selected = row[0];
			if (selected) {
				var o = grid.getSelectedRows();
				var ids="";
			  	$.each(o, function (j, m) { 
			  		ids+="'"+m.id+"',";
		    	});
			  	ids=ids.substring(0, ids.length-1);
				LG.ajax({
					 url: basepath+'homepage/delHdp.do',
	                 loading:'正在删除中...',         
					data : {id : ids},
					success : function() {
						LG.showSuccess('删除成功');
						f_reload();
					},
					error : function(message) {
						LG.showError(message);
					}
				});
			} else {
				LG.tip('请选择行!');
			}
		}

		var detailWin = null, curentData = null, currentIsAddNew;
		var mainform = $("#mainform");
		function showDetail(data, isAddNew) {
			curentData = data;
			currentIsAddNew = isAddNew;
			if (detailWin) {
				detailWin.show();
			} else {
				//创建表单结构
				mainform.ligerForm({
					inputWidth : 280,
					fields : [ 
					     {name : "id",type : "hidden"},
						 {
							display : "标题",
							name : "title",
							newline : true,
							labelWidth : 100,
							width : 280,
							space : 30,
							type : "text",
							validate : {							
								maxlength : 50
							}
						},
						   { display: "背景图", name: "img", comboboxName: "imgName", newline: true, 
							labelWidth: 100, width: 280, space: 30, type: "select", options: {
								valueFieldID:"img"
							}
					    
					      },
							{
								display : "是否启用",
								name : "status",
								newline : true,
								labelWidth : 100,
								space : 30,
								type : "select",comboboxName: "statusName", 
								validate : {
									required : true
								}	, 
							    options: { valueFieldID: "status", data:				 
							 			[
								   			{ text: '是', id: '0' },
				                    		{ text: '否', id: '1' }
							 			]
							 		}
							}, 
					      {
								display : "排序",
								name : "sort",
								newline : true,
								labelWidth : 100,
								width : 280,
								space : 30,
								type : "int"
					    }, 
					    {
								display : "超链接地址",
								name : "url",
								newline : true,
								labelWidth : 100,
								width : 280,
								space : 30,
								type : "textarea",
								validate : {
									url : true
								}
					    } ],
					toJSON : JSON2.stringify
				});
				detailWin = $.ligerDialog.open({
					target : $("#detail"),
					width : 500,
					height : 350,
					title:'首页幻灯片维护',
					top : 90,
					buttons : [ {
						text : '确定',
						onclick : function() {
							save(mainform);
						}
					}, {
						text : '取消',
						onclick : function() {
							detailWin.hide();
						}
					} ]
				});
			}
			if (curentData) {
				LG.loadDataForm(mainform,curentData);
			}

			function save() {
				 jQuery.metadata.setType("attr", "validate"); 
				 LG.validate(mainform, { debug: true });
				 if(mainform.valid()){
					 curentData =  {};
						curentData.img = $("#img").val();
						curentData.url = $("#url").val();
						curentData.status = $("#status").val();
						curentData.sort = $("#sort").val();
						curentData.title = $("#title").val();
						curentData.id = $("#id").val();
						LG.ajax({
							loading : '正在保存数据中...',
							url: basepath+"homepage/"+(currentIsAddNew ? "insertHdp.do"
									: "updateHdp.do"),
							data : curentData,
							success : function(data,message) {
								grid.loadData();
								detailWin.hide();
								LG.tip(message);
							},
							error : function(message) {
								LG.tip(message);
							}
						});
				 }
				   
			}
			 $.ligerui.get("imgName").set('onBeforeOpen', f_select_file);
			
			
		}
});

var AppendBillPathDetail = null;
function f_select_file() {
    if (AppendBillPathDetail) {
        AppendBillPathDetail.show();
    }
    else {
        AppendBillPathDetail = $.ligerDialog.open({
            target: $("#AppendBill_Div"), title: '添加图片',
            width: 360, height: 170, top: 270, left: 380,  // 弹出窗口大小
            buttons: [
                { text: '上传', onclick: function () { AppendBillPath_save(); } },
                { text: '取消', onclick: function () { AppendBillPathDetail.hide(); } }
                ]
        });
    }
}


function AppendBillPath_save() 
{
     var imgurl = $("#fileupload").val();
    var extend = imgurl.substring(imgurl.lastIndexOf("."), imgurl.length);
    extend = extend.toLowerCase();
    if (extend == ".jpg" || extend == ".jpeg" || extend == ".png" || extend == ".gif" || extend == ".bmp") 
    {
    }
    else 
    {
        LG.showError("请上传jpg,jpep,png,gif,bmp格式的图片文件");


        return;
    }
    var imageurl = $("#fileupload").val();  // extend
    $.ajaxFileUpload({
    	data:{'imageurl':imageurl},
        url:  basepath+"SystemWeb/uploadfile.do?type=img&model=huandengpian&imageurl="+imageurl  , // --上传-
        secureuri: false,
        fileElementId: "fileupload", //Input file id
        dataType: "json",
        success: function (data, status) 
        {
           $("#imgName").val(data);
           LG.tip("上传成功!");
           AppendBillPathDetail.hide();
        },
        error: function (data, status, e) {
            LG.showError(data.Message);


        }
    });


}
