$(function () {
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

                    var html = "<tr><td>\n" +
                        "                    <h2 class=\"ui center aligned header\"></h2>\n" +
                        "                </td>\n" +
                        "                <td class=\"single line\">" + result.id + "</td>\n" +
                        "                <td>" + result.beanName + "</td>\n" +
                        "                <td class=\"left aligned\">" + result.methodName + "</td>\n" +
                        "                <td>" + result.methodParams + "</td>\n" +
                        "                <td>" + result.cronExpression + "</td>\n" +
                        "                <td class=\"center aligned\">\n" +
                        "                    <div class=\"ui toggle checkbox\">\n" +
                        "                        <input type=\"checkbox\" name=\"public\" checked=" + result.jobStatus + ">\n" +
                        "                        <label></label>\n" +
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

    $(".ui.primary.button.right.floated").click(function () {
        $('.ui.modal.tiny')
            .modal('show')
        ;

    });
    $(".ui.button.right.floated").click(function () {

    });
    $(".ui.secondary.button.right.floated").click(function () {
    });

    // 重置按钮点击
    $("#btn_reset").click(function () {
        document.getElementById("add_form").reset();
    });
    // 提交按钮点击
    $("#btn_submit").click(function () {
        let beanName = $("[name='beanName']").val();
        let methodName = $("[name='methodName']").val();
        let cronExpression = $("[name='cronExpression']").val();
        if (null == beanName || "" == beanName) {
            $(".ui.negative.message.transition.hidden > div").html("Bean名称不能为空");
            $(".ui.negative.message.transition.hidden").transition('drop', {
                duration: 2000,
                allowRepeats: false,
                queue: true
            });
            $('.message .close').click();
        } else if (null == methodName || "" == methodName) {
            $(".ui.negative.message.transition.hidden > div").html("方法名称不能为空");
            $(".ui.negative.message.transition.hidden").transition('pulse', {
                duration: 2000,
                allowRepeats: false,
                queue: true
            });
            $('.message .close').click();
        } else if (null == cronExpression || "" == cronExpression) {
            $(".ui.negative.message.transition.hidden > div").html("Cron表达式不能为空");
            $(".ui.negative.message.transition.hidden").transition('pulse', {
                duration: 2000,
                allowRepeats: false,
                queue: true
            });
            $('.message .close').click();
        }
        // 序列号
        let jQuery = $("#add_form").serialize();
        $.post("/insertNew", jQuery, success)

        function success(result) {
            if (result.errorMsg) {
                $(".ui.negative.message.transition.hidden > div").html(result.errorMsg);
                $(".ui.negative.message.transition.hidden").transition('pulse', {
                    duration: 2000,
                    allowRepeats: false,
                    queue: true
                });
                $('.message .close').click();
            } else if (result.success) {
                // 隐藏新增模态框
                $('.ui.modal.tiny')
                    .modal('hide')
                ;
                $(".ui.modal div:nth-of-type(2) > p").html(result.success);
                $(".small.modal").modal('show');
            }
            getAllData();
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
    $(".ui.toggle.checkbox").checkbox({
        onChange: function (result) {

        }
    });
});

// 查询所有数据
function getAllData() {
    var content = null;
    $.ajax({
        url: "/getAllData",
        async: false,
        success: function (result) {
            // 移除所以数据
            $("#body_data").empty();
            var html = "";
            $.each(result, function (index, value) {
                var isCheck = value.jobStatus ? "checked" : "uncheck";
                if (value.jobStatus) {
                    var tempHtml = "<input type=\"checkbox\" checked>";
                } else {
                    var tempHtml = "<input type=\"checkbox\" uncheck>";
                }
                var paragraph = "<tr><td>\n" +
                    "                    <h2 class=\"ui center aligned header\"></h2>\n" +
                    "                </td>\n" +
                    "                <td class=\"single line\">" + value.id + "</td>\n" +
                    "                <td>" + value.beanName + "</td>\n" +
                    "                <td class=\"left aligned\">" + value.methodName + "</td>\n" +
                    "                <td>" + value.methodParams + "</td>\n" +
                    "                <td>" + value.cronExpression + "</td>\n" +
                    "                <td class=\"center aligned\">\n" +
                    "                    <div class=\"ui toggle checkbox\">\n" +
                    tempHtml +
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