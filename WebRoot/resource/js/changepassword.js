LG.changepassword = function ()
{
    $(document).bind('keydown.changepassword', function (e)
    {
        if (e.keyCode == 13)
        {
            doChangePassword();
        }
    });
    var changePasswordWin = null;
    if (changePasswordWin)
    {
    	$("#OldPassword").val();
    	$("#NewPassword").val();
    	$("#NewPassword2").val();
        changePasswordWin.show();
       
    }
    else
    {
        var changePasswordPanle = $("#passwordForm");
        changePasswordPanle.ligerForm({
            fields: [
                { display: '旧密码', name: 'OldPassword', type: 'password', validate: { maxlength: 50, required: true, messages: { required: '请输入密码'}} },
                { display: '新密码', name: 'NewPassword', type: 'password', validate: { maxlength: 50, required: true,mimaqiangdu:true, messages: { required: '请输入密码'}} },
                { display: '确认密码', name: 'NewPassword2', type: 'password', validate: { maxlength: 50, required: true, equalTo: '#NewPassword', messages: { required: '请输入密码', equalTo: '两次密码输入不一致'}} }
            ]
        });

        //验证
        jQuery.metadata.setType("attr", "validate");
        LG.validate(changePasswordPanle, { debug: true });
        LG.validate(changePasswordPanle);

        changePasswordWin = $.ligerDialog.open({
            width: 400,
            height: 180, top: 200,
            isResize: true,
            title: '用户修改密码',
            target : $("#passwordDetail"),
            buttons: [
            { text: '确定', onclick: function ()
            {
                doChangePassword();
            }
            },
            { text: '取消', onclick: function ()
            {
                changePasswordWin.hide();
                $(document).unbind('keydown.changepassword');
            }
            }
            ]
        });
        
    }

    function doChangePassword()
    {
        var OldPassword = $("#OldPassword").val();
        var NewPassword = $("#NewPassword").val();
        if (changePasswordPanle.valid())
        {
            LG.ajax({
                url: basepath+'SystemWeb/doChangePassword.do',
                data: { OldPassword: OldPassword, NewPassword: NewPassword },
                success: function (data,message)
                {
                
                    LG.showSuccess(message);
                    changePasswordWin.hide();
                    $(document).unbind('keydown.changepassword');
                },
                error: function ()
                {
                    LG.showError("message");
                }
            });
        }
    }

};