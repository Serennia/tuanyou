
function userLogin(){
	
}

LG.login = function ()
{
    $(document).bind('keydown.login', function (e)
    {
        if (e.keyCode == 13)
        {
            dologin();
        }
    });

    if (!window.loginWin)
    {
        var loginPanle = $("<form></form>");
        loginPanle.ligerForm({
            fields: [
                { display: '用户名', name: 'LoginUserName' },
                { display: '密码', name: 'LoginPassword', type: 'password' }
            ]
        });

        window.loginWin = $.ligerDialog.open({
            width: 400,
            height: 150, top: 200,
            isResize: true,
            title: '用户登录',
            target: loginPanle,
            buttons: [
            { text: '登录', onclick: function ()
            {
                dologin();
            } 
            },
            { text: '取消', onclick: function ()
            {
                window.loginWin.hide();
                $(document).unbind('keydown.login');
            } 
            }
            ]
        });
    }
    else
    {
        window.loginWin.show();
    }

    function dologin()
    {
        var username = $("#LoginUserName").val();
        var password = $("#LoginPassword").val();

        $.ajax({
            type: 'post', cache: false, dataType: 'json',
            url: '../login.action',
            data: [
                    { name: 'action', value: 'login' },
                    { name: 'username', value: username },
                    { name: 'password', value: password }
                    ],
            success: function (result)
            {
            	if (result!=null&&result!="")
                {
                	$.ligerDialog.error(result);
                    $("#txtUsername").focus();
                    return;
                } else
                {
                    location.href = decodeURIComponent("index.jsp");
                }
            },
            error: function ()
            {
                LG.showError('发送系统错误,请与系统管理员联系!');
            },
            beforeSend: function ()
            {
                LG.showLoading('正在登录中...');
            },
            complete: function ()
            {
                LG.hideLoading();
            }
        });
    }

};