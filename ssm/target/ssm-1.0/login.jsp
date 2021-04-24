
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="base.jsp"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->

    <%--以当前项目路径为相对路径参考--%>
    <base href="<%=basePath%>">
    <title>登录界面</title>


    <!-- Bootstrap -->
    <link href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="static/js/jquery-1.12.4.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。也可以根据需要只加载单个插件。 -->
    <script src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

    <%--css样式--%>
    <style>
        *{
            padding: 0px;
            margin: 0px;
            box-sizing: border-box;
        }
        body{
            background-color: #eee;
        }
        .full{
            height: 150px;
        }

        /*.login{
            text-align: center;
            box-shadow: 0px 0px 2px rgba(0,0,0,.2);
        }*/
        .mylogin{
            box-shadow: 0px 0px 5px rgba(0,0,0,.3);
        }

        .mybtn{
            width: 100%;
        }
        #kaptcha{
            width: 100px;
            height: 34px;
        }
    </style>

    <%--jQuery代码--%>
    <script>
        $(function () {

            /*重新发送请求，更新验证码图片*/
            $("#kaptcha").click(function () {
                $(this).attr("src","kaptcha.jpg?d="+new Date());
            });

            /*点击登录按钮，发送ajax请求*/
            $("#login_btn").click(function () {
                //先清理
                $("#username_input").parent().removeClass("has-error has-success");
                $("#username_input").next("span").text("");
                $("#password_input").parent().removeClass("has-error has-success");
                $("#password_input").next("span").text("");
                $("#kaptcha_input").parent().removeClass("has-error has-success");
                $("#kaptcha_input").next("span").text("");
                var formData=$("form").serialize();
                //console.log(formData);
                $.ajax({
                    url:"user/login",
                    data:formData,
                    success:function (result) {
                        var tip=result.obj.tip;
                        if(tip=="用户名错误"){
                            $("#username_input").parent().addClass("has-error");
                            $("#username_input").next("span").text(tip);
                        }else {
                            $("#username_input").parent().addClass("has-success");
                        }

                        if(tip=="密码错误"){
                            $("#password_input").parent().addClass("has-error");
                            $("#password_input").next("span").text(tip);
                        }else{
                            $("#password_input").parent().addClass("has-success");
                        }

                        if(tip=="验证码错误"){
                            $("#kaptcha_input").parent().addClass("has-error");
                            $("#kaptcha_input").next("span").text(tip);
                            //重新生成验证码
                            $("#kaptcha").attr("src","kaptcha.jpg?d="+new Date());
                        }else if(tip=="验证码正确"){
                            $("#kaptcha_input").parent().addClass("has-success");
                        }

                        //验证码成功，手动提交表单 submit()函数
                        if($("#kaptcha_input").parent().hasClass("has-success")){
                            $("form")[0].submit();
                        }
                    }
                });
            });

            //禁用回退按钮
            history.pushState(null,null,document.URL);
            window.addEventListener('popstate',function () {
                history.pushState(null,null,document.URL);

            })

        })
    </script>

</head>
<body>
    <div class="container">
        <div class="row">
            <div class="full">

            </div>
        </div>

        <div class="row">
            <div class="col-md-4 col-md-push-4 mylogin">
                <form class="form-horizontal" action="user/loginSuccess" method="post">
                    <div class="form-group">
                        <h4 class="col-md-4 col-md-push-4 text-center">用户登录</h4>
                    </div>
                    <div class="form-group">
                        <div class="col-md-12">
                            <input type="text" name="username" id="username_input" class="form-control" placeholder="用户名">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-12">
                            <input type="password" name="password" id="password_input" class="form-control" placeholder="密码">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-8">
                            <input type="text" name="kaptcha" id="kaptcha_input" class="form-control" placeholder="请输入五位验证码">
                            <span class="help-block"></span>
                        </div>
                        <div class="col-md-4">
                            <img id="kaptcha" src="kaptcha.jpg">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-12">
                            <button type="button" id="login_btn" class="btn btn-primary mybtn">登录</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
