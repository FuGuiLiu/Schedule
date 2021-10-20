$(function () {
    $('.coupled.modal')
        .modal({
            allowMultiple: true
        })
    ;
    getAllData();
    var content = getAllBeanName();
    $('.ui.search')
        .search({
            source: content,
            onSelect: function (result, response) {
                $.post("/getDataByBeanName", {id: result.id}, success);

                function success(result) {
                    // 移除所以数据
                    $("#body_data").empty();
                    if (result.jobStatus) {
                        var tempHtml = "<input type=\"checkbox\" name=\"public\" checked>";
                    } else {
                        var tempHtml = "<input type=\"checkbox\" name=\"public\" uncheck>";
                    }
                    var html = "<tr><td>\n" +
                        "                    <h2 class=\"ui center aligned header\">" +
                        "<a href='javaScript:;' onclick='update(" + result.id + ")'><i class=\"small edit icon\"></i></a>" +
                        "<a href='javaScript:;' onclick='deleteData(" + result.id + ")'><i class=\"small delete icon\"></i></a>" +
                        "</h2>\n" +
                        "                </td>\n" +
                        "                <td class=\"single line\">" + result.id + "</td>\n" +
                        "                <td>" + result.beanName + "</td>\n" +
                        "                <td class=\"left aligned\">" + result.methodName + "</td>\n" +
                        "                <td>" + result.methodParams + "</td>\n" +
                        "                <td>" + result.cronExpression + "</td>\n" +
                        "                <td class=\"center aligned\">\n" +
                        "                    <div class=\"ui toggle checkbox\">\n" +
                        tempHtml +
                        "<label></label>" +
                        "                    </div>\n" +
                        "                </td>\n" +
                        "                <td>" + result.remark + "</td></tr>";

                    $("#body_data").append(html);
                }

                return true;
            },
            onResultsClose: function () {
                getAllData();
            }
        });

    // 新增ui界面
    $(".ui.primary.button.right.floated").click(function () {
        $('.ui.first.coupled.modal.tiny')
            .modal({
                onShow: function () {
                    $("#btn_submit").text("新增");
                    $("#div_header").text("新增任务");
                    $("#btn_submit").val(0);
                }
            }).modal('show');
        ;
    });

    // 重置按钮点击
    $("#btn_reset").click(function () {
        document.getElementById("add_form").reset();
    });
    // 提交按钮点击
    $("#btn_submit").click(function () {
        if ("1" === $("#btn_submit").val()) {
            let beanName = $("[name='beanName']").val();
            let methodName = $("[name='methodName']").val();
            let cronExpression = $("[name='cronExpression']").val();

            if (null == beanName || "" == beanName) {
                $(".ui.second.coupled.small.modal div:nth-of-type(2) > p").html("Bean名称不能为空");
                $(".ui.second.coupled.small.modal").modal('attach events', '.ui.modal.tiny .button');
                return;
            } else if (null == methodName || "" == methodName) {
                $(".ui.second.coupled.small.modal div:nth-of-type(2) > p").html("方法名称不能为空");
                $(".ui.second.coupled.small.modal").modal('attach events', '.ui.modal.tiny .button');
                return;
            } else if (null == cronExpression || "" == cronExpression) {
                $(".ui.second.coupled.small.modal div:nth-of-type(2) > p").html("Cron表达式不能为空");
                $(".ui.second.coupled.small.modal").modal('attach events', '.ui.modal.tiny .button');
                return;
            }
            // 序列号
            let jQuery = $("#add_form").serialize();
            $.post("/update", jQuery, success);

            function success(result) {
                if (result.errorMsg) {
                    $(".ui.second.coupled.small.modal div:nth-of-type(2) > p").html(result.errorMsg);
                    $(".ui.second.coupled.small.modal").modal('show');

                    setTimeout(function () {
                        // 隐藏新增模态框
                        $('.ui.first.coupled.modal.tiny')
                            .modal('hide')
                        ;
                    }, 1500);
                } else if (result.success) {
                    $(".ui.second.coupled.small.modal div:nth-of-type(2) > p").html(result.success);
                    $(".ui.second.coupled.small.modal").modal('show');

                    setTimeout(function () {
                        // 隐藏新增模态框
                        $('.ui.first.coupled.modal.tiny')
                            .modal('hide')
                        ;
                    }, 1500);

                }
                getAllData();
            }
        } else {

            let beanName = $("[name='beanName']").val();
            let methodName = $("[name='methodName']").val();
            let cronExpression = $("[name='cronExpression']").val();

            if (null == beanName || "" == beanName) {
                $(".ui.second.coupled.small.modal div:nth-of-type(2) > p").html("Bean名称不能为空");
                $(".ui.second.coupled.small.modal").modal('attach events', '.ui.modal.tiny .button');
                return;
            } else if (null == methodName || "" == methodName) {
                $(".ui.second.coupled.small.modal div:nth-of-type(2) > p").html("方法名称不能为空");
                $(".ui.second.coupled.small.modal").modal('attach events', '.ui.modal.tiny .button');
                return;
            } else if (null == cronExpression || "" == cronExpression) {
                $(".ui.second.coupled.small.modal div:nth-of-type(2) > p").html("Cron表达式不能为空");
                $(".ui.second.coupled.small.modal").modal('attach events', '.ui.modal.tiny .button');
                return;
            }
            // 序列号
            let jQuery = $("#add_form").serialize();
            $.post("/insertNew", jQuery, success)

            function success(result) {
                if (result.errorMsg) {
                    $(".ui.second.coupled.small.modal div:nth-of-type(2) > p").html(result.errorMsg);
                    $(".ui.second.coupled.small.modal").modal('show');

                    setTimeout(function () {
                        // 隐藏新增模态框
                        $('.ui.first.coupled.modal.tiny')
                            .modal('hide')
                        ;
                    }, 1500);
                } else if (result.success) {
                    $(".ui.second.coupled.small.modal div:nth-of-type(2) > p").html(result.success);
                    $(".ui.second.coupled.small.modal").modal('show');

                    setTimeout(function () {
                        // 隐藏新增模态框
                        $('.ui.first.coupled.modal.tiny')
                            .modal('hide')
                        ;
                    }, 1500);

                }
                getAllData();
            }
        }
    });
    // 关闭消息提示
    $('.message .close')
        .on('click', function () {
            $(this)
                .closest('.message')
                .transition('fade')
            ;
        })
    ;
});

