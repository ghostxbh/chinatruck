/**
 * @author ghostxbh
 * @date 2020/04/01
 */
$(function () {
  var page_no = $("input[name='page_no']").val();
  var pages = $("input[name='pages']").val();
  var q = $("input[name='q']").val();
  var no = $("input[name='no']").val();
  var brand = $("input[name='brand']").val();
  var category = $("input[name='category']").val();
  var keywords = $("input[name='keywords']").val();
  var url = '/number.html?q=' + q + '&no=' + no + '&brand=' + brand + "&category=" + category + "&keywords=" + keywords + "&page_no=";
  //初始化加载分页
  getPage(page_no, pages, url);
});

function getPage(pageIndex, totalPage, url) {
  $(".uzy_page").createPage({      //创建分页
    pages: totalPage,               //总页数
    page_no: pageIndex,                 //当前页
    url: url,                           //请求链接
    backFn: function (p) {
      getPage(p, totalPage);          //点击页码或者跳转页码时的回掉函数，p为要跳转的页码
    }
  });
}

