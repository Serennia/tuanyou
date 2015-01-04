LG.addfavorite = function(success) {
	
	$(document).bind('keydown.addfavorite', function(e) {
		if (e.keyCode == 13) {
			doAddFavorite();
		}
	});

	if (!window.addfavoriteWin) {
		var addfavoritePanle = $("#addForm");

		var menusTree = {
			url : basepath + 'systemMenu/getMenus.do',
			checkbox : false,
			valueField : 'menuid',
			textFieldName : 'menuname',
			nodeWidth : 220
		};

		addfavoritePanle.ligerForm({
			fields : [ {
				display : "页面",
				name : "menuid",
				newline : true,
				labelWidth : 100,
				width : 220,
				space : 30,
				type : "select",
				comboboxName : "MyMenusMenuID",
				options : {
					id : 'MyMenusMenuID',
					treeLeafOnly : true,
					tree : menusTree,
					valueField : "menuid",
					textField:"menuname"
				},
				validate : {
					required : true,
					messages : {
						required : '请选择页面'
					}
				}
			},

			{
				display : "收藏备注",
				name : "FavoriteContent",
				newline : true,
				labelWidth : 100,
				width : 220,
				space : 30,
				type : "textarea"
			}
			]
		});
	
		// 验证
		jQuery.metadata.setType("attr", "validate");
		LG.validate(addfavoritePanle);

		window.addfavoriteWin = $.ligerDialog.open({
			width : 400,
			height : 200,
			top : 150,
			left : 230,
			isResize : true,
			title : '增加收藏',
			target : $("#detail"),
			buttons : [ {
				text : '确定',
				onclick : function() {
					doAddFavorite();
				}
			}, {
				text : '取消',
				onclick : function() {
					window.addfavoriteWin.hide();
					$(document).unbind('keydown.addfavorite');
				}
			} ]
		});
	} else {
		window.addfavoriteWin.show();
	}

	
	function doAddFavorite() {
		var manager = $.ligerui.get("MyMenusMenuID");
		if (addfavoritePanle.valid() && manager) {
			LG.ajax({
				url : basepath + 'SystemFavorite/add.do',
				data : {
					menuid : manager.getValue(),
					FavoriteContent : $("#FavoriteContent").val()
				},
				success : function(data, message) {
					LG.showSuccess(message);
					window.addfavoriteWin.hide();
					$(document).unbind('keydown.addfavorite');
					if (success) {
						success();
					}
				},
				error : function(message) {
					LG.showError(message);
				}
			});
		}

	}

};