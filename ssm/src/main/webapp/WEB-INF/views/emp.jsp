
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../base.jsp"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->

    <%--以当前项目路径为相对路径参考--%>
    <base href="<%=basePath%>">
    <title>员工界面</title>

    <!-- Bootstrap -->
    <link href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="static/js/jquery-1.12.4.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。也可以根据需要只加载单个插件。 -->
    <script src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

    <style type="text/css">
        *{
            margin: 0px;
            padding: 0px;
            box-sizing: border-box;
        }
        .mycontainer{
            margin-top: 50px;
        }
        .myimg{
            width: 100px;
        }
        .mythumbnail{
            border: none;
            background-color: rgb(250,247,245);
            margin-bottom: 0px;
        }
        .mycontainer>.row>.sidebar>.row:first-child{
            background-color: rgb(250,247,245);
        }
        .sidebar{
            height: 550px;
            box-shadow: 0px 0px 2px rgba(0,0,0,.2);
        }
        #emp_data_full{
            display: inline-block;
            height: 20px;
            width: 100%;
        }
        /*hiddenText类需定义在activeText上面，避免覆盖*/
        .hiddenText{
            display: none;
        }
        .activeText{
            display: block;
        }

    </style>

    <%--jQuery代码--%>
    <script>
        $(function () {
            //员工的id，作为全局变量
            var uid;

            //页面加载完毕，初始化员工信息
            initEmpployee();

            //初始化员工信息，发送ajax请求
            function initEmpployee() {
                $.ajax({
                    url:"emp/init",
                    success:function (result) {
                        //console.log(result);
                        uid=result.obj.employee.uid;
                        var username=result.obj.employee.username;
                        var gender=result.obj.employee.gender;
                        var email=result.obj.employee.email;
                        var did=result.obj.employee.did;
                        //完善左侧头像栏
                        $(".mythumbnail p").text(username);
                        //完善个人资料
                        $("#emp_data_username input").val(username);
                        $("#emp_data_gender input").val([gender]);
                        $("#emp_data_email input").val(email);
                        $("#emp_data_did select").val([did]);

                    }
                });
            }

            /*对邮箱格式的正则检验*/
            function checkEmail(sel){
                $(sel).focusout(function () {
                    $(this).parent().removeClass("has-error has-success");
                    $(this).next("span").text("");
                    var email = $(this).val();
                    var regExp=/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
                    var flag=regExp.test(email);
                    if(!flag){
                        $(this).parent().addClass("has-error");
                        $(this).next("span").text("邮箱格式错误");
                        //不可以点击保存修改按钮
                        $("#update_save_btn").attr("ajax-save","false")
                    }else{
                        $(this).parent().addClass("has-success");
                        $(this).next("span").text("邮箱格式正确");
                        //可以点击保存修改按钮
                        $("#update_save_btn").attr("ajax-save","true")
                    }
                })
            }

            //点击修改按钮弹出模态框
            $("#emp_email_update_btn").click(function () {
                $("#emp_email_update_modal").modal("show");
                //先清空格式
                $("#update_email_input").parent().removeClass("has-error has-success");
                $("#update_email_input").val("");
                $("#update_email_input").next("span").text("");
                $("#update_save_btn").removeAttr("ajax-save");
                //1.前端对邮箱格式进行校验
                checkEmail("#update_email_input");

                $("#update_save_btn").click(function () {
                    var ajax_save=$("#update_save_btn").attr("ajax-save");
                    var email=$("#emp_email_update_modal form").serialize();

                    //当邮箱格式正确时才发送ajax请求修改邮箱
                    if(ajax_save=="true"){
                        //2.发送ajax请求保存修改
                        $.ajax({
                            url:"emp/"+uid,
                            data:email,
                            type:"PUT",
                            success:function (result) {
                                var tip=result.obj.tip;
                                alert(tip);
                                //重新初始化员工信息
                                initEmpployee();
                            }
                        });
                        //3.关闭模态框
                        $("#emp_email_update_modal").modal("hide");
                    }

                });

            });

            //点击左侧导航栏切换右侧内容
            $(".nav-sidebar>li").click(function () {
                var index = $(this).index();
                var sidebar = $(this).parents(".sidebar");
                var siblings = sidebar.siblings();
                /*先移除所有的activeText*/
                siblings.removeClass("activeText");
                var activeInfo =siblings.eq(index);
                activeInfo.addClass("activeText");
            });

            //点击退出登录返回登录页面，并发送ajax请求清空session域中数据
            $("#nav_top_right>ul>li:nth-child(1)").click(function () {
                $.ajax({
                    url:"employee/logout",
                    success:function (result) {
                        console.log(result.message);
                    }
                });
            });

        });
    </script>


</head>
<body>

<%--修改员工邮箱模态框--%>
<div class="modal" id="emp_email_update_modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改邮箱</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">邮箱</label>
                        <div class="col-sm-10">
                            <input type="email" class="form-control" id="update_email_input" name="email" placeholder="email@qq.com">
                            <span class="help-block"></span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="update_save_btn">保存修改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->



<%--顶部导航条--%>
<nav class="navbar navbar-default navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="javascript:;">员工界面</a>
        </div>
        <div class="navbar-collapse collapse" id="nav_top_right">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="index.jsp">安全退出</a></li>
                <li><a href="javascript:;">帮助</a></li>
            </ul>
        </div>
    </div>
</nav>

