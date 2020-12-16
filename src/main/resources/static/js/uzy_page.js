/**
 * Create by ghostxbh 2020/2/15
 */
(function ($) {
    var ms = {
        init: function (obj, args) {
            return (function () {
                ms.fillHtml(obj, args);
                ms.bindEvent(obj, args);
            })();
        },
        //填充html
        fillHtml: function (obj, args) {
            return (function () {
                obj.empty();

                //上一页
                if (parseInt(args.page_no) > 1) {
                    let lastPage = args.page_no - 1;
                    obj.append('<a href="javascript:void(0)" onclick="goToPage(\'' + args.url + lastPage + '\')" class="prevPage"><p class="previous">上一页</p></a>');
                } else {
                    obj.remove('.prevPage');
                    obj.append('<span class="disabled"><p class="previous">上一页</p></span>');
                }
                //中间页码
                if (parseInt(args.page_no) != 1 && parseInt(args.page_no) >= 4 && parseInt(args.pages) != 4) {
                    obj.append('<a href="javascript:void(0)" onclick="goToPage(\'' + args.url + 1 + '\')" class="tcdNumber"><p>' + 1 + '</p></a>');
                }
                if (parseInt(args.page_no - 2) > 2 && parseInt(args.page_no) <= parseInt(args.pages) && parseInt(args.pages) > 5) {
                    obj.append('<span><p>...</p></span>');
                }
                var start = parseInt(args.page_no) - 2;
                var end = parseInt(args.page_no) + 2;
                if ((start > 1 && parseInt(args.page_no) < 4) || parseInt(args.page_no) == 1) {
                    end++;
                }
                if (parseInt(args.page_no) > parseInt(args.pages) - 4 && parseInt(args.page_no) >= parseInt(args.pages)) {
                    start--;
                }
                for (; start <= end; start++) {
                    if (start <= parseInt(args.pages) && start >= 1) {
                        if (start != parseInt(args.page_no)) {
                            obj.append('<a href="javascript:void(0)" onclick="goToPage(\'' + args.url + start + '\')" class="tcdNumber"><p>' + start + '</p></a>');
                        } else {
                            obj.append('<span class="page_no"><p class="page_no" style="background-color:#ccc;">' + start + '</p></span>');
                        }
                    }
                }
                if (parseInt(args.page_no) + 2 < parseInt(args.pages) - 1 && parseInt(args.page_no) >= 1 && parseInt(args.pages) > 5) {
                    obj.append('<span><p>...</p></span>');
                }
                if (parseInt(args.page_no) != parseInt(args.pages) && parseInt(args.page_no) < parseInt(args.pages) - 2 && parseInt(args.pages) != 4) {
                    obj.append('<a href="javascript:void(0)" onclick="goToPage(\'' + args.url + args.pages + '\')" class="tcdNumber"><p>' + args.pages + '</p></a>');
                }
                //下一页
                if (parseInt(args.page_no) < parseInt(args.pages)) {
                    let nextPage = args.page_no * 1 + 1;
                    obj.append('<a href="javascript:void(0)" onclick="goToPage(\'' + args.url + nextPage + '\')" class="nextPage"><p class="next">下一页</p></a>');
                } else {
                    obj.remove('.nextPage');
                    obj.append('<span class="disabled"><p class="next">下一页</p></span>');
                }
                obj.append('<span><p style="border:none;">跳转<input type="text" id="pageIndex" onkeyup="var page_no = $(this).val();if (isNaN(page_no)) {$(this).val(\'\');}if (page_no.indexOf(\'.\') != -1) {$(this).val(\'\');}">页</p><a href="javascript:void(0);" class="btn" onclick="goPage(\'' + args.url + '\')">确定</a></span>');
            })();
        },
        //绑定事件
        bindEvent: function (obj, args) {
            return (function () {
                obj.off("click", "a.tcdNumber");
                obj.on("click", "a.tcdNumber", function () {
                    var page_no = parseInt($(this).text());
                    ms.fillHtml(obj, {"page_no": page_no, "pages": args.pages});
                    if (typeof (args.backFn) == "function") {
                        args.backFn(page_no);
                    }
                });
                //上一页
                obj.off("click", "a.prevPage");
                obj.on("click", "a.prevPage", function () {
                    var page_no = parseInt(obj.children("span.page_no").text());
                    ms.fillHtml(obj, {"page_no": page_no - 1, "pages": args.pages});
                    if (typeof (args.backFn) == "function") {
                        args.backFn(page_no - 1);
                    }
                });
                //下一页
                obj.off("click", "a.nextPage");
                obj.on("click", "a.nextPage", function () {
                    var page_no = parseInt(obj.children("span.page_no").text());
                    ms.fillHtml(obj, {"page_no": page_no + 1, "pages": args.pages});
                    if (typeof (args.backFn) == "function") {
                        args.backFn(page_no + 1);
                    }
                });
                obj.off("click", "a.btn");
                obj.on("click", "a.btn", function () {
                    var page_no = $("#pageIndex").val();

                    if (parseInt(page_no) > 0 && parseInt(page_no) <= parseInt(args.pages) && page_no != "") {
                        ms.fillHtml(obj, {"page_no": page_no, "pages": args.pages});
                        if (typeof (args.backFn) == "function") {
                            args.backFn(page_no);
                        }
                        $("#pageIndex").val(page_no);
                    } else {
                        $("#pageIndex").val("");
                    }
                });

            })();
        },


        init1: function (obj, args) {
            return (function () {
                ms.fillHtml(obj, args);
            })();
        }
    };

    $.fn.createPage = function (options) {
        var page_no = options.page_no;
        var pages = options.pages;
        var url = options.url;
        var args = $.extend({
            pages: pages,
            page_no: page_no,
            url: url,
            backFn: function () {

            }
        }, options);
        ms.init(this, args);
    };

})(jQuery, window, document);

function goToPage(url) {
    window.location.href=url;
}

function goPage(url) {
    var index = $('#pageIndex').val();
    url += index;
    goToPage(url);
}
