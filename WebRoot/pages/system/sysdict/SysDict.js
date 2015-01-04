$(function(){
	//相对路径
	var rootPath = basepath;
	var types = LG.getSelectData(basepath+"systemDict/getDictTypes.do");
	//列表结构
	var grid = $("#maingrid")
			.ligerGrid(
					{
						columns : [ {
							display : "标签",
							name : "label",
							width : 300,
							type : "text",
							id: 'labelName',
							align : "left"
						}, {
							display : "键值",
							name : "value",
							width : 100,
							type : "text",
							align : "left"
						}, {
							display : "类型",
							name : "type",
							width : 150,
							type : "text",
							align : "left"
						}, {
							display : "描述",
							name : "desciption",
							width : 280,
							type : "textarea",
							align : "left"
						}, {
							display : "排序",
							name : "sort",
							width : 100,
							type : "text",
							align : "left"
						}],
						dataAction : 'server',
						pageSize : 20,
						toolbar : {},
						/* data:data, */
						url : rootPath
									+ 'SystemWeb/getTreeGrid.do?view=sys_dict&idfield=id&pidfield=fatherid',
						sortName : 'id',
						tree : {
							columnId : 'labelName',
//							columnName : 'label'
						       idField: 'id',
			                    parentIDField: 'fatherid'
						},
						width : '98%',
						height : '100%',
						heightDiff : -10,
						checkbox : true
	 });
	 $("#formsearch").ligerForm({
		   fields:[{display: "类型 ", name: "type", newline: true, labelWidth: 100, width: 220, space: 30, type: "autocomplete",
					comboboxName: "mytype", attr: { op: "equal" }, cssClass: "field", 
					options: { valueFieldID: "type", data:types,autocomplete : true }
					},
		           {display:"描述",name:"desciption",newline:false,labelWidth:100,width:220,space:30,type:"text",cssClass:"field",attr: { op: "like" }}],
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
				/*if(row.length>1){
					LG.tip('仅能选一行!');
					return;
				}
				var selected = grid.getSelected();	*/
				var selected = row[0];
				showDetail({
					parentLabel : selected ? selected.label : '',
					parentId : selected ? selected.id : 0,
					label : '',
					value : '',
					disttype :  selected ? selected.type : '',
					mydesciption : selected ? selected.desciption : '',
				    sort : 0
				}, true);
				break;
			/*case "addkeyvalue":
				var selected = grid.getSelected();
				
				if (!selected) {
					LG.tip('请选择行!');
					return
				}
				showDetail({
					label : '',
					value : '',
					disttype :  selected.type?selected.type:"",
					mydesciption :selected.desciption?selected.desciption:"",
				    id:selected.id,
				    sort : 0
				}, true);
				break;*/
			case "modify":
				var row = grid.getCheckedRows();
				/*if(row.length>1){
					LG.tip('仅能选一行!');
					return;
				}
				var selected = grid.getSelected();*/
				var selected = row[0];
				if (!selected) {
					LG.tip('请选择行!');
					return
				}
				var parent = grid.getParent(selected);
				showDetail(
						{   
							parentLabel : parent ? parent.label : "",
							parentId : selected ? selected.fatherid: 0,
							label :selected.label?selected.label:"",
							value : selected.value?selected.value:"",
							disttype :  selected.type?selected.type:"",
							mydesciption :selected.desciption?selected.desciption:"",
						    sort : selected.sort?selected.sort:"",
						    id:selected.id?selected.id:""
						}, false);
				break;
			case "delete":
				
				/*if(row.length>1){
					LG.tip('仅能选一行!');
					return;
				}*/
				
				//LG.exportExcel(grid,{title:'数据字典',sheetName:'数据字典',filename:"数据字典导出"});
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
					 url: basepath+'systemDict/delDict.do',
	                 loading:'正在删除中...',         
					data : {id : ids},
					success : function() {
						LG.showSuccess('删除成功');
						types = LG.getSelectData(basepath+"systemDict/getDictTypes.do");
						$("#mytype").ligerGetComboBoxManager().setData(types);
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
							name : "parentId",
							type : "hidden"
						 },
						 {
							display : "父标签",
							name : "parentLabel",
							newline : true,
							labelWidth : 100,
							width : 280,
							space : 30,
							type : "text",
							validate : {							
								maxlength : 50
							}
						},
					     {   display : "标签",
					    	 name : "label",
					    	 newline : true,
					    	 labelWidth : 100,
					    	 width : 280,space : 30,
					    	 type : "text",
					    	 validate : {
							     required : true,maxlength : 50
							 }
					      }, 
					      {   display : "键值",
					    	  name : "value",	
					    	  newline : true,	
					    	  labelWidth : 100,
					    	  width : 280,
					    	  space : 30,
					    	  type : "text",
								validate : {
									required : true,
									maxlength : 50
								}
					      },
					      {   display : "类型",
					    	  name : "disttype",	
					    	  newline : true,	
					    	  labelWidth : 100,
					    	  width : 280,
					    	  space : 30,
					    	  type : "text",
								validate : {
									required : true,
									maxlength : 50
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
								display : "描述",
								name : "mydesciption",
								newline : true,
								labelWidth : 100,
								width : 280,
								space : 30,
								type : "textarea",
								validate : {
									required : true
								}
					    } ],
					toJSON : JSON2.stringify
				});
				$("#parentLabel").attr("disabled", true);
				detailWin = $.ligerDialog.open({
					target : $("#detail"),
					width : 500,
					height : 350,
					title:'字典维护',
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
						curentData.label = $("#label").val();
						curentData.value = $("#value").val();
						curentData.type = $("#disttype").val();
						curentData.sort = $("#sort").val();
						curentData.desciption = $("#mydesciption").val();
						curentData.id = $("#id").val();
						curentData.fatherid = $("#parentId").val();
						LG.ajax({
							loading : '正在保存数据中...',
							url: basepath+"systemDict/"+(currentIsAddNew ? "insertDict.do"
									: "updateDict.do"),
							data : curentData,
							success : function(data,message) {
								types = LG.getSelectData(basepath+"systemDict/getDictTypes.do");
								 $("#mytype").ligerGetComboBoxManager().setData(types);
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
		}
});