// 查询所有数据
function getAllData() {
    $.ajax({
        url: "/getAllData",
        async: false,
        success: function (result) {
            // 移除所以数据
            $("#body_data").empty();
            var html = "";
            $.each(result, function (index, value) {
                if (value.jobStatus) {
                    var tempHtml = "<input type=\"checkbox\" name=\"public\" checked>";
                } else {
                    var tempHtml = "<input type=\"checkbox\" name=\"public\" uncheck>";
                }
                var paragraph = "<tr><td>\n" +
                    "                    <h2 class=\"ui center aligned header\">" +
                    "<a href='javaScript:;' onclick='update(" + value.id + ")'><i class=\"small edit icon\"></i></a>" +
                    "<a href='javaScript:;' onclick='deleteData(" + value.id + ")'><i class=\"small delete icon\"></i></a>" +
                    "</h2>\n" +
                    "                </td>\n" +
                    "                <td class=\"single line\">" + value.id + "</td>\n" +
                    "                <td>" + value.beanName + "</td>\n" +
                    "                <td class=\"left aligned\">" + value.methodName + "</td>\n" +
                    "                <td>" + value.methodParams + "</td>\n" +
                    "                <td>" + value.cronExpression + "</td>\n" +
                    "                <td class=\"center aligned\">\n" +
                    "                    <div class=\"ui toggle checkbox\">\n" +
                    tempHtml +
                    "<label></label>" +
                    "                    </div>\n" +
                    "                </td>\n" +
                    "                <td>" + value.remark + "</td></tr>";

                html = html + paragraph;

            });
            $("#body_data").append(html);
        }
    });
}

// 查询所有Bean名称
function getAllBeanName() {
    var content = null;
    $.ajax({
        url: "/getAllBeanName",
        async: false,
        success: function (result) {
            content = result;
        }
    });
    return content;
}

// 修改
function update(id) {
    $('.ui.first.coupled.modal.tiny')
        .modal({
            onShow: function () {
                $("#btn_submit").text("修改");
                $("#div_header").text("修改任务");
                $("#btn_submit").val(1);
                // 根据id查询当前对象信息
                $.ajax({
                    url: "/getDataByBeanName",
                    async: false,
                    data: {
                        id: id,
                    },
                    success: function (result) {
                        // 赋值
                        $("[name='beanName']").val(result.beanName);
                        $("[name='methodName']").val(result.methodName);
                        $("[name='cronExpression']").val(result.cronExpression);
                        if (result.jobStatus) {
                            $("#ipt_for1").attr("checked", "checked");
                            $("#ipt_for2").removeAttr("checked");
                        } else {
                            $("#ipt_for2").attr("checked", "checked");
                            $("#ipt_for1").removeAttr("checked");
                        }
                        $("[name='remark']").val(result.remark);
                        $("[name='id']").val(id);
                    }
                });
            }
        }).modal('show');
    ;
}

// 删除
function deleteData(id) {
    $(".ui.basic.modal").modal({
        closable: false,
        onApprove: function () {
            $.post("/delete", {id: id}, success);

            function success(result) {
                if (result.errorMsg) {
                    $(".ui.second.coupled.small.modal div:nth-of-type(2) > p").html(result.errorMsg);
                    $(".ui.second.coupled.small.modal").modal('show');

                    setTimeout(function () {
                        // 隐藏新增模态框
                        $('.ui.first.coupled.modal.tiny')
                            .modal('hide')
                        ;
                    }, 1500);
                } else if (result.success) {
                    $(".ui.second.coupled.small.modal div:nth-of-type(2) > p").html(result.success);
                    $(".ui.second.coupled.small.modal").modal('show');

                    setTimeout(function () {
                        // 隐藏新增模态框
                        $('.ui.first.coupled.modal.tiny')
                            .modal('hide')
                        ;
                    }, 1500);

                }
                getAllData();
            }
        }
    }).modal('show');
}