<%--主体内容--%>
<div class="container mycontainer">
    <div class="row">
        <%--左侧导航栏--%>
        <div class="col-md-2 sidebar">
            <%--左侧头像--%>
            <div class="row">
                <div class="col-md-12">
                    <div class="thumbnail mythumbnail">
                        <img src="static/imgs/userinfo.jpg" class="img-circle myimg">
                        <div class="caption">
                            <p class="text-center">曾强</p>
                        </div>
                    </div>
                </div>
            </div>

            <%--左侧导航条--%>
            <ul class="nav nav-sidebar">
                <li>
                    <a href="javascript:;">
                        <span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>
                        企业资讯
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                        个人资料
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>
                        密码管理
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
                        收件箱
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>
                        登录日志
                    </a>
                </li>
            </ul>
        </div>

        <%--显示资讯，activeText表示该div正在显示，初始时显示这个div--%>
        <div class="col-md-10 hiddenText activeText">
            <h3>企业资讯</h3>
            <div class="row placeholders">
                <div class="col-xs-6 col-sm-3 col-md-3 placeholder">
                    <img width="200" height="200" class="img-responsive" alt="Generic placeholder thumbnail" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==">
                    <h4>5月1日放假通知</h4>
                    <span class="text-muted">2021年五一在即，为了便于公司同仁提前安排好工作和生活，现根据国务院办公厅通知精神及公司的福利政策，将“五一”期间的放假安排通知如下...</span>
                </div>
                <div class="col-xs-6 col-sm-3 col-md-3 placeholder">
                    <img width="200" height="200" class="img-responsive" alt="Generic placeholder thumbnail" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==">
                    <h4>5月1日放假通知</h4>
                    <span class="text-muted">2021年五一在即，为了便于公司同仁提前安排好工作和生活，现根据国务院办公厅通知精神及公司的福利政策，将“五一”期间的放假安排通知如下...</span>
                </div>
                <div class="col-xs-6 col-sm-3 col-md-3 placeholder">
                    <img width="200" height="200" class="img-responsive" alt="Generic placeholder thumbnail" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==">
                    <h4>5月1日放假通知</h4>
                    <span class="text-muted">2021年五一在即，为了便于公司同仁提前安排好工作和生活，现根据国务院办公厅通知精神及公司的福利政策，将“五一”期间的放假安排通知如下...</span>
                </div>
                <div class="col-xs-6 col-sm-3 col-md-3 placeholder">
                    <img width="200" height="200" class="img-responsive" alt="Generic placeholder thumbnail" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==">
                    <h4>5月1日放假通知</h4>
                    <span class="text-muted">2021年五一在即，为了便于公司同仁提前安排好工作和生活，现根据国务院办公厅通知精神及公司的福利政策，将“五一”期间的放假安排通知如下...</span>
                </div>
                <div class="col-xs-6 col-sm-3 col-md-3 placeholder">
                    <img width="200" height="200" class="img-responsive" alt="Generic placeholder thumbnail" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==">
                    <h4>5月1日放假通知</h4>
                    <span class="text-muted">2021年五一在即，为了便于公司同仁提前安排好工作和生活，现根据国务院办公厅通知精神及公司的福利政策，将“五一”期间的放假安排通知如下...</span>
                </div>
                <div class="col-xs-6 col-sm-3 col-md-3 placeholder">
                    <img width="200" height="200" class="img-responsive" alt="Generic placeholder thumbnail" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==">
                    <h4>5月1日放假通知</h4>
                    <span class="text-muted">2021年五一在即，为了便于公司同仁提前安排好工作和生活，现根据国务院办公厅通知精神及公司的福利政策，将“五一”期间的放假安排通知如下...</span>
                </div>
            </div>
        </div>

        <%--显示个人资料，可修改--%>
        <div class="col-md-10 hiddenText" id="emp_data">
            <%--标题行--%>
            <div class="row">
                <div class="col-md-12">
                    <h3>个人资料</h3>
                    <span id="emp_data_full"></span>
                </div>
            </div>
            <%--表单数据--%>
            <form class="form-horizontal">
                <div class="form-group">
                    <label class="col-sm-2 control-label">姓名:</label>
                    <div class="col-sm-8" id="emp_data_username">
                        <input type="text" name="username" class="form-control" readonly>
                    </div>
                </div>
                <hr>
                <div class="form-group">
                    <label class="col-sm-2 control-label">性别:</label>
                    <div class="col-sm-8" id="emp_data_gender">
                        <input type="radio" name="gender" value="M" disabled="disabled">男
                        <input type="radio" name="gender" value="F" disabled="disabled">女
                    </div>
                </div>
                <hr>
                <div class="form-group">
                    <label class="col-sm-2 control-label">邮箱:</label>
                    <div class="col-sm-8" id="emp_data_email">
                        <input type="text" name="email" class="form-control" readonly>
                    </div>
                    <div class="col-sm-2">
                        <button type="button" class="btn btn-link" id="emp_email_update_btn">修改</button>
                    </div>
                </div>
                <hr>
                <div class="form-group">
                    <label class="col-sm-2 control-label">部门:</label>
                    <div class="col-sm-3" id="emp_data_did">
                        <select name="did" class="form-control" disabled="disabled">
                            <option value="1">开发部</option>
                            <option value="2">测试部</option>
                            <option value="3">运维部</option>
                        </select>
                    </div>
                </div>
                <hr>
            </form>
        </div>

        <%--密码管理--%>
        <div class="col-md-10 hiddenText">
            <h3>密码管理</h3>
        </div>

        <%--收件箱--%>
        <div class="col-md-10 hiddenText">
            <h3>收件箱</h3>
        </div>

        <%--登录日志--%>
        <div class="col-md-10 hiddenText">
            <h3>登录日志</h3>
        </div>

    </div>
</div>

</body>
</html>
